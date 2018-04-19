
import java.rmi.*;
import java.rmi.server.*; 
import java.rmi.registry.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;


public  class ChatServerServicesImpl implements ChatServerServices {

	public  List<ChatClientServices> client_list = new ArrayList<ChatClientServices>();
	public  List<String> messages_log = new ArrayList<String>();
	public static int clientids=0;
	public Date date = new Date();
	public String modifiedDateTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	public String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
	String FILENAME = "medatabase/"+modifiedDate + ".md";
	

	public void send_message(String message, ChatClientServices client) throws RemoteException {
			
			//store messages
			messages_log.add(message);

			//process messages
			for (ChatClientServices cli : client_list ){
				if(client.get_client_id() == cli.get_client_id()){
					
				}
				else{
					cli.receive_message(message);
				}
			}

			//store messages to file
			if(messages_log.size() == 1){
				System.out.println("LOGGING MESSAGES");
				write_messages_log(messages_log);
				messages_log.clear();
			}
	}

	public void private_message(String message, ChatClientServices client, int receiver, String name) throws RemoteException {
			
			//store messages
			//messages_log.add(message);

			//process messages
			for (ChatClientServices cli : client_list ){
				if(cli.get_client_id() == receiver && cli.get_client_name().equals(name)){
					cli.receive_message(message);
					return;
				}
			}

			client.receive_message("Client Not Available: Recheck ID provided and try again");

	}

	public String join(ChatClientServices client)throws RemoteException{

			if(!clientExist(client)){

				client_list.add(client);
				client.set_client_id(++clientids);
				return "Successful Registration! start chatting";
			}

			return "Unable to join! Try Again Later";

	}


	public String leave(ChatClientServices client)throws RemoteException{

				client_list.remove(client);
				return "Successfully logged out";
			//return "Something weird happened try Again";

	}


	public boolean clientExist(ChatClientServices client){

			for(ChatClientServices cli : client_list){

				if(client == cli){
					return true;
				}
			}

			return false;

	}


	public void write_messages_log(List<String> messages){
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {

			for(String mes : messages){
				bw.write(mes+" "+modifiedDateTime+"\n");
			}
			
		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public String history(String start, String end, String date) throws RemoteException{
			System.out.println(date);
			String filename = "medatabase/"+date+ ".md";
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
			    StringBuilder sb = new StringBuilder();
			    Date start_date = sdf.parse(start);
				Date end_date = sdf.parse(end);
			    String line = br.readLine();

			    while (line != null) {
			    	String[] message_date = line.split(" ");
			    	int mess_len = message_date.length;
			    	String date_str = message_date[mess_len-2] +" "+ message_date[mess_len-1];
			    	Date todayDate = sdf.parse(date_str);	
			    																																																																															
			    	if(todayDate.after(start_date) && todayDate.before(end_date)) {
			    		sb.append(line.substring(0, line.length() - 19));
			        	sb.append(System.lineSeparator());
					}
			        
			        line = br.readLine();
			    }
			    String everything = sb.toString();

			    return everything;
			}
			catch(Exception e){
				 System.out.println(e.getMessage());
				 return "History Does not exist";
			}



	}


}

