package com.funweb.web.test;


/*
 * 결론:
 * static 메서드 내부에 있는 변수와 실행 로직은
 * 동기화를 하지 않아도 각각의 쓰레드마다 독립적으로 실행된다.
 * 단! 실행되는 순서는 뒤섞일 수가 있다.
 * 
 * 싱글톤 패턴에서도 마찬가지이다.
 */
class clsNumber { // 데이터 공유를 위한 클래스

	private static clsNumber cn = null;
	
	private clsNumber() {
	}
	
	static clsNumber getInstance() {
		if(cn == null) {
			cn = new clsNumber();
		}
		return cn;
	}
	
	void addNum(final int n) { 
		
		int num = 0;
		
		for(int i = 0; i < 100; i++) {
			num++;
		}
		
		synchronized(clsNumber.class) {
			System.out.println("num : " + num);
			System.out.println("n : " + n);
		}
		
	}

}



class ThreadTest extends Thread

{

	int n;
	
	public ThreadTest(int n) {
		this.n = n;
	}
	
	@Override
	public void run() {
		clsNumber.getInstance().addNum(n);
	}
	
	public static void main(String[] args)

	{

		for(int i = 0; i < 10; i++) {
			new ThreadTest(i).start();
		}


	}

}

