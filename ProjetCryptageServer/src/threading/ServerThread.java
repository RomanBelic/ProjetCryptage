package threading;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import implementations.DispatcherImplementation;
import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;

public class ServerThread extends Thread implements Runnable, ICallback<CommunicationThread> {

	private ServerSocket servSocket;
	private volatile AtomicBoolean isActive = new AtomicBoolean(false);
	private final List<CommunicationThread> lstClients;
	private final IDispatcherService dispatcherService;
	
	public IDispatcherService getDispatcherService() {
		return dispatcherService;
	}

	public ServerThread(int port){
		lstClients = new ArrayList<>(64);
		dispatcherService = new DispatcherImplementation(lstClients);
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
				CommunicationThread commThread = new CommunicationThread(this, clSocket);
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
	}
}
