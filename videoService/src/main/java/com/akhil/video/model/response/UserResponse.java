package com.akhil.video.model.response;

import java.sql.Timestamp;

public class UserResponse {
	private long userId;
	private String emailId;
	private String password;
	private int activeInd;
	private Timestamp accountCreationDate;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(int activeInd) {
		this.activeInd = activeInd;
	}

	public Timestamp getAccountCreationDate() {
		return accountCreationDate;
	}

	public void setAccountCreationDate(Timestamp accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}
}
