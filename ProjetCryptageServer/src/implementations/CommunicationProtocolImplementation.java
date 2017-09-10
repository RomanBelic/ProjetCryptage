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
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public CommunicationProtocolImplementation(CommunicationThread commThread){
		this.commThread = commThread;
	}
	
	@Override
	public void communicate(IDispatcherService dispatcherService) {
		try {
			ois = new ObjectInputStream(commThread.getSocket().getInputStream());
			oos = new ObjectOutputStream(commThread.getSocket().getOutputStream());
			Message msg;
			while((msg = (Message) ois.readObject()) != null){
				resolveData(msg, oos, ois);
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}finally {
			closeCommunication();
		}
	}
	
	@Override
	public void sendMessage(Message message) {
		try {
			oos.writeObject(message);
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void closeCommunication() {
		try {
			if (oos != null)
				oos.close();
			if (ois != null)
				ois.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	private void resolveData(Message msg, ObjectOutputStream output, ObjectInputStream input) throws IOException{
		long packets = msg.getPackets();
		if ((packets & Communication.F_AskChallenge) == Communication.F_AskChallenge){ 
			msg.setPackets(packets | Communication.F_AcceptChallenge);
			msg.setData(new String("test_secret").getBytes());
			output.writeObject(msg);
			output.flush();
		}
		else if ((packets | Communication.F_AskChallenge | Communication.F_AcceptChallenge) == (Communication.F_AskChallenge | Communication.F_AcceptChallenge)){ 
			System.out.println(new String(msg.getData(), "UTF-8"));
		}
		else {
			System.out.println("unknown packet");
		}
		
	}

}
