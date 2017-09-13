package threading;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileSaverThread extends Thread implements Runnable {

	private volatile AtomicBoolean isActive;
	private final List<byte[]> vect;
	
	public FileSaverThread(){
		this.isActive = new AtomicBoolean(false);
		this.vect = new ArrayList<>(64);
	}
	
	@Override
	public void run() {
		isActive.set(true);
		byte[] data;
		try {
			while(isActive.get()){
				//synchronized(this){
					//this.wait();
					if (vect.iterator().hasNext()){
						data = vect.iterator().next();
						try (FileOutputStream fs = new FileOutputStream("test",false)){
							fs.write(data);
						}
					}
					//removeFirstQueue();
					//System.out.println("file saved");
				//}
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void insertToQueue(byte[]data){
		this.notify();
		//vect.insertElementAt(data, 0);
		/*synchronized(this){
			this.notify();
		}*/
	}
	
	public synchronized void removeFirstQueue(){
		if (vect.size() > 0)
			vect.remove(0);
		this.notify();
	}
	
	public synchronized void stopSaver(){
		this.notify();
		//System.out.println(isActive);
		this.isActive.set(false);
		//this.notify();
		//vect.clear();
	}
	
	

}
