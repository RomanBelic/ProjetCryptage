package implementations;

import dao.ClientService;
import interfaces.Ciphering.IHashable;
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
	private final IHashable hasher;
	private Client client;
	private String secretHash;
	
	public ResponseProcessorImplementation(IDispatcherService dispatcherService, ICommunicationProtocol<Message> commProtocol, 
			CommunicationThread sender){
		this.dispatcherService = dispatcherService;
		this.commProtocol = commProtocol;
		this.sender = sender;
		this.clientService = new ClientService();
		this.hasher = new HashImplementation();
		this.client = null;
		this.secretHash = null;
	}
	
	@Override
	public void onCalledBack(Message msg) {
		long packets = msg.getPackets();
		if ((packets | Communication.F_AskChallenge) == Communication.F_AskChallenge){ 
			//Ici envoyer code secret
			String srvChallengeKey = hasher.generateSecretKeyString(10);
			secretHash = hasher.createHashString(srvChallengeKey);
			msg.setPackets(Communication.F_AcceptChallenge);
			msg.setMessage(srvChallengeKey);
			commProtocol.sendResponse(msg);
		}
		else if ((packets |  Communication.F_AcceptChallenge) == Communication.F_AcceptChallenge){ 
			//Ici gerer login
			String[] split = msg.getMessage().split(";");
			String clSecretHash = split.length > 0 ? split[0] : null;
			if (secretHash != null && secretHash.equals(clSecretHash)){
				String login = split.length > 1 ?  split[1] : null;
				String pass = split.length > 2 ?  split[2] : null;
				client = clientService.getClient(login, pass);
				String msgContent = (client != null) ? String.valueOf(Communication.OK) : String.valueOf(Communication.No_Content);
				msg.setMessage(msgContent);
				msg.setPackets(Communication.F_PassedChallenge);
				commProtocol.sendResponse(msg);
			}
		}
		else if ((packets | Communication.F_PassedChallenge | Communication.F_SentMsg) == (Communication.F_PassedChallenge | Communication.F_SentMsg)){
			//Ici gerer message text ou fichier
			if (client != null){
				msg.setSenderName(client.getName());
				dispatcherService.dispatchMessage(commProtocol, sender, msg);
			}
		}	
		else if ((packets | Communication.F_AskInscription) == Communication.F_AskInscription){
			//Ici gerer l'inscription
			String[] split = msg.getMessage().split(";");
			String login = split.length > 0 ? split[0] : null;
			String pass = split.length > 1 ?  split[1] : null;
			String name = split.length > 2 ?  split[2] : null;
			int response = clientService.insertClient(login, pass, name);
			msg.setMessage(String.valueOf(response));
			commProtocol.sendResponse(msg);
		}
		else if ((packets & Communication.F_ShutDown) == Communication.F_ShutDown){
			//Deconnexion
			commProtocol.closeSocket();
		}
		else {
			System.out.println("unknown packet");
		}	
	}

}
