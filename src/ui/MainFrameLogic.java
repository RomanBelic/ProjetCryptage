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
import threading.CommunicationThread;
import threading.FileSaverThread;
import threading.ServerThread;

public class MainFrameLogic extends MainFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3114397683245521765L;
	private final ICallback<String> onFileSavedCallBack;
	private final List<CommunicationThread> lstClients;
	private final FileDecryptorImplementation fileDecryptor;
	private IDispatcherService dispatcherService;
	private ServerThread serverThread;
	private FileSaverThread fileSaverThread;
	
	public MainFrameLogic(){
		super();
		this.fileDecryptor = new FileDecryptorImplementation("Decrypted");
		this.onFileSavedCallBack = this::onSavedEncryptedFile;
		this.lstClients = new ArrayList<>(64);
		initUI();
	}
	
	private void initUI(){
		super.setVisible(true);
		loadFilesToComboBox(fileBox);	
	}
	
	private void loadFilesToComboBox(JComboBox<ComboBoxItem> jcb){
		File dir = new File("Uploads");
		if (!dir.exists())
			return;
		
		for(File f : dir.listFiles()){
			jcb.addItem(new ComboBoxItem(f.getName(), f.getAbsolutePath()));
		}
	}
	
	@Override
	protected void onBtnDecryptClick(ActionEvent e, JComponent sender){
		int index = fileBox.getSelectedIndex();
		if (index <= -1)
			return;
		
		fileDecryptor.action(fileBox.getItemAt(index).getValue());
	}
	
	@Override
	protected void onBtnStartClick(ActionEvent e, JComponent sender){ 
		fileSaverThread = new FileSaverThread(onFileSavedCallBack);
		dispatcherService = new DispatcherImplementation(lstClients, fileSaverThread);
		serverThread = new ServerThread(8888, lstClients, dispatcherService);
		serverThread.start();
		fileSaverThread.start();
		System.out.println("Server Started");
	}
	
	private void onSavedEncryptedFile(String filePath){
		int subFrom = filePath.lastIndexOf(File.separatorChar) > - 1 ? filePath.lastIndexOf(File.separatorChar) + 1 : 0;
		String fileName = filePath.substring(subFrom, filePath.length());
		fileBox.addItem(new ComboBoxItem(fileName, filePath));
	}

	@Override
	protected void onBtnStopClick(ActionEvent e, JComponent sender) {
		if (serverThread.getIsActive()){
			serverThread.stopServer();
			System.out.println("Server stopped");
		}
		if (fileSaverThread.getIsActive()){
			fileSaverThread.stopSaver();
			System.out.println("File saver stopped");
		}
	}
	
	
}
