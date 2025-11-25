package lms;

import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import lms.LConstants.EGangjwa;

public class LSelectionPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private LTable lRoot;
    private LTable lCampus;
    private LTable lCollege;
    private LTable lDepartment;

    public LSelectionPanel() {

        // 전체는 위/아래 구조
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 1) 위쪽 : 캠퍼스 / 대학 / 학과 3개를 가로로 배치
        JPanel indexPanel = new JPanel();
        indexPanel.setLayout(new BoxLayout(indexPanel, BoxLayout.X_AXIS));

        Vector<String> header1 = new Vector<String>();
        header1.add("코드");
        header1.add("캠퍼스");
        header1.add("파일키");
        lRoot = new LTable(header1);
        lRoot.update("root");
        indexPanel.add(lRoot);

        Vector<String> header2 = new Vector<String>();
        header2.add("코드");
        header2.add("대학");
        header2.add("파일키");
        lCampus = new LTable(header2);
        indexPanel.add(lCampus);

        Vector<String> header3 = new Vector<String>();
        header3.add("코드");
        header3.add("학과");
        header3.add("파일키");
        lCollege = new LTable(header3);
        indexPanel.add(lCollege);

        // 위쪽 index 패널 추가
        this.add(indexPanel);

        // 2) 아래쪽 : 강좌 테이블 (아이디, 강좌명, 강사, 학점, 시간)
        Vector<String> header4 = new Vector<String>();
        for (EGangjwa eGangjwa : EGangjwa.values()) {
            header4.add(eGangjwa.getText());
        }
        lDepartment = new LTable(header4);
        this.add(lDepartment);

        // 3) 선택 연동 로직

        // 캠퍼스 선택 → 대학 로드
        lRoot.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = lRoot.getTable().getSelectedRow();
            if (row == -1) return;
            String fileKey = (String) lRoot.getTable().getValueAt(row, 2); // 파일키 컬럼
            lCampus.update(fileKey);
            lCollege.update("");
            lDepartment.update("");
        });

        // 대학 선택 → 학과 로드
        lCampus.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = lCampus.getTable().getSelectedRow();
            if (row == -1) return;
            String fileKey = (String) lCampus.getTable().getValueAt(row, 2);
            lCollege.update(fileKey);
            lDepartment.update("");
        });

        // 학과 선택 → 강좌 로드
        lCollege.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = lCollege.getTable().getSelectedRow();
            if (row == -1) return;
            String fileKey = (String) lCollege.getTable().getValueAt(row, 2);
            lDepartment.update(fileKey);
        });
    }

    public LTable getDepartmentTable() {
        return lDepartment;
    }
}
