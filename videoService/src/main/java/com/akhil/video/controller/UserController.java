package com.akhil.video.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.video.model.request.UserRequest;
import com.akhil.video.model.response.UserResponse;
import com.akhil.video.service.IUserService;
import com.akhil.video.service.IUserToVideoService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	IUserService userService;
	@Autowired
	IUserToVideoService userToVideoService;
	
	@GetMapping("{userId}")
	public ResponseEntity<UserResponse> getUserByID(@PathVariable long userId)
	{
		UserResponse userResponse = userService.getUserByID(userId);
		if(userResponse == null)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(userResponse);
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getUsers()
	{
		return ResponseEntity.ok(userService.getUsers());
	}
	
	@GetMapping("/video/{userId}")
	public ResponseEntity<List<Long>> getVideoIdsByUserId(@PathVariable long userId)
	{
		return ResponseEntity.ok(userToVideoService.getVideoIdsByUserId(userId));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Long> createUser(@RequestBody UserRequest userRequest)
	{
		Long userId = userService.createUser(userRequest);
		if(userId < 0)
		{
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(userId);
	}
}
