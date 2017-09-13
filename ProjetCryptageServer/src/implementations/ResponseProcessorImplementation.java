package implementations;

import interfaces.Communication;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;
import models.Message;
import threading.CommunicationThread;

public class ResponseProcessorImplementation implements ICallback<Message> {

	private final IDispatcherService dispatcherService;
	private final ICommunicationProtocol<Message> commProtocol;
	private final CommunicationThread sender;
	
	public ResponseProcessorImplementation(IDispatcherService dispatcherService, ICommunicationProtocol<Message> commProtocol, CommunicationThread sender){
		this.dispatcherService = dispatcherService;
		this.commProtocol = commProtocol;
		this.sender = sender;
	}
	
	@Override
	public void onCalledBack(Message msg) {
		long packets = msg.getPackets();
		System.out.print("msg");
		if ((packets & Communication.F_AskChallenge) == Communication.F_AskChallenge){ 
			//Ici envoyer code secret
			
		}
		else if ((packets | Communication.F_AskChallenge | Communication.F_AcceptChallenge) == 
				(Communication.F_AskChallenge | Communication.F_AcceptChallenge)){ 
			//Ici verif login + mdp
		}
		else if ((packets & Communication.F_IsLogged | Communication.F_SentMsg) == (Communication.F_IsLogged | Communication.F_SentMsg)){
			//Ici gerer message text ou fichier
			dispatcherService.dispatchMessage(commProtocol, sender, msg);
		}	
		else if ((packets & Communication.F_ShutDown) == Communication.F_ShutDown){
			//Deconnexion
			
		}
		else {
			System.out.println("unknown packet");
		}	
	}

}
