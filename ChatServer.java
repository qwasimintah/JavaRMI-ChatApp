
import java.rmi.*; 
import java.rmi.server.*; 
import java.rmi.registry.*;

public class ChatServer {

  public static void  main(String [] args) {
	  try {
		  // Create a Hello remote object
	    ChatServerServicesImpl services = new ChatServerServicesImpl();
	    ChatServerServices service = (ChatServerServices) UnicastRemoteObject.exportObject(services, 0);

	    // Register the remote object in RMI registry with a given identifier
	    Registry registry= LocateRegistry.getRegistry(); 
	    registry.bind("ChatServices", service);
	    System.out.println("**************Welcome to Chaley Chat Server!*************************");
	   
	    System.out.println("*********************************************************************");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }
}
