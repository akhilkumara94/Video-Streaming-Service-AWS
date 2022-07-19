package com.akhil.video.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video")
public class VideoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long videoId;
	@Column(nullable = false)
	private String title;
	private String description;
	@Column(nullable = false)
	private String originalFileName;
	@Column(nullable = false)
	private String fileExtension;
	@Column(nullable = false)
	private int activeInd;

	/**
	 * @return the id
	 */
	public long getVideoId() {
		return videoId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the originalFileName
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/**
	 * @return the fileType
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @return the activeInd
	 */
	public int getActiveInd() {
		return activeInd;
	}

	protected VideoEntity() {
	}

	/**
	 * Create {@link VideoEntity} from the builder.
	 * 
	 * @param builder The builder.
	 */
	private VideoEntity(VideoEntityBuilder builder) {
		this.videoId = videoId;
		this.title = builder.title;
		this.description = builder.description;
		this.originalFileName = builder.originalFileName;
		this.fileExtension = builder.fileExtension;
		this.activeInd = builder.activeInd;
	}

	/**
	 * Returns the builder for {@link VideoEntity}.
	 * 
	 * @return The builder.
	 */
	public static VideoEntityBuilder builder() {
		return new VideoEntityBuilder();
	}

	/**
	 * Create a builder with the passed in video entity.
	 * 
	 * @param videoEntity The video entity.
	 * @return The builder.
	 */
	public static VideoEntityBuilder builder(VideoEntity videoEntity) {
		return new VideoEntityBuilder(videoEntity);
	}

	/**
	 * Builder for the Video Entity class.
	 * 
	 * @author akhil
	 */
	public static class VideoEntityBuilder {
		private long videoId;
		private String title;
		private String description;
		private String originalFileName;
		private String fileExtension;
		private int activeInd;

		public VideoEntityBuilder() {
		}

		public VideoEntityBuilder(VideoEntity videoEntity) {
			this.videoId = videoEntity.videoId;
			this.title = videoEntity.title;
			this.description = videoEntity.description;
			this.originalFileName = videoEntity.originalFileName;
			this.fileExtension = videoEntity.fileExtension;
			this.activeInd = videoEntity.activeInd;
		}

		public VideoEntity build() {
			return new VideoEntity(this);
		}

		public VideoEntityBuilder title(String title) {
			this.title = title;
			return this;
		}

		public VideoEntityBuilder description(String description) {
			this.description = description;
			return this;
		}

		public VideoEntityBuilder originalFileName(String originalFileName) {
			this.originalFileName = originalFileName;
			return this;
		}

		public VideoEntityBuilder fileExtension(String fileExtension) {
			this.fileExtension = fileExtension;
			return this;
		}

		public VideoEntityBuilder activeInd(int activeInd) {
			this.activeInd = activeInd;
			return this;
		}
	}
}
