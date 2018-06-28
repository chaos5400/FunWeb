package com.funweb.web.controller;

import com.funweb.web.icommand.ICommand;

public class ActionForward {

	private boolean isRedirect = false;
	
	private String viewPage = null;
	private ICommand command = null;
	
	public void setViewPage(String viewPage){  //setter
		this.viewPage = viewPage;
	}
	
	public String getViewPage(){
		return viewPage;
	}
	
	public void setCommand(ICommand command) {
		this.command = command;
	}
	
	public ICommand getCommand() {
		return command;
	}

	public void setRedirect(boolean isRedirect){
		this.isRedirect = isRedirect;
	}
	
	public boolean isRedirect(){
		return isRedirect;
	}
	
}

