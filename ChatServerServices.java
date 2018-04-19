
import java.rmi.*; 

public interface ChatServerServices extends Remote{


	public String join(ChatClientServices client)throws RemoteException;
	public void send_message(String message, ChatClientServices client)throws RemoteException;
	public void private_message(String message, ChatClientServices sender, int receiver, String name)throws RemoteException;
	public String leave(ChatClientServices client)throws RemoteException;
	public String history(String start, String end, String date) throws RemoteException;
}