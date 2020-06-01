package com.story.integration.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.story.integration.exception.BadRequestException;
import com.story.integration.service.StoryService;


@RestController
@RequestMapping("/v1/story")
public class StoryController {

	@GetMapping(value = "/top-stories")
	public ResponseEntity<List<Map<String, Object>>> getTopStories() throws Exception {
		return new ResponseEntity<List<Map<String, Object>>>(storyService.getTopStories(), HttpStatus.OK);

	}

	@GetMapping(value = "/comment")
	public ResponseEntity<List<Map<String, Object>>> getCommentsOfStory(@RequestParam(value = "story-id") Integer storyId)
			throws Exception {
		if (storyId == null || storyId <= 0) {
			throw new BadRequestException("Please provide story-id");
		}
		return new ResponseEntity<List<Map<String, Object>>>(storyService.getCommentsOnTheStory(storyId), HttpStatus.OK);
	}

	@GetMapping(value = "/past-stories")
	public ResponseEntity<List<Map<String, Object>>> getPastStories() throws Exception {

		return new ResponseEntity<List<Map<String, Object>>>(storyService.getPastStories(), HttpStatus.OK);
	}

	@Autowired
	private StoryService storyService;
}
