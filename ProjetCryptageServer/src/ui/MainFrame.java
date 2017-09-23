package ui;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8808883923263763897L;
	
	protected final JComboBox<ComboBoxItem> fileBox;
	protected final JButton btnDecrypt;
	protected final JButton btnStartServer;
	protected final JButton btnStopServer;
	
	public MainFrame() {
		getContentPane().setLayout(null);		
		fileBox = new JComboBox<>();
		fileBox.setBounds(12, 13, 150, 30);
		getContentPane().add(fileBox);
		btnDecrypt = new JButton("D\u00E9crypter");
		btnDecrypt.setBounds(174, 13, 100, 30);
		getContentPane().add(btnDecrypt);
		btnStartServer = new JButton("Start Server");
		btnStartServer.setBounds(240, 212, 120, 25);
		getContentPane().add(btnStartServer);
		
		btnStopServer = new JButton("Stop Server");
		btnStopServer.setBounds(240, 250, 120, 25);
		getContentPane().add(btnStopServer);
		setSize(new Dimension(600, 350));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initActions();
	}
	
	protected void onBtnDecryptClick(ActionEvent e, JComponent sender){	}
	protected void onBtnStartClick(ActionEvent e, JComponent sender){ }
	protected void onBtnStopClick(ActionEvent e, JComponent sender){ }
	
	private void initActions (){
		btnDecrypt.addActionListener((ActionEvent e) -> onBtnDecryptClick (e, btnDecrypt));
		btnStartServer.addActionListener((ActionEvent e) -> onBtnStartClick (e, btnDecrypt));
		btnStopServer.addActionListener((ActionEvent e) -> onBtnStopClick (e, btnDecrypt));
	}
}
