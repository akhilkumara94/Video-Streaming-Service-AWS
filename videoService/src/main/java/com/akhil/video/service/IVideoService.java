package com.akhil.video.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.akhil.video.model.VideoEntity;
import com.akhil.video.model.response.VideoResponse;

public interface IVideoService {
	VideoResponse getVideoById(int id);

	List<VideoResponse> getVideos();

	long addVideo(String title, String description, MultipartFile file, long userId);

	int updateVideoTitleAndDescription(VideoEntity video);

	int softDeleteVideo(long id);

	int hardDeleteVideo(long id);

	String getVideoUrlById(long videoId);
}
