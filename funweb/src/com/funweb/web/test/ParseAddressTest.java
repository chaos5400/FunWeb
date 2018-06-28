package com.funweb.web.test;

public class ParseAddressTest {

	public static void main(String[] args) {
		int fromIndex = 0;
		int endIndex = -1;
		int index = 4;
		String address = "A&B&C&D&E&";
		String result = null;
		
		do {
			fromIndex = endIndex + 1;
			endIndex = address.indexOf('&', fromIndex);
			System.out.println("fromIndex : " + fromIndex);
			System.out.println("endIndex : " + endIndex);
		} while(index-- > 0);
		
		
		System.out.println(address.substring(fromIndex, endIndex));
		
	}

}
