package implementations;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import interfaces.Communication.ICommunicationProtocol;
import interfaces.Patterns.ICallback;
import models.Message;

public class CommunicationProtocolImplementation implements ICommunicationProtocol<Message> {
	
	private final Socket socket;
	private ObjectOutputStream oos;
	
	public CommunicationProtocolImplementation(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void getResponse(ICallback<Message> callback) {
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
	public synchronized void sendResponse(Message message) {
		try {
			oos.writeObject(message);
			oos.flush();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public synchronized void closeSocket() {
		// TODO Auto-generated method stub
		try {
			if (oos != null)
				oos.close();
			if (socket != null)
				socket.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	

}
