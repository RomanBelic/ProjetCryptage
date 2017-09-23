package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;

public class RegisterGUILogic extends AbstractUILogic<RegisterGUI> implements IRegisterGUI{

	public RegisterGUILogic(RegisterGUI ui){
		super(ui);
	}
	
	@Override
	public void onRegisterButtonClick(ActionEvent e, JComponent sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExitButtonClick(ActionEvent e, JComponent sender) {
		// TODO Auto-generated method stub
		ui.setVisible(false);
	}

	@Override
	public void OnWindowStart(Component sender, Object... args) {
		ui.setVisible(true);
		
	}

}
