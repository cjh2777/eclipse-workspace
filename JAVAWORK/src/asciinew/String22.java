package asciinew;

import java.io.*;

public class String22 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("실수 문자열 입력: ");
        String floatText = br.readLine();

        double result = 0.0;
        boolean isFraction = false;  // 소수부 시작 여부
        double divisor = 1.0;

        for (int i = 0; i < floatText.length(); i++) {
            int ch = floatText.charAt(i);

            if (ch == '.') { // 소수점 만남
                isFraction = true;
                continue;
            }

            if (ch >= 0x30 && ch <= 0x39) {
                int digit = ch - 0x30;
                if (!isFraction) {
                    result = result * 10 + digit;
                } else {
                    divisor *= 10.0;
                    result = result + digit / divisor;
                }
            }
        }

        System.out.println("변환된 실수: " + result);
    }
}
