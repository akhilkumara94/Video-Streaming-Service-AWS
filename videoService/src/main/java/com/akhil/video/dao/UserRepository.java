package com.akhil.video.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.akhil.video.model.UserEntity;

@Repository
public class UserRepository {
	final static Logger logger = Logger.getLogger(UserRepository.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public UserEntity getUserById(final long id) {
		logger.info("Request received to fetch user by user Id.");
		Session session = sessionFactory.getCurrentSession();
		UserEntity user = session.get(UserEntity.class, id);
		if (user != null && user.getActiveInd() == 0) {
			return null;
		}

		return user;
	}

	@Transactional
	public List<UserEntity> getUsers() {
		logger.info("Request received to fetch all users.");
		Session session = sessionFactory.getCurrentSession();
		List<UserEntity> users = session.createQuery("from UserEntity v where v.activeInd=1").list();

		return users;
	}

	@Transactional
	public Long addUser(UserEntity user) {
		logger.info("Request received to add a new user.");
		Session session = sessionFactory.getCurrentSession();
		Long userId = (Long) session.save(user);

		return userId;
	}
}
