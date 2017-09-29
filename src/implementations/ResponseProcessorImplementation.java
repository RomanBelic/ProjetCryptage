package implementations;

import dao.ClientService;
import interfaces.Ciphering.IHashable;
import interfaces.Communication;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import interfaces.Communication.IResponseProcessor;
import models.Client;
import models.Message;
import models.Upload;

public class ResponseProcessorImplementation implements IResponseProcessor<Message> {

	private final ICommunicationProtocol<Message> commProtocol;
	private final ClientService clientService;
	private final IHashable hasher;
	private Client client;
	private String secretHash;
	
	public ResponseProcessorImplementation(ICommunicationProtocol<Message> commProtocol){
		this.commProtocol = commProtocol;
		this.clientService = new ClientService();
		this.hasher = new HashImplementation();
		this.client = null;
		this.secretHash = null;
	}
	
	@Override
	public void processResponse (Message msg, IDispatcherService<Message> dispatcherService) {
		long packets = msg.getPackets();
		System.out.println("received");
		if ((packets | Communication.F_AskChallenge) == Communication.F_AskChallenge){ 
			//Ici envoyer code secret
			String srvChallengeKey = hasher.generateSecretKeyString(10);
			secretHash = hasher.createHashString(srvChallengeKey);
			msg.setPackets(Communication.F_AcceptChallenge);
			msg.setMessage(srvChallengeKey);
			msg.setCode(Communication.Accept);
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
				msg.setCode((client != null) ? Communication.OK : Communication.No_Content);
				msg.setPackets(Communication.F_PassedChallenge);
				commProtocol.sendResponse(msg);
			}else {
				msg.setCode(Communication.Unauthorized);
				commProtocol.sendResponse(msg);
			}
		}
		else if ((packets | Communication.F_PassedChallenge | Communication.F_SentMsg) == (Communication.F_PassedChallenge | Communication.F_SentMsg)){
			//Ici gerer message text ou fichier
			if (client == null)
				return;
			if (msg.getClass() == Message.class){
				msg.setSenderName(client.getName());
				dispatcherService.dispatchMessage(commProtocol, msg);
			}
			else if (msg.getClass() == Upload.class){
				Upload upload = new Upload();
				upload = (Upload)msg;
				dispatcherService.uploadFile(upload);
			}
		}	
		else if ((packets | Communication.F_AskInscription) == Communication.F_AskInscription){
			//Ici gerer l'inscription
			String[] split = msg.getMessage().split(";");
			String login = split.length > 0 ? split[0] : null;
			String pass = split.length > 1 ?  split[1] : null;
			String name = split.length > 2 ?  split[2] : null;
			int code = clientService.insertClient(login, pass, name);
			msg.setCode(code);
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
