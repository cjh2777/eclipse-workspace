package lms;

import java.awt.LayoutManager;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LSelectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private LayoutManager layoutManager;
	
	public LSelectionPanel() {
		layoutManager = new BoxLayout(this, BoxLayout.X_AXIS);
		this.setLayout(this.layoutManager);
		
		LIndex lCampus = new LIndex("캠퍼스");		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setViewportView(lCampus);		
		this.add(scrollPane1);
		
		LIndex lCollege = new LIndex("대학");		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setViewportView(lCollege);		
		this.add(scrollPane2);
		
		LIndex lDepartment = new LIndex("학과");		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setViewportView(lDepartment);		
		this.add(scrollPane3);
	}
	
	private class LIndex extends JTable {
		private static final long serialVersionUID = 1L;

		public LIndex(String title) {
			Vector<String> header = new Vector<String>();
			header.add(title);
			
			DefaultTableModel tableModel = new DefaultTableModel(null, header);
			this.setModel(tableModel);
			
			Vector<String> row = new Vector<String>();
			row.add("data"+title);

			tableModel.setNumRows(0);
			for (int i=0; i<3; i++) {
				tableModel.addRow(row);		
			}
		}
	}

}
