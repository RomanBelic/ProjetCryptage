package implementations;

import dao.ClientService;
import interfaces.Communication;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;
import models.Client;
import models.Message;
import threading.CommunicationThread;

public class ResponseProcessorImplementation implements ICallback<Message> {

	private final IDispatcherService dispatcherService;
	private final ICommunicationProtocol<Message> commProtocol;
	private final CommunicationThread sender;
	private final ClientService clientService;
	private Client client;
	
	public ResponseProcessorImplementation(IDispatcherService dispatcherService, ICommunicationProtocol<Message> commProtocol, 
			CommunicationThread sender){
		this.dispatcherService = dispatcherService;
		this.commProtocol = commProtocol;
		this.sender = sender;
		this.clientService = new ClientService();
		this.client = new Client();
	}
	
	@Override
	public void onCalledBack(Message msg) {
		long packets = msg.getPackets();
		if ((packets & Communication.F_AskChallenge) == Communication.F_AskChallenge){ 
			//Ici envoyer code secret
			msg.setPackets(Communication.F_AskChallenge | Communication.F_AcceptChallenge);
			
			
		}
		else if ((packets | Communication.F_AskChallenge | Communication.F_AcceptChallenge) == 
				(Communication.F_AskChallenge | Communication.F_AcceptChallenge)){ 
			//Ici verif login + mdp
			client = clientService.getClient("", "");		
		}
		else if ((packets & Communication.F_IsLogged | Communication.F_SentMsg) == (Communication.F_IsLogged | Communication.F_SentMsg)){
			//Ici gerer message text ou fichier
			if (!client.isEmpty()){
				msg.setSenderName(client.getName());
				dispatcherService.dispatchMessage(commProtocol, sender, msg);
			}
		}	
		else if ((packets & Communication.F_ShutDown) == Communication.F_ShutDown){
			//Deconnexion
			
		}
		else {
			System.out.println("unknown packet");
		}	
	}

}
