package lms;

import javax.swing.JFrame;

public class LFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private LMainPanel lMainPanel;
	
	public LFrame() {
		// set attributes
		this.setLocation(400, 100);
		this.setSize(400, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// set components
		this.lMainPanel = new LMainPanel();
		this.add(this.lMainPanel);
	}

}
