import java.rmi.*; 

public interface ChatClientServices extends Remote{
	
	public void receive_message(String message) throws RemoteException;
	public void set_client_id(int id) throws RemoteException;
	public int get_client_id() throws RemoteException;
	public void set_client_name(String name) throws RemoteException;
	public String get_client_name() throws RemoteException;

}