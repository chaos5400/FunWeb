package com.funweb.web.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import mail.util.EmailUtils;
import mail.util.SMTPAuthenticator;

public class SendMailTest {
	
	public static void main(String[] args) {
		new Thread(() -> {	// 비동기 처리를 위한 쓰레드 생성
			try {
				EmailUtils eu = new EmailUtils();
				
				/* 보내는 계정의 아이디와 비밀번호 */
				SMTPAuthenticator auth = new SMTPAuthenticator("papayaza999", "practice123");
				
				/* 위에서 생성한 SMTPAuthenticator 객체를 EmailUtils 객체에 저장한다. */
				eu.setAuth(auth);
				
				/* IP주소를 가져온다 */
				InetAddress local = InetAddress.getLocalHost();
				String ip = local.getHostAddress();
				String mainpage = "http://" + ip + ":8080/funweb_v1_1_2_SMTP/";
				
				/* 이메일 전송시 필요한 정보 */
				String from = "papayaza999@gmail.com";	// 보내는 사람의 이메일 주소
				String to = "tlsdyd1452@naver.com";		// 받는 사람의 이메일 주소
				String subject = "이메일 인증 테스트입니다.";	// 이메일 제목
				String content = "이메일 인증 테스트 성공!!!<a href='" + mainpage + "'>mainpage</a>";	// 이메일 내용
				
				/* Send E Mail */
				eu.sendEmail(from, to, subject, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
	
}
