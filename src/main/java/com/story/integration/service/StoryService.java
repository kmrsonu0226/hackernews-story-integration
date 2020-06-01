package com.story.integration.service;

import java.util.List;
import java.util.Map;

import com.story.integration.model.Story;

public interface StoryService {

	List<Map<String, Object>> getTopStories() throws Exception;

	List<Map<String, Object>> getCommentsOnTheStory(Integer storyId) throws Exception;

	List<Map<String, Object>> getPastStories() throws Exception;

}
