package lms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class LDataAccessObject {
	public LDataAccessObject() {
		
	}
	
	public Vector<String> findAll(String fileName) {
		File file = new File("data/"+fileName);
		Scanner scanner = null;
		Vector<String> vRows = null;
		try {
			// file open
			scanner = new Scanner(file);
			// prepare value objects
			vRows = new Vector<String>();
			while(scanner.hasNext()) {
				String row = scanner.nextLine();
				vRows.add(row);
			}
			scanner.close();
			return vRows;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public String find(String fileName, String key) {
		return null;
	}
}
