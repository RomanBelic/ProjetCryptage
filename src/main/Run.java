package main;

import java.awt.EventQueue;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

import ui.MainFrame;

public class Run {

	public static void main(String[] args) throws InvalidAlgorithmParameterException, UnsupportedEncodingException {
		EventQueue.invokeLater(() -> {
			//TODO Auto-generated method stub
			new MainFrame();
		});		
	}														  
}
