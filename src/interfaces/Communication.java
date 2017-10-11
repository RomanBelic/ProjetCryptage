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
		void processResponse(T arg, IDispatcherService<T> dispatcherService);
	}
	
	public static final long F_SentMsg = 8;
	public static final long F_Fin = 16;
	public static final long F_ShutDown = 32;
	public static final long F_PassedChallenge = 64;
	public static final long F_AskChallenge = 128;
	public static final long F_AcceptChallenge = 256;
	public static final long F_AskInscription = 512;
	public static final long F_AcceptInscription = 512;
	
	public static final int No_Content = 204;
	public static final int OK = 200;
	public static final int Created = 201;
	public static final int Accept = 202;
	public static final int Conflict = 409;
	public static final int InternalError = 500;
	public static final int Unauthorized = 401;  
}
