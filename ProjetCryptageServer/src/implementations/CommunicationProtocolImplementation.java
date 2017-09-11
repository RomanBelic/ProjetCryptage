package implementations;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import interfaces.Communication;
import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import models.Message;
import threading.CommunicationThread;

public class CommunicationProtocolImplementation implements ICommunicationProtocol {
	
	private final CommunicationThread commThread;
	private final IDispatcherService dispatcherService;
	private ObjectOutputStream oos;
	
	public CommunicationProtocolImplementation(CommunicationThread commThread, IDispatcherService dispatcherService){
		this.commThread = commThread;
		this.dispatcherService = dispatcherService;
	}
	
	@Override
	public void communicate() {
		try(ObjectInputStream ois = new ObjectInputStream(commThread.getSocket().getInputStream())){
			oos = new ObjectOutputStream(commThread.getSocket().getOutputStream());
			Message msg;
			while((msg = (Message) ois.readObject()) != null){
				resolveData(msg, oos, ois);
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally {
			try {
				if (oos != null) oos.close();
			}catch(Exception e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	@Override
	public void sendMessage(Message message) {
		try {
			oos.writeObject(message);
			oos.flush();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	private void resolveData(Message msg, ObjectOutputStream output, ObjectInputStream input) throws IOException{
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
			dispatcherService.dispatchMessage(this, commThread, msg);
		}	
		else if ((packets & Communication.F_ShutDown) == Communication.F_ShutDown){
			//Deconnexion
			
		}
		else {
			System.out.println("unknown packet");
		}
		
	}

}
