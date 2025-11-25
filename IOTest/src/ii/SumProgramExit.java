package ii;

import java.util.Scanner;

public class SumProgramExit {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
			while (true) {
			    System.out.print("숫자 N을 입력하세요 (X 입력 시 종료): ");
			    String input = sc.nextLine();

			    if (input.equalsIgnoreCase("X")) {   // X 입력 시 종료
			        System.out.println("프로그램을 종료합니다.");
			        break;
			    }

			    try {
			        int n = Integer.parseInt(input);
			        int sum = n * (n + 1) / 2;  
			        System.out.println("결과값: " + sum);
			    } catch (NumberFormatException e) {
			        System.out.println("잘못된 입력입니다. 숫자 또는 X를 입력하세요.");
			    }
			}
		}
    }
    
}
