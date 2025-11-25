package asciinew;

import java.io.*;

public class String33 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("실수 입력: ");
        String input = br.readLine();

        try {
            double value = Double.parseDouble(input);
            String strValue = Double.toString(value);

            System.out.println("실수를 다시 문자열로 변환: " + strValue);
        } catch (NumberFormatException e) {
            System.out.println("올바른 실수를 입력하세요.");
        }
    }
}
 