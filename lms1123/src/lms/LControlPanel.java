package lms;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LControlPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JButton lButton;
    private JButton rButton;

    private LTable leftTable;
    private LTable rightTable;

    public LControlPanel(LTable leftTable, LTable rightTable) {
        this.leftTable = leftTable;
        this.rightTable = rightTable;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.lButton = new JButton("<<");
        this.add(this.lButton);

        this.rButton = new JButton(">>");
        this.add(this.rButton);

        this.rButton.addActionListener(e -> moveRow(this.leftTable, this.rightTable));
        this.lButton.addActionListener(e -> moveRow(this.rightTable, this.leftTable));
    }

    private void moveRow(LTable fromTable, LTable toTable) {
        if (fromTable == null || toTable == null) return;

        JTable from = fromTable.getTable();
        JTable to = toTable.getTable();

        int selectedRow = from.getSelectedRow();
        if (selectedRow == -1) return;

        DefaultTableModel fromModel = (DefaultTableModel) from.getModel();
        DefaultTableModel toModel = (DefaultTableModel) to.getModel();

        int columnCount = fromModel.getColumnCount();
        Object[] rowData = new Object[columnCount];

        for (int col = 0; col < columnCount; col++) {
            rowData[col] = fromModel.getValueAt(selectedRow, col);
        }

        toModel.addRow(rowData);
        fromModel.removeRow(selectedRow);
    }
}
