
import java.rmi.*;
import java.util.*;

public class ChatClientServicesImpl implements ChatClientServices {

	private int client_id;
	private String client_name;

	public void receive_message(String message) throws RemoteException {
		System.out.println(message);
		
	}

	public void set_client_id(int id) throws RemoteException{

		this.client_id = id;
	}
	public int get_client_id() throws RemoteException{

		return client_id;
	}

	public void set_client_name(String name) throws RemoteException{

		this.client_name = name;
	}
	public String get_client_name() throws RemoteException{

		return client_name;
	}
}

