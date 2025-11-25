package ii;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		int input, result;
		
		try {
			result =0;
			input = System.in.read();  // int a = System.in.read();라고 써도 같음
			while ( input >= 0x30 && input <= 0x39) //HEX 0x30 == '0' 
				result = result*10 + (input-0x30);
				input = System.in.read();
			{
				// char output;
				// output = result/10
				// output + 0x30
				// float 24.05를 아스키 코드로 변환, '.'도 아스키 코드로 변환함
				
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
