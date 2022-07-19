package com.akhil.video.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.akhil.video.model.VideoEntity;
import com.akhil.video.model.response.VideoResponse;
import com.akhil.video.service.IVideoService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/videos")
public class VideoController {
	final static Logger logger = Logger.getLogger(VideoController.class);

	@Autowired
	IVideoService videoService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VideoResponse> getVideoById(@PathVariable("id") int id) {
		VideoResponse videoResponse = videoService.getVideoById(id);
		if (videoResponse == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(videoResponse);
	}

	@GetMapping(value = "/videos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VideoResponse>> getVideos() {
		List<VideoResponse> videoResponses = videoService.getVideos();

		return ResponseEntity.ok(videoResponses);
	}
	
	@GetMapping(value = "/url/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getVideoUrl(@PathVariable("id") int id) {
		String videoUrl= videoService.getVideoUrlById(id);

		return ResponseEntity.ok(videoUrl);
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addVideo(@RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("userId") long userId,
			@RequestParam("file") MultipartFile file) {
		long videoId = videoService.addVideo(title, description, file, userId);
		if (videoId == -1) {
			return ResponseEntity.ok("Video couldn't be uploaded. Check error logs on server.");
		}
		return ResponseEntity.ok("Video upload with ID: " + String.valueOf(videoId));
	}

	@PostMapping(value = "/updateVideo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateVideoTitleAndDescription(@RequestBody VideoEntity video) {
		if (videoService.updateVideoTitleAndDescription(video) == -1) {
			return ResponseEntity.ok("Video details couldn't be updated. Check error logs on server.");
		}

		return ResponseEntity.ok("Video details updated successfully.");
	}

	@DeleteMapping(value = "/softDelete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> softDeleteVideo(@PathVariable("id") int id) {
		if (videoService.softDeleteVideo(id) == -1) {
			return ResponseEntity.ok("Video couldn't be deleted. Check error logs on server.");
		}
		return ResponseEntity.ok("Video deleted successfully.");
	}

	@DeleteMapping(value = "/hardDelete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> hardDeleteVideo(@PathVariable("id") int id) {
		if (videoService.hardDeleteVideo(id) == -1) {
			return ResponseEntity.ok("Video could not be completely deleted from S3 and DB.");
		}

		return ResponseEntity.ok("Video deleted successfully");
	}
}