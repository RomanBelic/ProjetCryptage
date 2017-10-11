package ui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import implementations.DispatcherImplementation;
import implementations.FileDecryptorImplementation;
import interfaces.Communication.IDispatcherService;
import interfaces.Patterns.ICallback;
import models.Message;
import threading.CommunicationThread;
import threading.FileSaverThread;
import threading.ServerThread;
import utils.Utils;

public class MainFrameLogic implements IMainFrame {

	private final ICallback<String> onFileSavedCallBack;
	private final List<CommunicationThread> lstClients;
	private final FileDecryptorImplementation fileDecryptor;
	private IDispatcherService<Message> dispatcherService;
	private ServerThread serverThread;
	private FileSaverThread fileSaverThread;
	private final MainFrame ui;
	
	public MainFrameLogic(MainFrame ui){
		this.ui = ui; 
		this.fileDecryptor = new FileDecryptorImplementation("Decrypted");
		this.onFileSavedCallBack = this::onSavedEncryptedFile;
		this.lstClients = new ArrayList<>(64);
		initUI();
	}
	
	private void initUI(){
		ui.setVisible(true);
		loadFilesToComboBox(ui.fileBox);	
	}
	
	private void loadFilesToComboBox(JComboBox<ComboBoxItem> jcb){
		File dir = new File("Uploads");
		if (dir.listFiles() == null || !dir.exists())
			return;
		
		for(File f : dir.listFiles()){
			jcb.addItem(new ComboBoxItem(f.getName(), f.getAbsolutePath()));
		}
	}
	
	@Override
	public void onBtnDecryptClick(ActionEvent e, JComponent sender){
		int index = ui.fileBox.getSelectedIndex();
		if (index <= -1)
			return;
		
		fileDecryptor.action(ui.fileBox.getItemAt(index).getValue());
	}
	
	@Override
	public void onBtnStartClick(ActionEvent e, JComponent sender){ 
		fileSaverThread = new FileSaverThread(onFileSavedCallBack);
		dispatcherService = new DispatcherImplementation(lstClients, fileSaverThread);
		serverThread = new ServerThread(8888, lstClients, dispatcherService);
		serverThread.start();
		fileSaverThread.start();
		System.out.println("Server Started");
		System.out.println("File saver Started");
	}
	
	private void onSavedEncryptedFile(String filePath){
		String fileName = Utils.resolveFileName(filePath);
		ui.fileBox.addItem(new ComboBoxItem(fileName, filePath));
	}

	@Override
	public void onBtnStopClick(ActionEvent e, JComponent sender) {
		if (serverThread == null)
			return;
		serverThread.stopServer();
		System.out.println("Server stopped");
		
		if (fileSaverThread == null)
			return;
		fileSaverThread.stopSaver();
		System.out.println("File saver stopped");
	}
	
	
}
