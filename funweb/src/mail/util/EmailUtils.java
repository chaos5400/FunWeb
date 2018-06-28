package mail.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {
	
	private Authenticator auth;
	
	public EmailUtils() {
	}
	
	public EmailUtils(Authenticator auth) {
		this.auth = auth;
	}

	public void setAuth(Authenticator auth) {
		this.auth = auth;
	}
	
	/**
	 * 이메일을 발송하는 메소드
	 * 
	 * @param from		보내는 사람의 이메일 주소(ex. abc@gmail.com)
	 * @param to		받는 사람의 이메일 주소
	 * @param subject	메일의 제목
	 * @param content	메일의 내용
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public void sendEmail(String from, String to, String subject, String content) throws AddressException, MessagingException {
		
		Properties props = new Properties();
		
		/* SMTP 서버에 접속하기 위한 정보 저장 */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.debug", "true");
		
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		/* SMTP 정보 저장 끝 */
		
			
		Session maillSession = Session.getInstance(props, auth);
		
		maillSession.setDebug(true);
		
		Message message = new MimeMessage(maillSession);
		
		// Set From: 보내는 사람 지정
		message.setFrom(new InternetAddress(from));
		
		// Set to: 받는 사람 지정
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		// Set Subject: 메일 제목 지정
		message.setSubject(subject);
		
		// Set Content: 메일 내용 지정
		message.setContent(content, "text/html; charset=UTF-8");
		
		// 메일 전송
		Transport.send(message);
			
		
	}
	
}
