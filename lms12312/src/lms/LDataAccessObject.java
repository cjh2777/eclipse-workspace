package lms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class LDataAccessObject {

    private static final String DATA_DIR = "data";

    public LDataAccessObject() {
    }

    
    public Vector<String> findAll(String fileName) {
        Vector<String> vRows = new Vector<>();

        File file = new File(DATA_DIR + File.separator + fileName);
        if (!file.exists()) {
            System.err.println("데이터 파일이 없습니다: " + file.getAbsolutePath());
            // ❗ null 대신 "빈 벡터" 반환 
            return vRows;
        }

        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine().trim();
                if (!row.isEmpty()) {
                    vRows.add(row);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("파일을 열 수 없습니다: " + file.getAbsolutePath());
        }

        return vRows;
    }

    
    public String find(String fileName, String key) {
        File file = new File(DATA_DIR + File.separator + fileName);
        if (!file.exists()) {
            System.err.println("데이터 파일이 없습니다: " + file.getAbsolutePath());
            return null;
        }
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(key)) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("파일을 열 수 없습니다: " + file.getAbsolutePath());
        }
        return null;
    }
}
