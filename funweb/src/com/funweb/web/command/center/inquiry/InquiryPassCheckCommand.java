package com.funweb.web.command.center.inquiry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funweb.web.dao.ReplyBoardDao;
import com.funweb.web.daoimpl.InquiryDaoImpl;
import com.funweb.web.dto.Inquiry;
import com.funweb.web.icommand.ICommand;
import com.funweb.web.model.BoardModel;
import com.funweb.web.util.RequestUtils;

import jdbccontext.exception.NoQueryDataException;

public class InquiryPassCheckCommand implements ICommand {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ReplyBoardDao<Inquiry> rdao = new InquiryDaoImpl();
		BoardModel<Inquiry> model = new BoardModel<>(request, rdao); 
		
		try {
			if(!model.isEqualPass()) { // 패스워드가 일치하지 않는다면
				RequestUtils.setMessage(request, "패스워드가 일치하지 않습니다.<br><br>다시 시도하여 주십시오.");
				RequestUtils.setArticle(request, "inquiry/confirmPass.jsp"); // 패스워드 확인 페이지로 이동한다.
				return "/center/board.jsp";
			}
		} catch (NoQueryDataException e) {	// 게시글이 존재하지 않는다면
			RequestUtils.setMessage(request, "게시글이 존재하지 않습니다.");
			return "inquiry.do";
		} catch (NullPointerException e) {	// 정상적이지 않은 접근으로,
											// 패스워드 입력 없이 접근하였을 시 발생한다.
			RequestUtils.setMessage(request, 
					  "정상적이지 않은 접근입니다.<br><br>"
					+ "올바른 방법으로 다시 시도하여 주십시오.");
			return "inquiry.do";
		}
		
		
		// 패스워드를 올바르게 입력하였다면 게시글 읽기 페이지로 이동한다.
		return "inquiryRead.do";
		
	}

}
