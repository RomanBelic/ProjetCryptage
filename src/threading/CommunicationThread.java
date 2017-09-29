package threading;

import java.net.Socket;

import implementations.CommunicationProtocolImplementation;
import implementations.ResponseProcessorImplementation;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import interfaces.Communication.IResponseProcessor;
import models.Message;

public class CommunicationThread extends Thread implements Runnable {

	private final Socket clSocket;
	private final ICommunicationProtocol<Message> commProtocol;
	private final IResponseProcessor<Message> responseProcessor;
	private final IDispatcherService<Message> dispatcherService;
	
	public Socket getSocket(){
		return clSocket;
	}

	public CommunicationThread(IDispatcherService<Message> dispatcherService, Socket clSocket){
		this.clSocket = clSocket;
		this.dispatcherService = dispatcherService;
		this.commProtocol = new CommunicationProtocolImplementation(clSocket);
		this.responseProcessor = new ResponseProcessorImplementation(commProtocol);
	}
	
	@Override
	public void run() {
		Message msg;
		while ((msg = commProtocol.getResponse()) != null){
			responseProcessor.processResponse(msg, dispatcherService);
		}
		closeSocket();
	}
	
	public void closeSocket(){
		commProtocol.closeSocket();
	}
	
	public void sendMessage(Message msg){
		commProtocol.sendResponse(msg);
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		clSocket.close();
	}
}
