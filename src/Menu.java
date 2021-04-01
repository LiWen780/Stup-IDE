package stupIDE;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Menu extends JMenuBar{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Menu()
	{
		setBackground(Color.DARK_GRAY);
	}
	
	public void addItem(JMenu bar)
	{
		bar.setForeground(Color.WHITE);
		add(bar);
	}
}
