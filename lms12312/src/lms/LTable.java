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
        table = new JTable();
        tableModel = new DefaultTableModel(null, header);
        table.setModel(tableModel);
        this.setViewportView(table);
    }

    public void update(String fileName) {    
        LDataAccessObject dao = new LDataAccessObject();
        Vector<String> vRows = dao.findAll(fileName);

        // 혹시 모를 null 방지
        tableModel.setRowCount(0);
        if (vRows == null || vRows.isEmpty()) {
            return;
        }

        int columnCount = tableModel.getColumnCount();

        for (String line : vRows) {
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;

            // 공백 여러 개도 허용
            String[] tokens = line.split("\\s+");
            Object[] row = new Object[columnCount];

            if (columnCount == 1) {
                // 기존 의도 유지: 두 번째 토큰을 주로 사용
                if (tokens.length >= 2) {
                    row[0] = tokens[1];
                } else {
                    row[0] = tokens[0];
                }
            } else {
                // 강좌 테이블처럼 컬럼 여러 개인 경우
                for (int i = 0; i < columnCount && i < tokens.length; i++) {
                    row[i] = tokens[i];
                }
            }

            tableModel.addRow(row);
        }
    }

    // 나중에 선택된 행 가져오거나 이동할 때 쓰라고 getter도 하나 달아줌
    public JTable getTable() {
        return table;
    }
}
