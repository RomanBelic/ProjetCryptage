package threading;

import java.net.Socket;

import implementations.CommunicationProtocolImplementation;
import interfaces.Communication;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;
import models.Message;

public class CommunicationThread extends Thread implements Runnable, ICallback<Message>{

	private final ServerThread server;
	private final Socket clSocket;
	private final ICommunicationProtocol<Message> commProtocol;
	private final IDispatcherService dispatcherService;
	
	public Socket getSocket(){
		return clSocket;
	}
	
	public ICommunicationProtocol<Message> getCommunicationProtocol(){
		return commProtocol;
	}

	public CommunicationThread(ServerThread server, Socket clSocket){
		this.server = server;
		this.clSocket = clSocket;
		this.dispatcherService = server.getDispatcherService();
		this.commProtocol = new CommunicationProtocolImplementation(this.clSocket, this);
	}
	
	@Override
	public void run() {
		commProtocol.getResponse();
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

	@Override
	public void onCalledBack(Message msg) {
		long packets = msg.getPackets();
		if ((packets & Communication.F_AskChallenge) == Communication.F_AskChallenge){ 
			//Ici envoyer code secret
			
		}
		else if ((packets | Communication.F_AskChallenge | Communication.F_AcceptChallenge) == 
				(Communication.F_AskChallenge | Communication.F_AcceptChallenge)){ 
			//Ici verif login + mdp
		}
		else if ((packets & Communication.F_IsLogged | Communication.F_SentMsg) == (Communication.F_IsLogged | Communication.F_SentMsg)){
			//Ici gerer message text ou fichier
			dispatcherService.dispatchMessage(commProtocol, this, msg);
		}	
		else if ((packets & Communication.F_ShutDown) == Communication.F_ShutDown){
			//Deconnexion
			
		}
		else {
			System.out.println("unknown packet");
		}		
	}	
}
