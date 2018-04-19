import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import java.rmi.server.*; 
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


public class ChatClient implements Runnable {

  public static String name;
  public static ChatServerServices server;
  public static ChatClientServicesImpl client;
  public static ChatClientServices cli;
  public int status=0;
  public static void main(String [] args) {
	
	try {
	 /* if (args.length < 1) {
	   System.out.println("Usage: java ChatClient ");
	   return;
	}*/
	String host = "localhost";
	System.out.println("**************Welcome to Chaley Chat!*************************");
	System.out.println("Start by typing JOIN <name>");
	System.out.println("**************************************************************");

	// Get remote object reference
	Registry registry = LocateRegistry.getRegistry(host); 
	server = (ChatServerServices) registry.lookup("ChatServices");
	client = new ChatClientServicesImpl();
 	cli= (ChatClientServices) UnicastRemoteObject.exportObject(client, 0);
	ChatClient client = new ChatClient();
	 Thread t = new Thread(client);
	 t.start();

	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}

  }

  public String combine_message(String[] message){
  		String mes=" ";
  		for(int i = 2; i < message.length; i++){
  			mes += message[i];
  			mes +=" ";
  		}

  		return mes;
  }


  public void run(){

  	Scanner input = new Scanner(System.in);

 	String command;

 	while(true){

 		command= input.nextLine();
 		String split[] = command.split(" ");
 		String service=split[0];
 		String resp="";
 		if(service==""|| service==null || split.length<1){
 			System.out.println("Please enter a valid command or message");
 			break;
 		}

 		try{


 		switch (service) {
 			case "JOIN":
 				if(split.length!=2){
 					System.out.println("USE:JOIN <name>");
 					break;
 				}
 			   	name = split[1];
 			   	//System.out.println(name);
 			    
 			    resp=server.join(cli);
 			    status=1;
 			    client.set_client_name(name);
 			    name= name +"_" + String.valueOf(client.get_client_id());
 			    System.out.println(resp);
 			    System.out.println("Your unique identifier is "+ name + ". Please use this for Private messaging");
 			    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
 			    break;

 			case "LEAVE":
 				if(status!=0){
	 				resp = server.leave(cli);
	 				System.out.println(resp);
	 				status=0;
 				}else{
 					 System.out.println("You are not logged in");
 				}
 				break;

 			case "@":
 				if(split.length <= 2){
 					System.out.println("You cannot send empty message");
 					break;
 				}

 				if(split[1].split("_").length < 2){
 					System.out.println("User name not valid. Please use the format - @ name_id message- eg. @ dennis_1 hey");
 					break;
 				}
 				int user_id= Integer.parseInt(split[1].split("_")[1]);
 				String user_name= split[1].split("_")[0];
 				//System.out.println(user_name);
 				if(status!=0){
 				String message = "@"+name +": "+ combine_message(split);
 					server.private_message(message, client, user_id, user_name);
 				}
 				else{
 					System.out.println("Please JOIN to chat");
 				}
 				break;
 			case "HISTORY": //TOD0: HISTORY PARSER TO VALIDATE DURATION AND QUERY BUILDING
 				Date date = new Date();
 				 if(status!=0){

					if(split.length <2){
						System.out.println("You command incorrect: USE HISTORY TODAY or HISTORY H <number_of_hours> * hours must be an integer");
						break;
					}


 				 	String duration = split[1];
 				 	String query ="";
 				 	switch (duration) {
 				 		case "TODAY":
 				 			
							String modifiedDateTime= new SimpleDateFormat("yyyy-MM-dd").format(date);

							resp = server.history(modifiedDateTime+" 00:00:00", modifiedDateTime+" 23:59:00", modifiedDateTime );
							break;
 				 		case "H":
 				 			if(split.length <3){
								resp="You command incorrect: USE HISTORY H <number_of_hours> * hours must be an integer";
								break;
							}
							int hours =1;
							try{
								hours = Integer.parseInt(split[2]);
							}
							catch(Exception e){
								resp="Number of Hours must be an integer";
							}
 				 			
 				 			long HOUR = 60*60*1000*hours;
 				 			Date newDate = new Date(date.getTime() - HOUR);
 				 			String modifiedDateTime1= new SimpleDateFormat("yyyy-MM-dd").format(date);
							String modifiedTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
							String modifiedTime1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newDate);
							resp = server.history(modifiedTime1, modifiedTime, modifiedDateTime1 );

 				 			break;
 				 		case "DATE":
 				 			if(split.length <3){
								resp="You command incorrect: USE HISTORY DATE yyyy-MM-dd"; 
								break;
							}

 				 			String dur = split[2];
 				 			try{
 				 				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
								Date dates = format.parse(dur);
								String modifiedDateTime2 = dur;
								resp = server.history(modifiedDateTime2+" 00:00:00", modifiedDateTime2+" 23:59:00", modifiedDateTime2 );
								break;

							}
							catch(Exception e){	
								resp ="Date format wrong. Use yyyy-MM-dd ";
								
							}

 				 			break;
 				 		default:
 				 			 resp="History Usage: HISTORY TODAY or HISTORY H <number of hours> or USE HISTORY DATE yyyy-MM-dd";
 				 		     break;


 				 		
 				 	}
	 				//resp = server.history(duration);
	 				if(resp==""){
	 					resp= "No History Found";
	 				}
	 				System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	 				System.out.println(resp);
	 				System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	 				System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
 				}else{
 					 System.out.println("You are not logged in");
 				}
 				break;

 			default:
 				if(status!=0){
 				String message = "@"+name +": "+ command;
 				server.send_message(message, client);
 				}
 				else{
 					System.out.println("Please JOIN to chat");
 				}

 				break;
 				

 		}
 	  }
 	  catch (RemoteException e) {
 	  	   System.out.println(e.getMessage());
 	  }
 	}


  }


}