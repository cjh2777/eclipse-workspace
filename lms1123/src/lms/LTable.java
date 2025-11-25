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
        tableModel = new DefaultTableModel(null, header);
        table = new JTable(tableModel);
        this.setViewportView(table);
    }

    public JTable getTable() {
        return table;
    }

    public void update(String fileName) {
        tableModel.setRowCount(0);

        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        LDataAccessObject dao = new LDataAccessObject();
        Vector<String> data = dao.findAll(fileName);

        if (data == null) {
            return;
        }

        for (String line : data) {
            if (line == null) {
                continue;
            }
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            String[] tokens = trimmed.split("\\s+");
            tableModel.addRow(tokens);
        }
    }
}
