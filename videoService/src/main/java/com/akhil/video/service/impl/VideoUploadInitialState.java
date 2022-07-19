package com.akhil.video.service.impl;

import org.springframework.web.multipart.MultipartFile;

import com.akhil.video.model.VideoEntity;

/**
 * The class representing the initial state of the video upload.
 * 
 * @author akhil
 */
public class VideoUploadInitialState extends VideoState {
	private VideoStateMachine videoStateMachine;

	public VideoUploadInitialState(VideoStateMachine videoStateMachine) {
		this.videoStateMachine = videoStateMachine;
	}

	@Override
	public void handleRequest(MultipartFile file, VideoEntity video, Long videoId) {
		AWSS3Service awsS3Service = new AWSS3Service();
		try {
			if (awsS3Service.upload(videoId.toString(), video.getFileExtension(), file.getInputStream(),
					file.getSize())) {
				logger.info("Video has been uploaded successfully. Moving it to success state.");
				videoStateMachine.setVideoState(videoStateMachine.getVideoUploadSuccessState());
			}
			else
			{
				logger.info("Video has failed to upload. Moving it to failed state");
				videoStateMachine.setVideoState(videoStateMachine.getVideoUploadFailState());
				videoStateMachine.getCurrentState().handleRequest(file, video, videoId);
			}
		} catch (Exception exception) {
			logger.error(exception);
			logger.info("Video has failed to upload. Moving it to failed state");
			videoStateMachine.setVideoState(videoStateMachine.getVideoUploadFailState());
			videoStateMachine.getCurrentState().handleRequest(file, video, videoId);
		}
	}
}
