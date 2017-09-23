package main;

import java.awt.EventQueue;

import GUI.ConnectionGUI;

public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(() -> {
			new ConnectionGUI();
		});
	}
}
