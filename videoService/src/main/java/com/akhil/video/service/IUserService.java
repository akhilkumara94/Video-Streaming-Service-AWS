package com.akhil.video.service;

import java.util.List;

import com.akhil.video.model.request.UserRequest;
import com.akhil.video.model.response.UserResponse;

public interface IUserService {

	UserResponse getUserByID(long userId);

	List<UserResponse> getUsers();

	Long createUser(UserRequest userRequest);
}
