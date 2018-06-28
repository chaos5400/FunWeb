package com.funweb.web.commandfactory;

import com.funweb.web.command.account.AccountInfoCommand;
import com.funweb.web.command.account.DeleteAccountProcCommand;
import com.funweb.web.command.account.EMailCertificationCommand;
import com.funweb.web.command.account.JoinCommand;
import com.funweb.web.command.account.LoginCommand;
import com.funweb.web.command.account.LoginProcCommand;
import com.funweb.web.command.account.LogoutCommand;
import com.funweb.web.command.account.ModifyInfoCommand;
import com.funweb.web.command.account.ModifyInfoProcCommand;
import com.funweb.web.command.account.ModifyPassProcCommand;
import com.funweb.web.command.center.inquiry.DeleteInquiryProcCommand;
import com.funweb.web.command.center.inquiry.InquiryConfirmUserCommand;
import com.funweb.web.command.center.inquiry.InquiryListCommand;
import com.funweb.web.command.center.inquiry.InquiryPassCheckCommand;
import com.funweb.web.command.center.inquiry.InquiryReadCommand;
import com.funweb.web.command.center.inquiry.InquiryReplyCommand;
import com.funweb.web.command.center.inquiry.InquiryReplyProcCommand;
import com.funweb.web.command.center.inquiry.InquiryWriteCommand;
import com.funweb.web.command.center.inquiry.InquiryWriteProcCommand;
import com.funweb.web.command.center.notice.DeleteNoticeProcCommand;
import com.funweb.web.command.center.notice.NoticeListCommand;
import com.funweb.web.command.center.notice.NoticeReadCommand;
import com.funweb.web.command.center.notice.NoticeWriteCommand;
import com.funweb.web.command.center.notice.NoticeWriteProcCommand;
import com.funweb.web.command.center.publicnews.DeletePublicNewsProcCommand;
import com.funweb.web.command.center.publicnews.PublicNewsListCommand;
import com.funweb.web.command.center.publicnews.PublicNewsReadCommand;
import com.funweb.web.command.center.publicnews.PublicNewsWriteCommand;
import com.funweb.web.command.center.publicnews.PublicNewsWriteProcCommand;
import com.funweb.web.command.main.MainPageCommand;
import com.funweb.web.controller.ActionForward;

/**
 * <p>서블릿에서 커맨드 패턴 사용시 소스 코드의 양을 줄이기 위해서
 * 이 클래스를 제작하였다.
 * 
 * <p>이 클래스에서는 uri 주소값을 가지고 적절한 커맨드 객체를
 * 반환하는 역할을 수행한다.
 * 
 * <p>이 클래스에서는 uri 주소값을 가지고 적절한 viewPage를
 * 반환하는 역할을 수행한다.
 * 
 * @author 정진원
 */
public class CommandAndViewFactory {
	
	
	
	
	
	/* 이 클래스는 객체 생성이 필요 없다! */
	private CommandAndViewFactory() {
	}
	
	
	
	
	
	/**
	 * <p>인자로 들어온 com 의 값에 따라 적절한 커맨드 객체를 반환한다.
	 * 
	 * <p>이 메서드는 관리자만 접근할 수 있는 커맨드 객체를 반환한다.
	 */
	public static ActionForward getCommandForAdmin(String com) {
		
		ActionForward actionForward = null;

		actionForward = redirectCommandForAdmin(com);
		
		if(actionForward.getCommand() == null) {
			
			actionForward = forwardCommandForAdmin(com);
			
		}
		
		return actionForward;
		
	}
	
	
	
	
	
	/**
	 * 관리자 페이지 중에서 리다이렉트시킬 커맨드 객체를 생성한다.
	 */
	private static ActionForward redirectCommandForAdmin(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		actionForward.setRedirect(true);
		
		if("/noticeWriteProc.do".equals(com)) {					// 공지사항 글 올리기 작업 수행
			
			actionForward.setCommand(new NoticeWriteProcCommand());
			
		} else if("/deleteNotice.do".equals(com)) {				// 공지사항 삭제
			
			actionForward.setCommand(new DeleteNoticeProcCommand());
			
		} else if("/publicNewsWriteProc.do".equals(com)) {		// 뉴스 글 올리기 작업 수행
			
			actionForward.setCommand(new PublicNewsWriteProcCommand());
			
		} else if("/deletePublicNews.do".equals(com)) {			// 뉴스 삭제
			
			actionForward.setCommand(new DeletePublicNewsProcCommand());
			
		}
		
		return actionForward;
		
	}
	

	
	
	
	/**
	 * 관리자 페이지 중에서 포워드시킬 커맨드 객체를 생성한다.
	 */
	private static ActionForward forwardCommandForAdmin(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		actionForward.setRedirect(false);
		
		if("/noticeWrite.do".equals(com)) {						// 공지사항 글쓰기 페이지
				
			actionForward.setCommand(new NoticeWriteCommand());
				
		} else if("/publicNewsWrite.do".equals(com)) {			// 뉴스  글쓰기 페이지
			
			actionForward.setCommand(new PublicNewsWriteCommand());
			
		}
			
		return actionForward;
		
	}
	
	
	
	
	
	/**
	 * <p>인자로 들어온 com 의 값에 따라 적절한 뷰페이지를 반환한다.
	 * 
	 * <p>이 메서드는 관리자만 접근할 수 있는 뷰페이지를 반환한다.
	 * 
	 * @param com .do 로 끝나는 url의 추출값
	 * @return com과 일치하는 url이 있다면 뷰페이지, 없다면 null
	 */
	public static ActionForward getViewPageForAdmin(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
		
	}
	
	

	
	
	/**
	 * <p>인자로 들어온 com 의 값에 따라 적절한 커맨드 객체를 반환한다.
	 * 
	 * <p>이 메서드는 로그인된 유저만 접근할 수 있는 커맨드 객체를 반환한다.
	 *
	 * @param com .do 로 끝나는 url의 추출값
	 * @return com과 일치하는 url이 있다면 ICommand의 서브클래스 객체, 없다면 null
	 */
	public static ActionForward getCommandForLoginUser(String com) {
		
		ActionForward actionForward = null;

		actionForward = redirectCommandForLogin(com);
		
		if(actionForward.getCommand() == null) {
			
			actionForward = forwardCommandForLogin(com);
			
		}
		
		return actionForward;
		
	}
	
	
	
	
	
	/**
	 * 로그인 유저 페이지 중에서 리다이렉트시킬 커맨드 객체를 생성한다.
	 */
	private static ActionForward redirectCommandForLogin(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		actionForward.setRedirect(true);

		if("/logout.do".equals(com)) {							// 로그아웃		
			
			actionForward.setCommand(new LogoutCommand());
			
		} else if("/modifyInfo_proc.do".equals(com)) {			// 정보 수정 수행
			
			actionForward.setCommand(new ModifyInfoProcCommand());
			
		} else if("/modifyPass_proc.do".equals(com)) {			// 비밀번호 변경 수행
			
			actionForward.setCommand(new ModifyPassProcCommand());
			
		} else if("/deleteAccount_proc.do".equals(com)) {		// 회원 탈퇴 진행
			
			actionForward.setCommand(new DeleteAccountProcCommand());
			
		} else if("/inquiryWriteProc.do".equals(com)) {			// 고객문의 작성 수행
			
			actionForward.setCommand(new InquiryWriteProcCommand());
			
		} else if("/deleteInquiry.do".equals(com)) {			// 고객문의 글 삭제
			
			actionForward.setCommand(new DeleteInquiryProcCommand());
			
		} else if("/inquiryReplyProc.do".equals(com)) {			// 고객 문의 답글 작성 수행
			
			actionForward.setCommand(new InquiryReplyProcCommand());
			
		}
		
		return actionForward;
		
	}
	
	
	
	
	
	/**
	 * 로그인 유저 페이지 중에서 포워드시킬 커맨드 객체를 생성한다.
	 */
	private static ActionForward forwardCommandForLogin(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		actionForward.setRedirect(false);
		
		if("/info.do".equals(com)) {							// 내 정보 페이지
			
			actionForward.setCommand(new AccountInfoCommand());
			
		} else if("/modifyInfo.do".equals(com)) {				// 정보 수정 페이지
			
			actionForward.setCommand(new ModifyInfoCommand());
			
		} else if("/inquiryWrite.do".equals(com)) {				// 고객문의 작성 페이지
			
			actionForward.setCommand(new InquiryWriteCommand());
			
		} else if("/inquiryReply.do".equals(com)) {				// 고객 문의 답글 작성 페이지
			
			actionForward.setCommand(new InquiryReplyCommand());
			
		}
			
		return actionForward;
		
	}
	
	
	
	
	
	
	/**
	 * <p>인자로 들어온 com 의 값에 따라 적절한 뷰페이지를 반환한다.
	 * 
	 * <p>이 메서드는 로그인된 유저만 접근할 수 있는 뷰페이지를 반환한다.
	 * 
	 * @param com .do 로 끝나는 url의 추출값
	 * @return com과 일치하는 url이 있다면 뷰페이지, 없다면 null
	 */
	public static ActionForward getViewPageForLoginUser(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		if("/confirmPassword.do".equals(com)) {					// 회원 탈퇴 시 비밀번호 확인 페이지
			
			actionForward.setViewPage("/member/info/confirmPassword.jsp");
			
		} else if("/modifyPass.do".equals(com)) {				// 비밀번호 변경 페이지
			
			actionForward.setViewPage("/member/info/modifyPass.jsp");
			
		} else if("/imageUpload.do".equals(com)) {				// 에디터 이미지 업로드 작업 수행 
			
			actionForward.setViewPage("/board/imageUpload.jsp");
			
		}
			
		return actionForward;
		
	}
	
	
	
	
	
	/**
	 * <p>인자로 들어온 com 의 값에 따라 적절한 커맨드 객체를 반환한다.
	 *
	 * <p>이 메서드는 로그인 여부와 상관없이 접근할 수 있는 커맨드 객체를 반환한다.
	 *
	 * @param com .do 로 끝나는 url의 추출값
	 * @return com과 일치하는 url이 있다면 ICommand의 서브클래스 객체, 없다면 null
	 */
	public static ActionForward getCommandNonSecurity(String com) {
		
		ActionForward actionForward = null;

		actionForward = redirectCommandNonSecurity(com);
		
		if(actionForward.getCommand() == null) {
			
			actionForward = forwardCommandNonSecurity(com);
			
		}
		
		return actionForward;
		
	}
	
	
	
	

	/**
	 * 로그인 유저 페이지 중에서 리다이렉트시킬 커맨드 객체를 생성한다.
	 */
	private static ActionForward redirectCommandNonSecurity(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		actionForward.setRedirect(true);

		if ("/join_proc.do".equals(com)) { 						// 회원가입 진행

			actionForward.setCommand(new JoinCommand());

		} else if ("/loginProc.do".equals(com)) {				// 실제 로그인 작업 진행
		
			actionForward.setCommand(new LoginProcCommand());
			
		} else if ("/authenticateemail.do".equals(com)) { 		// 이메일 인증확인
			
			actionForward.setCommand(new EMailCertificationCommand());

		} 
		
		return actionForward;
		
	}
	
	
	
	
	
	/**
	 * 로그인 유저 페이지 중에서 포워드시킬 커맨드 객체를 생성한다.
	 */
	private static ActionForward forwardCommandNonSecurity(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		actionForward.setRedirect(false);
		
		if ("/index.do".equals(com)) {							// 메인 페이지

			 actionForward.setCommand(new MainPageCommand());

		} else if ("/notice.do".equals(com)) {					// 공지사항 목록 

			actionForward.setCommand(new NoticeListCommand());

		} else if ("/noticeRead.do".equals(com)) {				// 공지사항 읽기
			
			actionForward.setCommand(new NoticeReadCommand());
			
		} else if ("/publicNews.do".equals(com)) {				// 뉴스 목록 페이지
			
			actionForward.setCommand(new PublicNewsListCommand());
			
		} else if ("/publicNewsRead.do".equals(com)) { 			// 뉴스 읽기
			
			actionForward.setCommand(new PublicNewsReadCommand());
			
		} else if ("/inquiry.do".equals(com)) {					// 고객 문의 목록
			
			actionForward.setCommand(new InquiryListCommand());
			
		} else if ("/inquiryRead.do".equals(com)) {				// 고객 문의 읽기
			
			actionForward.setCommand(new InquiryReadCommand());
			
		} else if ("/inquiryConfirmUser.do".equals(com)) {		// 게시글 작성을 본인이 했는지 확인
			
			actionForward.setCommand(new InquiryConfirmUserCommand());
			
		} else if ("/inquiryPassCheck.do".equals(com)) {		// 본인이 아니라면 게시글 패스워드가 일치하는지 확인
		
			actionForward.setCommand(new InquiryPassCheckCommand());
			
		} else if ("/checkLoginValidation.do".equals(com)) { 	// 로그인 유효성 검사 진행

			actionForward.setCommand(new LoginCommand());

		} 
		
		return actionForward;
		
	}
	
	
	
	
	
	/**
	 * <p>인자로 들어온 com 의 값에 따라 적절한 뷰페이지를 반환한다.
	 * 
	 * <p>이 메서드는 로그인 여부와 상관없이 접근할 수 있는 뷰페이지를 반환한다.
	 * 
	 * @param com .do 로 끝나는 url의 추출값
	 * @return com과 일치하는 url이 있다면 뷰페이지, 없다면 null
	 */
	public static ActionForward getViewPageNonSecurity(String com) {
		
		ActionForward actionForward = new ActionForward();
		
		if("/".equals(com)) { 									// 서버 첫 로딩시 들어오는 주소값
																// 설정을 안해두면 존재하지 않는 경로로 인식한다.
			actionForward.setViewPage("/index.do");
			
		} else if ("/welcome.do".equals(com)) {					// 회사 소개 페이지

			actionForward.setViewPage("/company/welcome.jsp");

		} else if ("/join.do".equals(com)) {					// 회원가입 페이지

			actionForward.setViewPage("/member/join.jsp");

		} else if ("/login.do".equals(com)) {					// 로그인 페이지

			actionForward.setViewPage("/member/login.jsp");

		} else if ("/checkSignup.do".equals(com)) { 			// 아이디 중복 체크시 ajax에서 사용하는 url

			actionForward.setViewPage("/member/check/duplicateCheck.jsp");

		} else if ("/emailauthentication.do".equals(com)) { 	// 이메일 인증 페이지

			actionForward.setViewPage("/member/authentication/emailauthentication.jsp");

		} else if ("/emailresend.do".equals(com)) { 			// 이메일 재발송시 ajax에서 사용하는 url

			actionForward.setViewPage("/member/authentication/emailresend.jsp");

		} else if ("/inquiryPass.do".equals(com)) {				// 고객 문의 게시글 패스워드 입력 페이지
			
			actionForward.setViewPage("/center/inquiry/confirmPass.jsp");
			
		}
		
		return actionForward;
		
	}
	
}
