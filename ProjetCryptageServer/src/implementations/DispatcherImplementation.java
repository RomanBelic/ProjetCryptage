package implementations;

import java.util.List;

import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import models.Message;
import threading.CommunicationThread;

public class DispatcherImplementation implements IDispatcherService {

	private final List<CommunicationThread> lstClients;
	
	public DispatcherImplementation(List<CommunicationThread> lstClients){
		this.lstClients = lstClients;
	}
	
	@Override
	public void dispatchMessage(ICommunicationProtocol protocol, CommunicationThread sender, Message message) {
		for(CommunicationThread receiver : lstClients){
			if (!receiver.equals(sender)){
				receiver.getCommunicationProtocol().sendMessage(message);
			}
		}	
	}
}
