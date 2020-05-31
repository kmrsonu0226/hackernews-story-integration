package com.story.integration.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.story.integration.exception.BadRequestException;
import com.story.integration.model.Comment;
import com.story.integration.model.Story;
import com.story.integration.response.model.Response;
import com.story.integration.service.StoryService;

import javassist.NotFoundException;

@RestController
public class StoryController {

	@GetMapping(value = "/top-stories")
	public Response<List<Story>> getTopStories() throws Exception {
		List<Story> newStory = new ArrayList<>();
		try {
			newStory = storyService.getTopStories();
			if (newStory.isEmpty()) {
				throw new NotFoundException("no new story");
			}
			return new Response<List<Story>>(newStory);
		} catch (Exception e) {
			logger.error("error in fetching stories due to : {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping(value = "/comment")
	public Response<List<Comment>> getCommentsOfStory(@RequestParam(value = "story-id") Integer storyId)
			throws Exception {
		try {
			if (storyId == null || storyId <= 0) {
				throw new BadRequestException("Please provide story-id");
			}
			return new Response<List<Comment>>(storyService.getCommentsOnTheStory(storyId));
		} catch (Exception e) {
			logger.error("Unable to get comment due to : {}", e.getMessage());
			throw e;
		}
	}

	@GetMapping(value = "/past-stories")
	public Response<List<Story>> getPastStories() throws Exception {
		try {
			return new Response<List<Story>>(storyService.getPastStories());
		} catch (Exception e) {
			logger.error("Unable to fetch past stories due to : {}", e.getMessage());
			throw e;
		}
	}

	private static Logger logger = LoggerFactory.getLogger(StoryController.class);

	@Autowired
	private StoryService storyService;
}
