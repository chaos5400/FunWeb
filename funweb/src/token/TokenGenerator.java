package token;

import java.util.Random;

public class TokenGenerator {
	
	public static String getTokenString(int length) {
		
		String token = "";
		Random rnd = new Random();
		
		for(int i = 0; i < length; i++) {
			int rIndex = rnd.nextInt(3);
			String temp = "";
			switch (rIndex) {
			/* 소문자 */
			case 0:
				temp = String.valueOf((char) ((int) (rnd.nextInt(26) + 97)));
				break;
			/* 대문자 */
			case 1:
				temp = String.valueOf((char) ((int) (rnd.nextInt(26) + 65)));
				break;
			/* 숫자 */
			case 2:
				temp = String.valueOf((char) ((int) (rnd.nextInt(10) + 48)));
				break;
				default:
			}
			/* 랜덤으로 얻은 토큰 한 문자를 추가시킨다. */
			token += temp;
		}
		
		return token;
		
	}
	
	
}
