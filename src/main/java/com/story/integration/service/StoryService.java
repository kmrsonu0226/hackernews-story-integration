package com.story.integration.service;

import java.util.List;

import com.story.integration.model.Comment;
import com.story.integration.model.Story;

public interface StoryService {

	List<Story> getTopStories() throws Exception;

	List<Comment> getCommentsOnTheStory(Integer storyId) throws Exception;

	List<Story> getPastStories() throws Exception;

}
