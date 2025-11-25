package lms;

import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import lms.LConstants.EGangjwa;

public class LMainPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private LSelectionPanel lSelectionPanel;
    private LControlPanel lControlPanel1;
    private LTable lMiridamgi;
    private LControlPanel lControlPanel2;
    private LTable lSincheong;

    public LMainPanel() {

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.lSelectionPanel = new LSelectionPanel();

        Vector<String> header = new Vector<String>();
        for (EGangjwa eGangjwa : EGangjwa.values()) {
            header.add(eGangjwa.getText());
        }

        this.lMiridamgi = new LTable(header);
        this.lSincheong = new LTable(header);

        this.lControlPanel1 = new LControlPanel(this.lSelectionPanel.getDepartmentTable(), this.lMiridamgi);
        this.lControlPanel2 = new LControlPanel(this.lMiridamgi, this.lSincheong);

        this.add(this.lSelectionPanel);
        this.add(this.lControlPanel1);
        this.add(this.lMiridamgi);
        this.add(this.lControlPanel2);
        this.add(this.lSincheong);
    }
}
