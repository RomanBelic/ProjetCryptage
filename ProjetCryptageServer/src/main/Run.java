package main;

import java.awt.EventQueue;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;

import implementations.CBCCypher;
import implementations.HashImplementation;
import models.Message;
import models.Upload;
import threading.FileSaverThread;
import threading.ServerThread;

public class Run {

	public static void main(String[] args) throws InvalidAlgorithmParameterException, UnsupportedEncodingException {
		EventQueue.invokeLater(() -> {
		/*	Object lock = new Object();
			FileSaverThread saver = new FileSaverThread();
			saver.start();

			byte[] data = new String("test555").getBytes();
			byte[] data2 = new String("test6").getBytes();
			Upload upl = new Upload("test", ".txt", data);
			saver.appendToQueue(upl);
			saver.stopSaver();
		*/
			
			HashImplementation hasher = new HashImplementation();
			Random rand = new Random();
			String str = "";
			while(str.length() < 10){
				System.out.println(rand.nextInt(255));
				str += ((char) rand.nextInt(255));
			}
			
			long hash = hasher.createHash(new String("testqsdsdqsd").getBytes());
			System.out.println(hash);
			System.out.println(str);
		});
		// TODO Auto-generated method stub
		//ServerThread server = new ServerThread(8888);
		//server.start();
		//saver.stopSaver();	
	}

}
