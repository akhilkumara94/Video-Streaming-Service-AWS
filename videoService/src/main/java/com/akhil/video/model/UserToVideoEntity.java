package com.akhil.video.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "uservideo", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "videoId" }) })
public class UserToVideoEntity {
	@Id
	private long videoId;
	private long userId;

	public long getUserId() {
		return userId;
	}

	public long getVideoId() {
		return videoId;
	}

	protected UserToVideoEntity() {
	}

	/**
	 * Constructor creates {@link UserToVideoEntity} from the builder.
	 * 
	 * @param builder The builder used to create the entity.
	 */
	private UserToVideoEntity(UserToVideoEntityBuilder builder) {
		this.videoId = builder.videoId;
		this.userId = builder.userId;
	}

	/**
	 * Builder for {@link UserToVideoEntity}.
	 * @return The builder.
	 */
	public static UserToVideoEntityBuilder builder() {
		return new UserToVideoEntityBuilder();
	}

	public static class UserToVideoEntityBuilder {
		private long videoId;
		private long userId;

		public UserToVideoEntity build() {
			return new UserToVideoEntity(this);
		}

		public UserToVideoEntityBuilder videoId(long videoId) {
			this.videoId = videoId;
			return this;
		}

		public UserToVideoEntityBuilder userId(long userId) {
			this.userId = userId;
			return this;
		}
	}
}
