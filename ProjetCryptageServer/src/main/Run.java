package main;

import threading.ServerThread;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerThread server = new ServerThread(8888);
		server.start();
		/*long i = 32;
		long y = 64;
		long x = 128;
		Message mm = new Message();
		mm.setPackets(mm.getPackets() | i);
		mm.setPackets(mm.getPackets() | y);
		System.out.println((mm.getPackets() | i | y) == (i | y));*/
	}

}
