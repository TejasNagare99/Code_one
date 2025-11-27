package com.excel.bean;



public class EventBean {


	private String eventDate;
	private String title;
	private String text;
	private String username;
	private String companyCode;
	
	public String getEventDate() {
		return eventDate;
	}
	@Override
	public String toString() {
		return "EventBean [eventDate=" + eventDate + ", title=" + title + ", text=" + text + ", username=" + username
				+ ", companyCode=" + companyCode + "]";
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}

