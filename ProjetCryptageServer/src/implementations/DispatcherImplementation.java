package implementations;

import java.util.List;

import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import models.Message;
import models.Upload;
import threading.CommunicationThread;

public class DispatcherImplementation implements IDispatcherService {

	private final List<CommunicationThread> lstClients;
	
	public DispatcherImplementation(List<CommunicationThread> lstClients){
		this.lstClients = lstClients;
	}
	
	@Override
	public void dispatchMessage(ICommunicationProtocol<Message> protocol, CommunicationThread sender, Message message) {
		for(CommunicationThread receiver : lstClients){
			receiver.getCommunicationProtocol().sendResponse(message);		
		}	
	}

	@Override
	public void uploadFile(Upload upload) {
		// TODO Auto-generated method stub
		
	}
}
