package com.akhil.video.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "emailId") })
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long userId;
	@Column(nullable = false)
	private String emailId;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private int activeInd;
	@Column(nullable = false)
	private Timestamp accountCreationDate;

	public long getUserId() {
		return userId;
	}

	public int getActiveInd() {
		return activeInd;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPassword() {
		return password;
	}

	public Timestamp getAccountCreationDate() {
		return accountCreationDate;
	}

	protected UserEntity() {
	}

	/**
	 * Private Constructor used by the builder to create the object.
	 * 
	 * @param builder The User Entity Builder.
	 */
	private UserEntity(UserEntityBuilder builder) {
		this.accountCreationDate = builder.accountCreationDate;
		this.emailId = builder.emailId;
		this.password = builder.password;
		this.activeInd = builder.activeInd;
	}

	/**
	 * Builder for {@link UserEntity}.
	 * 
	 * @return The builder.
	 */
	public static UserEntityBuilder builder() {
		return new UserEntityBuilder();
	}

	/**
	 * Builder for the User Entity class.
	 * 
	 * @author akhil
	 */
	public static class UserEntityBuilder {
		private String emailId;
		private String password;
		private int activeInd;
		private Timestamp accountCreationDate;

		public UserEntityBuilder() {
		}

		public UserEntity build() {
			return new UserEntity(this);
		}

		public UserEntityBuilder emailId(String emailId) {
			this.emailId = emailId;
			return this;
		}

		public UserEntityBuilder password(String password) {
			this.password = password;
			return this;
		}

		public UserEntityBuilder activeInd(int activeInd) {
			this.activeInd = activeInd;
			return this;
		}

		public UserEntityBuilder accountCreationDate(Timestamp accountCreationDate) {
			this.accountCreationDate = accountCreationDate;
			return this;
		}
	}
}
