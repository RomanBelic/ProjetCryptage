package main;

import java.awt.EventQueue;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import implementations.FileDecryptorImplementation;

public class Run {

	public static void main(String[] args) throws InvalidAlgorithmParameterException, UnsupportedEncodingException {
		EventQueue.invokeLater(() -> {
			FileDecryptorImplementation decryptor = new FileDecryptorImplementation();
			File file = new File("Uploads/test.txt");
			decryptor.action(file);
		});
		// TODO Auto-generated method stub
		//ServerThread server = new ServerThread(8888);
		//server.start();
	}

}
