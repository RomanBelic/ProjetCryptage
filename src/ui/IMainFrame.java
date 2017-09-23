package ui;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

public interface IMainFrame {
	void onBtnDecryptClick(ActionEvent e, JComponent sender);
	void onBtnStartClick(ActionEvent e, JComponent sender);
	void onBtnStopClick(ActionEvent e, JComponent sender);
}
