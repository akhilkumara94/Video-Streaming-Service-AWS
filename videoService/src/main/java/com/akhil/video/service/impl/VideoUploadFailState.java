package com.akhil.video.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.akhil.video.dao.VideoRepository;
import com.akhil.video.model.VideoEntity;

/**
 * The class representing the failed state of video upload.
 * 
 * @author akhil
 *
 */
public class VideoUploadFailState extends VideoState {
	@Override
	public void handleRequest(MultipartFile file, VideoEntity video, Long videoId) {
		logger.info("Video has been uploaded successfully.");
		VideoRepository videoRepository = new VideoRepository();
		videoRepository.hardDelete(videoId);
	}
}
