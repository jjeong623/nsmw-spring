package com.naeddoco.nsmwspring.legacyController.action;

import java.io.IOException;

import com.naeddoco.nsmwspring.legacyController.common.Action;
import com.naeddoco.nsmwspring.legacyController.common.ActionForward;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 로그인버튼을 눌렀을때 로그인페이지로 이동하는 로직
		 */
		ActionForward forward = new ActionForward();
		//정보를 보낼 경로와 redirect방식 설정
		forward.setPath("login.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}