package com.funweb.web.test;

import token.TokenGenerator;

public class TokenGeneratorTest {

	public static void main(String[] args) {
		
		
		String token = TokenGenerator.getTokenString(50);
		
		System.out.println(token);
		
		for(int i = 0; i < token.length(); i++) {
			System.out.print((int)token.charAt(i) + " ");
		}
		
	}

}
