package com.funweb.web.test;

public class getCauseTest {

	public static void main(String[] args) {
		
		try {
			throw new InnerException();
		} catch (InnerException ie) {
			try {
				throw new OuterException(ie);
			} catch (OuterException oe) {
				System.out.println(oe.getCause());
			}
		}
		
	}

}

class OuterException extends Exception {
	
	public OuterException() {
	}
	
	public OuterException(Throwable t) {
		super(t);
	}
	
}

class InnerException extends Exception {
}
