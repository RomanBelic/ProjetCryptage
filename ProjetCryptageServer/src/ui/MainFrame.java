package ui;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8808883923263763897L;
	
	protected JComboBox<File> fileBox;
	protected JButton btnDecrypt;
	
	public MainFrame() {
		getContentPane().setLayout(null);
		
		fileBox = new JComboBox<>();
		fileBox.setBounds(12, 13, 150, 30);
		getContentPane().add(fileBox);
		btnDecrypt = new JButton("D\u00E9crypter");
		btnDecrypt.setBounds(174, 13, 100, 30);
		getContentPane().add(btnDecrypt);
		initActions();
	}
	
	protected void onBtnDecryptClick(ActionEvent e, JComponent sender){	}
	
	private void initActions (){
		btnDecrypt.addActionListener((ActionEvent e) -> onBtnDecryptClick (e, btnDecrypt));
	}

}
