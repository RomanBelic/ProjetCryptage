package GUI;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

public interface IConnectionGUI extends IWindowGUI {
	void onConnectionButtonClick(ActionEvent e, JComponent sender);
	void onRegisterButtonClick(ActionEvent e, JComponent sender);
	void onCloseWindowButtonClick(ActionEvent e, JComponent sender);
}
