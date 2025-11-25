package asciinew;

import java.io.*;

public class ASCIIRound {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("문자열 입력: ");
        String input = br.readLine();

        // 문자열 → 아스키 코드
        System.out.print("ASCII 코드: ");
        for (int i = 0; i < input.length(); i++) {
            int code = (int) input.charAt(i);
            System.out.print(code + " ");
        }
        System.out.println();

        // 아스키 코드 → 다시 문자열
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            int code = (int) input.charAt(i);
            sb.append((char) code);
        }

        System.out.println("복원된 문자열: " + sb.toString());
    }
}
