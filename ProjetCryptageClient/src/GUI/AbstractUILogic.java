package GUI;

import javax.swing.JFrame;

public abstract class AbstractUILogic<T extends JFrame> {
	
	protected final T ui;
	public AbstractUILogic(T ui){
		this.ui = ui;
	}
}
