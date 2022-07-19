package com.akhil.video.service.impl;

import org.springframework.stereotype.Component;

/**
 * The state machine maintaining the state of the video upload.
 * 
 * @author akhil
 *
 */
@Component
public class VideoStateMachine {
	VideoState videoUploadInitialState;
	VideoState videoUploadSuccessState;
	VideoState videoUploadFailState;

	VideoState videoState;

	/**
	 * Creates all the states and then sets the initial state of the machine.
	 */
	public VideoStateMachine() {
		videoUploadInitialState = new VideoUploadInitialState(this);
		videoUploadSuccessState = new VideoUploadSuccessState();
		videoUploadFailState = new VideoUploadFailState();

		videoState = videoUploadInitialState;
	}

	/**
	 * Setter to set the video state.
	 * 
	 * @param videoState The video state to be set as the current state.
	 */
	public void setVideoState(VideoState videoState) {
		this.videoState = videoState;
	}

	/**
	 * Returns the initial states of the machine.
	 * 
	 * @return The initial state of the machine.
	 */
	public VideoState getVideoUploadInitialState() {
		return videoUploadInitialState;
	}

	/**
	 * Gets the success state of the machine.
	 * 
	 * @return The success state of the machine.
	 */
	public VideoState getVideoUploadSuccessState() {
		return videoUploadSuccessState;
	}

	/**
	 * Gets the fail state of the machine.
	 * 
	 * @return The fail state of the machine.
	 */
	public VideoState getVideoUploadFailState() {
		return videoUploadFailState;
	}

	/**
	 * Gets the current state of the machine.
	 * 
	 * @return The current state of the machine.
	 */
	public VideoState getCurrentState() {
		return videoState;
	}
}
