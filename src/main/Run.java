package main;

import java.awt.EventQueue;

import ui.MainFrame;

public class Run {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			new MainFrame();
		});		
	}														  
}
