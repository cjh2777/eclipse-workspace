package asciinew;

import java.io.*;

public class String11 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("정수 문자열 입력: ");
        String intText = br.readLine();

        int result = 0;
        for (int i = 0; i < intText.length(); i++) {
            int ch = intText.charAt(i);
            if (ch >= 0x30 && ch <= 0x39) { 
                result = result * 10 + (ch - 0x30);
            }
        }

        System.out.println("변환된 정수: " + result);
    }
}
