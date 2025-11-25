package lms;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LTable extends JScrollPane {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	
	public LTable(Vector<String> header) {
		// create a table
		table = new JTable();
		// create a table model
		tableModel = new DefaultTableModel(null, header);
		// bind
		table.setModel(tableModel);	
		// place a table
		this.setViewportView(table);	
	}

	public void update(String fileName) {	
		LDataAccessObject dao = new LDataAccessObject();
		Vector<String> vRows = dao.findAll(fileName);

		tableModel.setNumRows(0);
		for (String line: vRows) {
			String[] row = new String[1];
			row[0] = line.split(" ")[1];
			tableModel.addRow(row);		
		}		
	}
}
