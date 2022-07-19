package com.akhil.video.service.impl;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.akhil.video.model.VideoEntity;

/**
 * Abstract class to maintain the state of the video.
 * 
 * @author akhil
 */
public abstract class VideoState {
	final static Logger logger = Logger.getLogger(VideoState.class);

	/**
	 * Takes the file and process it based on the state.
	 * 
	 * @param file    The file to be processed.
	 * @param video   The video entity.
	 * @param videoId The video Id of the video.
	 */
	public void handleRequest(MultipartFile file, VideoEntity video, Long videoId) {
		logger.error("This state should not be accessible. Error occured.");
	}
}
