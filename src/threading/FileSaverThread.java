package threading;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import implementations.FileEncryptorImplementation;
import interfaces.Patterns.ICallback;
import interfaces.Patterns.IDelegate;
import models.Upload;

public class FileSaverThread extends Thread implements Runnable {

	private volatile AtomicBoolean isActive;
	private final Vector<Upload> vect;
	private final IDelegate<String, Upload> isaver;
	private final ICallback<String> callBack;
	
	public FileSaverThread(ICallback<String> callBack){
		this.vect = new Vector<>(64);
		this.isActive = new AtomicBoolean(true);
		this.isaver = new FileEncryptorImplementation("Uploads");
		this.callBack = callBack;
	}
	
	public boolean getIsActive(){
		return this.isActive.get();
	}
	
	@Override
	public void run() {
		super.run();
		Upload upload;
		try {
			while(isActive.get()){
				synchronized(this){
					while(!vect.iterator().hasNext())
						wait();
				}
				upload = vect.iterator().next();
				callBack.onCalledBack(isaver.action(upload));
				removeFirst();
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	public synchronized void appendToQueue(Upload upload){
		if (this.isActive.get()){
			vect.addElement(upload);
			this.notify();
		}
	}
	
	private void removeFirst(){
		if (vect.size() > 0)
			vect.remove(0);
	}
	
	public synchronized void stopSaver(){
		isActive.set(false);
		vect.clear();
		this.notify();
	}
	
	

}
