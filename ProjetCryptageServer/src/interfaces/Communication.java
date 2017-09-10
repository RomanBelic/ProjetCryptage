package interfaces;

import models.Message;
import threading.CommunicationThread;

public class Communication {
	
	public interface IDispatcherService{
		void dispatchMessage (ICommunicationProtocol protocol, CommunicationThread sender, Message message);
	}
	
	public interface ICommunicationProtocol{
		void communicate(IDispatcherService dispatcherService);
		void sendMessage(Message message);
		void closeCommunication();
	}
	
	public static long F_SentMsg = 8;
	public static long F_Fin = 16;
	public static long F_ShutDown = 32;
	public static long F_IsLogged = 64;
	public static long F_AskChallenge = 128;
	public static long F_AcceptChallenge = 256;
}
