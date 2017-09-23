package GUI;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RegisterGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7289001126939436994L;
	protected final JTextField userloginJTextfield;
	protected final JPasswordField passwordJPassword;
	protected final JButton buttonRegister;
	protected final JTextField usernameJTextfield;
	protected final JButton buttonExit;
	private final IRegisterGUI guilogic;
	
	public RegisterGUI(){
		super("User's register");
		JPanel panel = new JPanel();
		JLabel usernameLabel = new JLabel("Identifiant : ");
		usernameLabel.setBounds(12, 10, 70, 30);
		JLabel passwordLabel = new JLabel("Mot de passe : ");
		passwordLabel.setBounds(12, 53, 89, 30);
		userloginJTextfield = new JTextField("");
		userloginJTextfield.setBounds(119, 13, 100, 24);
		passwordJPassword = new JPasswordField("");
		passwordJPassword.setBounds(119, 50, 100, 24);
		buttonRegister = new JButton("S'enregistrer");
		buttonRegister.setBounds(12, 147, 107, 25);
		userloginJTextfield.setPreferredSize(new Dimension(100, 24));
		passwordJPassword.setPreferredSize(new Dimension(100, 24));
		panel.setLayout(null);
		panel.add(usernameLabel);
		panel.add(userloginJTextfield);
		panel.add(passwordLabel);
		panel.add(passwordJPassword);
		panel.add(buttonRegister);
		setContentPane(panel);
		setSize(270, 267);	
	    JLabel lblNom = new JLabel("Nom : ");
	    lblNom.setBounds(12, 84, 70, 30);
	    panel.add(lblNom);
	    buttonExit = new JButton("Annuler");
	    buttonExit.setBounds(131, 147, 107, 25);
	    panel.add(buttonExit);
	    usernameJTextfield = new JTextField("");
	    usernameJTextfield.setPreferredSize(new Dimension(100, 24));
	    usernameJTextfield.setBounds(119, 87, 100, 24);
	    panel.add(usernameJTextfield);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);			// Stop running when we close the window
		setLocationRelativeTo(null);
		guilogic = new RegisterGUILogic(this);
		attachActions(guilogic);
		guilogic.OnWindowStart(this, new Object(){});
	}
	
	private void attachActions(IRegisterGUI guiLogic){
		buttonRegister.addActionListener((e) -> guiLogic.onRegisterButtonClick(e, buttonRegister));
		buttonExit.addActionListener((e) -> guiLogic.onExitButtonClick(e, buttonExit));
	}

}
