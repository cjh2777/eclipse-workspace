package echo;

import java.util.Scanner;

public class EchoPrinter {
    public static void main(String[] args) {
        // 콘솔 입력 
        Scanner scanner = new Scanner(System.in);

        System.out.println("텍스트를 입력하세요. (종료하려면 exit 입력)");

        while (true) {
            System.out.print("> "); // 프롬프트 표시
            String input = scanner.nextLine(); // 한 줄 입력 받기

            // 종료 조건
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            // 입력한 내용을 그대로 출력
            System.out.println("Echo: " + input);
        }

        scanner.close(); // 자원 해제
    }
}