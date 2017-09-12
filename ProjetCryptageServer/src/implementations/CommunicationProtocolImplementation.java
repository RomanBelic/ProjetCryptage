package implementations;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import interfaces.Communication.ICommunicationProtocol;
import interfaces.Patterns.ICallback;
import models.Message;

public class CommunicationProtocolImplementation implements ICommunicationProtocol<Message> {
	
	private final Socket socket;
	private final ICallback<Message> callback;
	private ObjectOutputStream oos;
	
	public CommunicationProtocolImplementation(Socket socket, ICallback<Message> callback){
		this.socket = socket;
		this.callback = callback;
	}
	
	@Override
	public void getResponse() {
		try(ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())){
			oos = new ObjectOutputStream(socket.getOutputStream());
			Message msg;
			while((msg = (Message) ois.readObject()) != null){
				callback.onCalledBack(msg);
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
	public void sendResponse(Message message) {
		try {
			oos.writeObject(message);
			oos.flush();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

}
