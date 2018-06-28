package com.funweb.web.scheduler;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

import com.funweb.web.task.ServerCheckTask;

public class ServerCheckScheduler {
	
	private Timer timer = new Timer();
	private GregorianCalendar gcal = new GregorianCalendar();
	private ServerCheckTask cleanTask = new ServerCheckTask();	// 임시 이미지 파일 삭제 태스크
	
	private Calendar cal = Calendar.getInstance();
	
	private int year = cal.get(Calendar.YEAR);
	private int month = cal.get(Calendar.MONTH);
	private int day = cal.get(Calendar.DATE);
	
	private int hour = cal.get(Calendar.HOUR_OF_DAY);
	private int minute = cal.get(Calendar.MINUTE);
	
	public ServerCheckScheduler() {
		// 서버 구동 후 다음날 새벽 2시 30분 00초에 서버 초기화 시작
		gcal.set(year, month, day + 1, 2, 30, 0);
		// 테스트 용으로 서버 구동 후 5분후에 실행
//		gcal.set(year, month, day, hour, minute +  5, 0);
		// 서버 초기화 스케쥴 등록
		timer.schedule(cleanTask, gcal.getTime(), 24 * 60 * 60 * 1000);
	}
	
}
