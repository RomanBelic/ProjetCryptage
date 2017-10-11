package threading;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;
import models.Message;

public class ServerThread extends Thread implements Runnable, ICallback<CommunicationThread> {

	private ServerSocket servSocket;
	private final List<CommunicationThread> lstClients;
	private final IDispatcherService<Message> dispatcherService;
	private final int port;
	private volatile AtomicBoolean isActive;
	
	public List<CommunicationThread> getListClient(){
		return this.lstClients;
	}
	
	public ServerThread(int port, List<CommunicationThread>lstClients, IDispatcherService<Message> dispatcherService){
		this.lstClients = lstClients;
		this.dispatcherService = dispatcherService;
		this.port = port;
		isActive = new AtomicBoolean(false);
	}
	
	@Override
	public synchronized void start() {
		if (isActive.get())
			return;
		super.start();
		isActive.set(true);
	}
	
	@Override
	public void run() {
		super.run();
		setPriority(MIN_PRIORITY);
		try {
			servSocket = new ServerSocket(port);
		}catch(Exception e){
			System.err.print(e.getMessage());
			e.printStackTrace();
		}
		while(isActive.get()){
			try {
				Socket clSocket = servSocket.accept();
				CommunicationThread commThread = new CommunicationThread(dispatcherService, clSocket);
				commThread.start();
				lstClients.add(commThread);
			}catch(Exception e){
				System.err.print(e.getMessage());
				e.printStackTrace();
			}
		}
		stopServer();
	}
	
	@Override
	public void onCalledBack(CommunicationThread commThread) {
		lstClients.remove(commThread);
	}
		
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		servSocket.close();
	}
	
	public void stopServer(){
		if (!isActive.get())
			return;
		isActive.set(false);
		try{
			servSocket.close();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		for (CommunicationThread commThread : lstClients){
			commThread.closeSocket();
		}
		lstClients.clear();
	}
}
