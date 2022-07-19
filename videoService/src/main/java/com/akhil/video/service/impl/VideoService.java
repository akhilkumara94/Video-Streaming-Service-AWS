package com.akhil.video.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.akhil.video.AWSS3Properties;
import com.akhil.video.dao.VideoRepository;
import com.akhil.video.model.VideoEntity;
import com.akhil.video.model.response.VideoResponse;
import com.akhil.video.service.IUserToVideoService;
import com.akhil.video.service.IVideoService;

@Service("videoService")
public class VideoService implements IVideoService {
	final static Logger logger = Logger.getLogger(IVideoService.class);

	@Autowired
	VideoRepository videoRepository;
	@Autowired
	IUserToVideoService userToVideoService;
	@Autowired
	AWSS3Properties awss3Properties;
	@Autowired
	AWSS3Service awsS3Service;

	@Override
	public VideoResponse getVideoById(int id) {
		VideoEntity videoEntity = videoRepository.getVideoById(id);
		if (videoEntity == null) {
			return null;
		}

		ModelMapper modelMapper = new ModelMapper();
		VideoResponse videoResponse = modelMapper.map(videoEntity, VideoResponse.class);

		return videoResponse;
	}

	@Override
	public List<VideoResponse> getVideos() {
		List<VideoEntity> videoEntities = videoRepository.getVideos();
		ModelMapper modelMapper = new ModelMapper();
		List<VideoResponse> videoReponses = new LinkedList<>();
		for (VideoEntity videoEntity : videoEntities) {
			videoReponses.add(modelMapper.map(videoEntity, VideoResponse.class));
		}

		return videoReponses;
	}

	@Override
	public String getVideoUrlById(long videoId) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(awss3Properties.getAWSProperties().get("aws.cloudfront.url"));
		urlBuilder.append("/").append(String.valueOf(videoId));
		urlBuilder.append("/Default/HLS/");
		urlBuilder.append(String.valueOf(videoId));
		urlBuilder.append(".m3u8");

		return urlBuilder.toString();
	}

	@Override
	public long addVideo(String title, String description, MultipartFile file, long userId) {
		VideoEntity video = VideoEntity.builder().title(title).description(description)
				.originalFileName(file.getOriginalFilename())
				.fileExtension(FilenameUtils.getExtension(file.getOriginalFilename())).activeInd(1).build();
		Long videoId = videoRepository.addVideo(video);

		VideoStateMachine stateMachine = new VideoStateMachine();
		stateMachine.getCurrentState().handleRequest(file, video, videoId);

		if (stateMachine.getCurrentState() instanceof VideoUploadFailState) {
			logger.error("Video upload failed. Video entries will be deleted from DB.");
			return -1;
		} else {
			userToVideoService.addUserToVideo(userId, videoId);
		}
		return videoId;
	}

	@Override
	public int updateVideoTitleAndDescription(VideoEntity video) {
		int status = videoRepository.updateVideoTitleAndDescription(video);

		return status;
	}

	@Override
	public int softDeleteVideo(long id) {
		if (videoRepository.softDelete(id) == -1) {
			return -1;
		}

		if (userToVideoService.deleteEntryByVideoId(id) == -1) {
			return -1;
		}
		return 1;
	}

	@Override
	public int hardDeleteVideo(long id) {
		if (false == deleteVideoFromS3(id)) {
			return -1;
		}

		if (-1 == videoRepository.hardDelete(id)) {
			return -1;
		}

		if (userToVideoService.deleteEntryByVideoId(id) == -1) {
			return -1;
		}

		return 1;
	}

	/**
	 * Deletes the video from the S3 bucket by calling awsS3 service if the video is
	 * present.
	 * 
	 * @param id The video ID.
	 * @return True if the video is deleted, otherwise false.
	 */
	private boolean deleteVideoFromS3(long id) {
		try {
			VideoEntity videoEntity = videoRepository.getVideoById(id);
			if (videoEntity != null) {
				return awsS3Service.delete(Long.toString(videoEntity.getVideoId()));
			}
		} catch (Exception exception) {
			logger.error(exception);
			return false;
		}

		return true;
	}
}
