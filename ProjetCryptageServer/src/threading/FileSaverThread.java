package threading;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import implementations.FileSaverImplementation;
import interfaces.Patterns.IDelegate;
import models.Upload;

public class FileSaverThread extends Thread implements Runnable {

	private volatile AtomicBoolean isActive;
	private final Vector<Upload> vect;
	private final IDelegate<Void, Upload> isaver;
	
	public FileSaverThread(){
		this.vect = new Vector<>(64);
		this.isActive = new AtomicBoolean(true);
		this.isaver = new FileSaverImplementation("Uploads");
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
				isaver.action(upload);
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
