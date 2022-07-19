package com.akhil.video.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.video.dao.UserToVideoRepository;
import com.akhil.video.model.UserToVideoEntity;
import com.akhil.video.service.IUserToVideoService;

@Service("userToVideoService")
public class UserToVideoService implements IUserToVideoService {
	final static Logger logger = Logger.getLogger(IUserToVideoService.class);
	@Autowired
	UserToVideoRepository userToVideoRepository;

	@Override
	public int addUserToVideo(long userId, long videoId) {
		logger.log(Level.INFO, "Inserting user to video entry.");
		UserToVideoEntity userToVideoEntity = UserToVideoEntity.builder().userId(userId).videoId(videoId).build();
		try {
			userToVideoRepository.addUserToVideo(userToVideoEntity);
		} catch (Exception exception) {
			logger.log(Level.ERROR, exception);
			return -1;
		}

		return 1;
	}

	@Override
	public long getUserIdByVideoId(long videoId) {
		UserToVideoEntity userToVideoEntity = userToVideoRepository.getUserByVideoId(videoId);
		return userToVideoEntity.getUserId();
	}

	@Override
	public List<Long> getVideoIdsByUserId(long userId) {
		List<UserToVideoEntity> userToVideoEntities = userToVideoRepository.getRecordsByUserId(userId);
		List<Long> videoIds = new LinkedList<>();
		for (UserToVideoEntity userToVideoEntity : userToVideoEntities) {
			videoIds.add(userToVideoEntity.getVideoId());
		}

		return videoIds;
	}

	@Override
	public int deleteEntryByVideoId(long videoId) {
		return userToVideoRepository.deleteRecordByVideoId(videoId);
	}

	@Override
	public int deleteEntriesByUserId(long userId) {
		return userToVideoRepository.deleteRecordByUserId(userId);
	}
}
