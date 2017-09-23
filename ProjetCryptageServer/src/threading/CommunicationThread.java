package threading;

import java.net.Socket;

import implementations.CommunicationProtocolImplementation;
import implementations.ResponseProcessorImplementation;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;
import models.Message;

public class CommunicationThread extends Thread implements Runnable {

	private final ServerThread server;
	private final Socket clSocket;
	private final ICommunicationProtocol<Message> commProtocol;
	private final ICallback<Message> callback;
	
	public Socket getSocket(){
		return clSocket;
	}
	
	public ICommunicationProtocol<Message> getCommunicationProtocol(){
		return commProtocol;
	}

	public CommunicationThread(ServerThread server, IDispatcherService dispatcherService, Socket clSocket){
		this.server = server;
		this.clSocket = clSocket;
		this.commProtocol = new CommunicationProtocolImplementation(clSocket);
		this.callback = new ResponseProcessorImplementation(dispatcherService, commProtocol, this);
	}
	
	@Override
	public void run() {
		commProtocol.getResponse(callback);
		server.onCalledBack(this);
		closeSocket();
	}
	
	public void closeSocket(){
		commProtocol.closeSocket();
	}
}
