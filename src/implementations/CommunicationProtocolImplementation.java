package implementations;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import interfaces.Communication.ICommunicationProtocol;
import models.Message;

public class CommunicationProtocolImplementation implements ICommunicationProtocol<Message> {
	
	private final Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public CommunicationProtocolImplementation(Socket socket){
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public Message getResponse() {
		try {
			return (Message) ois.readObject();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public void sendResponse(Message message) {
		if (oos == null || socket == null || socket.isClosed())
			return;
		try {
			oos.writeObject(message);
			oos.flush();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void closeSocket() {
		// TODO Auto-generated method stub
		try {
			if (oos != null)
				oos.close();
			if (ois != null)
				ois.close();
			if (socket != null)
				socket.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		oos.close();
		ois.close();
		socket.close();
	}
}
