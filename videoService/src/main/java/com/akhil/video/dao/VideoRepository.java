package com.akhil.video.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.akhil.video.HibernateUtility;
import com.akhil.video.model.VideoEntity;
import com.akhil.video.service.impl.VideoService;

@Repository
public class VideoRepository {
	final static Logger logger = Logger.getLogger(VideoRepository.class);
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public VideoEntity getVideoById(final long id) {
		Session session = sessionFactory.getCurrentSession();
		VideoEntity video = session.get(VideoEntity.class, id);
		if (video != null && video.getActiveInd() == 0) {
			return null;
		}

		return video;
	}

	@Transactional
	public List<VideoEntity> getVideos() {
		Session session = sessionFactory.getCurrentSession();
		List<VideoEntity> videos = session.createQuery("from VideoEntity v where v.activeInd=1").list();

		return videos;
	}

	@Transactional
	public Long addVideo(VideoEntity video) {
		Session session = sessionFactory.getCurrentSession();
		Long videoId = (Long) session.save(video);

		return videoId;
	}

	@Transactional
	public int updateVideoTitleAndDescription(VideoEntity video) {
		try {
			Session session = sessionFactory.getCurrentSession();
			VideoEntity updatedVideo = (VideoEntity) session.get(VideoEntity.class, Long.valueOf(video.getVideoId()));
			if (video != null && updatedVideo != null) {
				updatedVideo = VideoEntity.builder(updatedVideo).title(video.getTitle())
						.description(video.getDescription()).build();
			}
			session.update(updatedVideo);
		} catch (Exception exception) {
			logger.log(Level.ERROR, exception);
			return -1;
		}
		return 1;
	}

	@Transactional
	public int softDelete(long id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			VideoEntity video = (VideoEntity) session.get(VideoEntity.class, Long.valueOf(id));
			if (video != null) {
				video = VideoEntity.builder(video).activeInd(0).build();
			}
			session.update(video);
		} catch (Exception exception) {
			logger.log(Level.ERROR, exception);
			return -1;
		}
		return 1;
	}

	@Transactional
	public int hardDelete(long id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			VideoEntity video = (VideoEntity) session.load(VideoEntity.class, Long.valueOf(id));
			if (video != null) {
				session.delete(video);
			}
		} catch (Exception exception) {
			logger.log(Level.ERROR, exception);
			return -1;
		}
		return 1;
	}
}
