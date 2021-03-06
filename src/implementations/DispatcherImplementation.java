package implementations;

import java.util.List;

import interfaces.Communication.ICommunicationProtocol;
import interfaces.Communication.IDispatcherService;
import models.Message;
import models.Upload;
import threading.CommunicationThread;
import threading.FileSaverThread;

public class DispatcherImplementation implements IDispatcherService<Message> {

	private final FileSaverThread fileSaver;
	private final List<CommunicationThread> lstClients;
	
	public DispatcherImplementation(List<CommunicationThread> lstClients, FileSaverThread fileSaver){
		this.lstClients = lstClients;
		this.fileSaver = fileSaver;
	}
	
	@Override
	public void dispatchMessage(ICommunicationProtocol<Message> protocol, Message message) {
		for(CommunicationThread receiver : lstClients){
			receiver.sendMessage(message);		
		}	
	}

	@Override
	public void uploadFile(Upload upload) {
		if (fileSaver.isActive()){
			fileSaver.appendToQueue(upload);
		}
	}
}
