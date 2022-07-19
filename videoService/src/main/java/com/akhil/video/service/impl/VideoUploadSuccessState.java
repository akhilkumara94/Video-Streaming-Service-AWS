package com.akhil.video.service.impl;

import org.springframework.web.multipart.MultipartFile;

import com.akhil.video.model.VideoEntity;

/**
 * The class representing the success state of the video upload.
 * 
 * @author akhil
 *
 */
public class VideoUploadSuccessState extends VideoState {
	@Override
	public void handleRequest(MultipartFile file, VideoEntity video, Long videoId) {
		logger.info("Video has been uploaded successfully.");
	}
}
