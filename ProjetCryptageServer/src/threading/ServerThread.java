package threading;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;

public class ServerThread extends Thread implements Runnable, ICallback<CommunicationThread> {

	private ServerSocket servSocket;
	private volatile AtomicBoolean isActive = new AtomicBoolean(false);
	private final List<CommunicationThread> lstClients;
	private final IDispatcherService dispatcherService;
	
	public List<CommunicationThread> getListClient(){
		return this.lstClients;
	}
	
	public boolean getIsActive(){
		return this.isActive.get();
	}
	
	public ServerThread(int port, List<CommunicationThread>lstClients, IDispatcherService dispatcherService){
		this.lstClients = lstClients;
		this.dispatcherService = dispatcherService;
		try {
			servSocket = new ServerSocket (port);
			isActive.set(true);
		}catch(Exception e){
			System.err.print(e.getMessage());
		}
	}

	@Override
	public void run() {
		while(isActive.get()){
			try {
				Socket clSocket = servSocket.accept();
				CommunicationThread commThread = new CommunicationThread(this, dispatcherService, clSocket);
				commThread.start();
				lstClients.add(commThread);
			}catch(Exception e){
				System.err.print(e.getMessage());
			}
		}
	}
	
	@Override
	public void onCalledBack(CommunicationThread commThread) {
		lstClients.remove(commThread);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		servSocket.close();
		for (CommunicationThread commThread : lstClients){
			commThread.closeSocket();
		}
		lstClients.clear();
	}
	
	public void stopServer(){
		isActive.set(false);
		try{
			servSocket.close();
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		for (CommunicationThread commThread : lstClients){
			commThread.closeSocket();
		}
		lstClients.clear();
	}
}
