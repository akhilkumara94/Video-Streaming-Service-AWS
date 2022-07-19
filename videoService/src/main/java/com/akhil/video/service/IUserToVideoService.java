package com.akhil.video.service;

import java.util.List;

public interface IUserToVideoService {
	int addUserToVideo(long userId, long videoId);

	long getUserIdByVideoId(long videoId);

	List<Long> getVideoIdsByUserId(long userId);

	int deleteEntryByVideoId(long videoId);

	int deleteEntriesByUserId(long userId);
}
