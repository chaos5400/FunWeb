package com.funweb.web.util;

import java.net.InetAddress;

import javax.mail.Authenticator;

import mail.util.EmailUtils;
import mail.util.SMTPAuthenticator;

public class EMailUtils {
	
	private Authenticator auth = new SMTPAuthenticator("papayaza999", "practice123"); // 이메일을 발송할 계정과 계정 비밀번호
	
	public void setAuth(Authenticator auth) {
		this.auth = auth;
	}

	public void sendMailForAuthentication(String to, String token, String contextPath) {
		if (token == null) return;
		new Thread(() -> {	// 비동기 처리를 위하여 Thread 클래스의 run메소드를 사용한다.
				try {
					EmailUtils eu = new EmailUtils();
					
					/* 위에서 생성한 SMTPAuthenticator 객체를 EmailUtils 객체에 저장한다. */
					eu.setAuth(auth);
					
					/* IP주소를 얻는다. */
					InetAddress local = InetAddress.getLocalHost();
					String ip = local.getHostAddress();
					
					/* 인증확인을 위한 url이다. 회원가입시 생성한 토큰값을 같이 넘겨준다. */
					String mainpage = "http://" + ip + ":8080" + contextPath + "/authenticateemail.do?token=" + token;
					
					/* 이메일 전송시 필요한 정보 */
					String from = "papayaza999@gmail.com";	// 보내는 사람의 이메일 주소(관리자 계정)
					String subject = "FunWeb에서 보낸 회원가입 인증용 메일입니다. 계정을 활성화 하려면 클릭하십시오.";	// 이메일 제목
					String content = "<h2>FunWeb 회원가입을 축하합니다!</h2><BR/>" +
									 "<a href='" + mainpage + "'>FunWeb 계정을 활성화 하려면 이 링크를 클릭하십시오.</a>";

					/* Send E-Mail */
					eu.sendEmail(from, to, subject, content);
				} catch (Exception e) {
					// 이메일 전송에 실패하더라도 메일 재발송 기능이 있으므로 아무것도 하지 않는다.
				}
		}).start();
	}
	
}
