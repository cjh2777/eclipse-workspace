package asciinew;

import java.io.*;

public class FloatToAscii {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("실수 입력 (예: 23.57): ");
        String numText = br.readLine();   

        
        System.out.print("ASCII 코드: ");
        for (int i = 0; i < numText.length(); i++) {
            int code = (int) numText.charAt(i);
            System.out.print(code + " ");
        }
        System.out.println();

        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numText.length(); i++) {
            int code = (int) numText.charAt(i);
            sb.append((char) code);
        }

        System.out.println("복원된 문자열: " + sb.toString());

        
        try {
            double value = Double.parseDouble(sb.toString());
            System.out.println("복원된 숫자(double): " + value);
        } catch (NumberFormatException e) {
            System.out.println("숫자로 변환 불가한 입력입니다.");
        }
    }
}
