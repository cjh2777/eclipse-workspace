package ii;

import java.util.Scanner;

public class SumProgramLoop {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
			while (true) {   
			    System.out.print("숫자 N을 입력하세요: ");
			    String input = sc.nextLine();

			    try {
			        int n = Integer.parseInt(input);
			        int sum = 0;
			        for (int i = n; i >= 1; i--) {
			            sum += i;
			        }
			        System.out.println("결과값: " + sum);
			    } catch (NumberFormatException e) {
			        System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
			    }
			}
		}
    }
}
