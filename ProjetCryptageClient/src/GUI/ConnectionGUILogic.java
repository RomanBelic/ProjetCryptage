package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;

public class ConnectionGUILogic extends AbstractUILogic<ConnectionGUI> implements IConnectionGUI{

	public ConnectionGUILogic(ConnectionGUI ui) {
		super(ui);
	}

	@Override
	public void onConnectionButtonClick(ActionEvent e, JComponent sender) {
		
		
	}

	@Override
	public void onRegisterButtonClick(ActionEvent e, JComponent sender) {
	
		
	}

	@Override
	public void onCloseWindowButtonClick(ActionEvent e, JComponent sender) {
		ui.dispose();
		System.exit(0);
	}

	@Override
	public void OnWindowStart(Component sender, Object... args) {
		ui.setVisible(true);
	}
}
