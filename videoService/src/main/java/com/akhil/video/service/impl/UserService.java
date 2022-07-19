package com.akhil.video.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.video.dao.UserRepository;
import com.akhil.video.model.UserEntity;
import com.akhil.video.model.request.UserRequest;
import com.akhil.video.model.response.UserResponse;
import com.akhil.video.service.IUserService;
import com.akhil.video.service.IUserToVideoService;

@Service("userService")
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserResponse getUserByID(long userId) {
		UserEntity userEntity = userRepository.getUserById(userId);
		if (userEntity != null) {
			ModelMapper modelMapper = new ModelMapper();
			UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
			return userResponse;
		}

		return null;
	}

	@Override
	public List<UserResponse> getUsers() {
		List<UserEntity> userEntities = userRepository.getUsers();
		ModelMapper modelMapper = new ModelMapper();
		List<UserResponse> userResponses = new LinkedList<>();
		for (UserEntity userEntity : userEntities) {
			userResponses.add(modelMapper.map(userEntity, UserResponse.class));
		}

		return userResponses;
	}

	@Override
	public Long createUser(UserRequest userRequest) {
		UserEntity entity = buildUserEntity(userRequest);
		if (entity == null) {
			return null;
		}

		Long createdUserId = userRepository.addUser(entity);

		return createdUserId;
	}

	/**
	 * Builds a {@link UserEntity} using the builder if the fields are not null.
	 * 
	 * @param userRequest The user request from which the entity is built.
	 * @return The {@link UserEntity}.
	 */
	private UserEntity buildUserEntity(UserRequest userRequest) {
		if (userRequest.getEmailId() == null || userRequest.getPassword() == null || userRequest.getEmailId().isEmpty()
				|| userRequest.getPassword().isBlank()) {
			return null;
		}

		return UserEntity.builder().emailId(userRequest.getEmailId()).password(userRequest.getPassword()).activeInd(1)
				.accountCreationDate(Timestamp.from(Instant.now())).build();
	}
}
