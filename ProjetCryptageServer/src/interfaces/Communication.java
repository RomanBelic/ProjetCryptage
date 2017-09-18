package interfaces;

import interfaces.Patterns.ICallback;
import models.Message;
import models.Upload;
import threading.CommunicationThread;

public class Communication {
	
	public interface IDispatcherService{
		void dispatchMessage (ICommunicationProtocol<Message> protocol, CommunicationThread sender, Message message);
		void uploadFile(Upload upload);
	}
	
	public interface ICommunicationProtocol<T>{
		void getResponse(ICallback<Message> callback);
		void sendResponse(T message);
		void closeSocket();
	}
	
	public static long F_SentMsg = 8;
	public static long F_Fin = 16;
	public static long F_ShutDown = 32;
	public static long F_PassedChallenge = 64;
	public static long F_AskChallenge = 128;
	public static long F_AcceptChallenge = 256;
	public static long F_AskInscription = 512;
	public static long F_AcceptInscription = 512;
	
	public static int No_Content = 204;
	public static int OK = 200;
	public static int Created = 201;
	public static int Conflict = 409;
	public static int InternalError = 500;
}
