package threading;

import java.net.Socket;

import implementations.CommunicationProtocolImplementation;
import interfaces.Communication.ICommunicationProtocol;

public class CommunicationThread extends Thread implements Runnable {

	private final ServerThread server;
	private final Socket clSocket;
	private final ICommunicationProtocol commProtocol;
	
	public Socket getSocket(){
		return clSocket;
	}
	
	public ICommunicationProtocol getCommunicationProtocol(){
		return commProtocol;
	}

	public CommunicationThread(ServerThread server, Socket clSocket){
		this.server = server;
		this.clSocket = clSocket;
		this.commProtocol = new CommunicationProtocolImplementation(this, server.getDispatcherService());
	}
	
	@Override
	public void run() {
		commProtocol.communicate();
		server.onCalledBack(this);
		closeSocket();
	}
	
	public void closeSocket(){
		try {
			if (clSocket != null)
				clSocket.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
