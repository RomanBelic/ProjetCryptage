package GUI;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

public interface IRegisterGUI extends IWindowGUI {
	void onRegisterButtonClick(ActionEvent e, JComponent sender);
	void onExitButtonClick(ActionEvent e, JComponent sender);
}
