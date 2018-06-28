package com.funweb.web.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {

	Timer timer = new Timer();
	HelloTask task = new HelloTask();
	GregorianCalendar gcal = new GregorianCalendar();
	Calendar cal = Calendar.getInstance();
	
	int year = cal.get(cal.YEAR);
	int month = cal.get(cal.MONTH) + 1;
	int date = cal.get(cal.DATE);
	
	int hour = cal.get(cal.HOUR_OF_DAY);
	int min = cal.get(cal.MINUTE);
	int sec = cal.get(cal.SECOND);
	
	public TestTimer() {
		gcal.set(2018, Calendar.APRIL, 35, 12, 45, 0);
		timer.schedule(task, gcal.getTime(), 1000);
	}

	public synchronized void stop() {
		if (timer != null)
			timer.cancel();
	}

	public synchronized void start() throws Exception {
		timer = new Timer();
		if (task == null)
			task = new HelloTask();

		timer.schedule(task, 0, 1000);
	}

	class HelloTask extends TimerTask {
		public void run() {
			System.out.println(new Date());
		}
	}

	public static void main(String[] args) {
		try {
			TestTimer t = new TestTimer();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
