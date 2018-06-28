package mail.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {

	PasswordAuthentication passwordAuthentication;
	
	public SMTPAuthenticator(String mail, String pwd) {
		passwordAuthentication = new PasswordAuthentication(mail, pwd);
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return passwordAuthentication;
	}
	
}
