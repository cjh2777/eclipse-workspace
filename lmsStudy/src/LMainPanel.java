package lms;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LMainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private LSelectionPanel lSelectionPanel;
	private LControlPanel lControlPanel1;
	private LBasket lMiridamgi;
	private LControlPanel lControlPanel2;
	private LBasket lSincheong;
	
	public LMainPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.lSelectionPanel = new LSelectionPanel();
		this.add(this.lSelectionPanel);
		
		this.lControlPanel1 = new LControlPanel();
		this.add(this.lControlPanel1);
		
		this.lMiridamgi = new LBasket();
		this.add(this.lMiridamgi);
		
		this.lControlPanel2 = new LControlPanel();
		this.add(this.lControlPanel2);
		
		this.lSincheong = new LBasket();
		this.add(this.lSincheong);
	}

}
