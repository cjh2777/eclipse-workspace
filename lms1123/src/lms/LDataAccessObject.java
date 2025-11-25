package lms;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Vector;

public class LDataAccessObject {

    public LDataAccessObject() {
    }

    public Vector<String> findAll(String fileName) {
        Vector<String> vRows = new Vector<String>();

        if (fileName == null || fileName.isEmpty()) {
            return vRows;
        }

        File file = new File("data/" + fileName + ".txt");

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
            while (scanner.hasNext()) {
                String row = scanner.nextLine();
                vRows.add(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return vRows;
    }

    public String find(String fileName, String key) {
        return null;
    }
}
