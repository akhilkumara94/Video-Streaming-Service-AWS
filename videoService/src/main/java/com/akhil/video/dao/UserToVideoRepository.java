package com.akhil.video.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akhil.video.HibernateUtility;
import com.akhil.video.model.UserEntity;
import com.akhil.video.model.UserToVideoEntity;
import com.akhil.video.model.VideoEntity;

@Repository
public class UserToVideoRepository {
	final static Logger logger = Logger.getLogger(UserToVideoRepository.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public UserToVideoEntity getUserByVideoId(final long videoId) {
		logger.info("Request received to get user by video Id.");
		Session session = sessionFactory.getCurrentSession();
		UserToVideoEntity userToVideoEntity = session.get(UserToVideoEntity.class, videoId);
		if (userToVideoEntity != null) {
			return null;
		}

		return userToVideoEntity;
	}

	@Transactional
	public void addUserToVideo(final UserToVideoEntity userToVideoEntity) {
		logger.info("Request received to add a new entry in user to video.");
		Session session = sessionFactory.getCurrentSession();
		session.save(userToVideoEntity);
	}

	@Transactional
	public List<UserToVideoEntity> getRecordsByUserId(final long userId) {
		logger.info("Request received to get all records by userId.");
		Session session = sessionFactory.getCurrentSession();
		List<UserToVideoEntity> userToVideoEntities = session.createQuery("from UserToVideoEntity u where u.userId=" + userId)
				.list();

		return userToVideoEntities;
	}

	@Transactional
	public int deleteRecordByVideoId(final long videoId) {
		logger.info("Request received to delete record by video Id.");
		try {
			Session session = sessionFactory.getCurrentSession();
			UserToVideoEntity userToVideoEntity = (UserToVideoEntity) session.load(UserToVideoEntity.class,
					Long.valueOf(videoId));
			if (userToVideoEntity != null) {
				session.delete(userToVideoEntity);
			}
		} catch (Exception exception) {
			logger.log(Level.ERROR, exception);
			return -1;
		}

		return 1;
	}

	@Transactional
	public int deleteRecordByUserId(final long userId) {
		logger.info("Request received to delete all records associated to user Id.");
		try {
			Session session = sessionFactory.getCurrentSession();
			List<UserToVideoEntity> userToVideoEntities = session
					.createQuery("from uservideo u where u.userId=" + userId).list();
			for (UserToVideoEntity userToVideoEntity : userToVideoEntities) {
				session.delete(userToVideoEntity);
			}
		} catch (Exception exception) {
			logger.log(Level.ERROR, exception);
			return -1;
		}

		return 1;
	}
}
