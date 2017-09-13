package main;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.zip.CRC32;

import implementations.CBCCypher;
import models.Message;
import threading.FileSaverThread;
import threading.ServerThread;

public class Run {

	public static void main(String[] args) throws InvalidAlgorithmParameterException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		//ServerThread server = new ServerThread(8888);
		//server.start();
		FileSaverThread saver = new FileSaverThread();
		saver.start();
		byte[] data = new String("test555").getBytes();
		byte[] data2 = new String("test6").getBytes();
		//saver.insertToQueue(data);
		//saver.insertToQueue(data2);
		saver.stopSaver();
		/*
		long i = 32;
		long y = 64;
		long x = 128;
		Message mm = new Message();
		mm.setPackets(mm.getPackets() | i | y | x);
		System.out.println((mm.getPackets() & i | x) == (i | x));
		
		CBCCypher cyp = new CBCCypher("key", "vec");
		byte[] enc = cyp.encrypt("testtest".getBytes());
		System.out.println(new String(enc,"UTF-8"));
		byte[] dec = cyp.decrypt(enc);
		System.out.println(new String(dec,"UTF-8"));*/
	}

}
