package interfaces;

import models.Upload;

public class Communication {
	
	public interface IDispatcherService<T>{
		void dispatchMessage (ICommunicationProtocol<T> protocol, T message);
		void uploadFile(Upload upload);
	}
	
	public interface ICommunicationProtocol<T>{
		T getResponse();
		void sendResponse(T message);
		void closeSocket();
	}
	
	public interface IResponseProcessor<T> {
		public void processResponse(T arg, IDispatcherService<T> dispatcherService);
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
	public static int Accept = 202;
	public static int Conflict = 409;
	public static int InternalError = 500;
	public static int Unauthorized = 401; 
}
