package com.story.integration.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.story.integration.exception.NoCommentFoundException;
import com.story.integration.exception.StoryNotFoundException;
import com.story.integration.hackernews.preurl.HackerNewsApiService;
import com.story.integration.model.Comment;
import com.story.integration.model.Story;
import com.story.integration.model.User;
import com.story.integration.repository.StoryRepository;
import com.story.integration.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Override
	@Cacheable("newTopStories")
	public List<Map<String, Object>> getTopStories() throws Exception {
		List<Story> stories = new ArrayList<>();
		int minutes = 10;
		stories = getTopStoriesDetails(minutes);
		if (stories == null || stories.isEmpty()) {
			throw new StoryNotFoundException("No story found");
		}
		if (stories.size() > 10) {
			stories = stories.subList(0, 10);
		}
		storyRepository.saveAll(stories);
		return getStoryResponse(stories);
	}
	
	private List<Map<String, Object>> getStoryResponse(List<Story> storyList) {
		return storyList.stream()
			.map(s -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", s.getId());
				map.put("title", s.getTitle());
				map.put("url", s.getUrl());
				map.put("score", s.getScore());
				map.put("time", s.getTime());
				map.put("by", s.getBy());
				return map;
			}).collect(Collectors.toList());
	}

	

	public List<Story> getTopStoriesDetails(int minutes) throws Exception {
		try {
			Timestamp tenMinBefore = Timestamp.valueOf(LocalDateTime.now().minusMinutes(minutes));
			List<Story> storyList = new ArrayList<>();
			List<Integer> storyLines = new ArrayList<>();
			storyLines = hackerNewsApiService.getNewStoryFromHackerNews();
			if (storyLines == null || storyLines.isEmpty()) {
				throw new StoryNotFoundException("No story found");
			}
			System.out.println(storyLines);
			for (Integer id : storyLines) {
				Story ns = new Story();
				// ns = hackerNewsApiService.getStoryById(id);
				ns = (Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class);
				if (ns != null && ns.getTime().before(tenMinBefore)) {
					break;
				}
				if (ns != null) {
					storyList.add(ns);
				}
			}
			return storyList.parallelStream().filter(Objects::nonNull)
					.filter(story -> story.getTime() != null && story.getTime().after(tenMinBefore))
					.sorted(Comparator.comparing(Story::getScore).reversed()).collect(Collectors.toList());

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	@Cacheable("parentComments")
	public List<Map<String, Object>> getCommentsOnTheStory(Integer storyId) throws Exception {
		if (storyId == null || storyId < 0) {
			throw new IllegalArgumentException("Invalid parameter");
		}
		List<Comment> parentCommentList = new ArrayList<>();
		try {
			Story story = (Story) hackerNewsApiService.getItemByIdAndClassType(storyId, Story.class);
			if (story == null) {
				throw new StoryNotFoundException("No story Found");
			}
			if (story.getKids() == null || story.getKids().isEmpty()) {
				throw new NoCommentFoundException("No comment Found");
			}
			List<Integer> commentIds = story.getKids();
			commentIds.parallelStream().forEach(id -> {
				Comment parentComment = (Comment) hackerNewsApiService.getItemByIdAndClassType(id, Comment.class);
				if (parentComment != null) {
					User user = hackerNewsApiService.getUserByUserId(parentComment.getBy());
					if (user != null) {
						parentComment
								.setUserAge((System.currentTimeMillis() - user.getCreated().getTime()) / 31536000000L); // (365*24*60*60*1000)
					}
					if (parentComment.getKids() != null && !parentComment.getKids().isEmpty()) {
						parentComment.setReplyCount(parentWithReplyCommentCount(id));
					}
					parentCommentList.add(parentComment);
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		Collections.sort(parentCommentList, Comparator.comparing(Comment::getReplyCount).reversed());
		if (parentCommentList.size() > 10) {
			return getCommentResponse(parentCommentList.subList(0, 10));
		}
		return getCommentResponse(parentCommentList);
	}
	
	private List<Map<String, Object>> getCommentResponse(List<Comment> parentCommentList) {
		return parentCommentList.stream()
			.map(c -> {
				Map<String, Object> map = new HashMap<>();
				map.put("id", c.getId());
				map.put("text", c.getText());
				map.put("userHandle", c.getBy());
				map.put("userAge", c.getUserAge());
				return map;
			}).collect(Collectors.toList());
	}

	/*
	 * 
	 * To find the chained reply counts of each of the item
	 */
	private int parentWithReplyCommentCount(Integer id) {
		Comment comment = (Comment) hackerNewsApiService.getItemByIdAndClassType(id, Comment.class);
		if (comment.getKids() == null || comment.getKids().isEmpty()) {
			return comment.getReplyCount();
		}
//		comment.getKids().stream().forEach(commentId -> {
//			comment.setReplyCount(comment.getReplyCount()  + 1 + parentWithReplyCommentCount(commentId));
//		});
		for (Integer commentId : comment.getKids()) {
			comment.setReplyCount(comment.getReplyCount() + 1 + parentWithReplyCommentCount(commentId));
		}
		return comment.getReplyCount();
	}

	@Override
	public List<Map<String, Object>> getPastStories() throws Exception {
		List<Story> stories = new ArrayList<>();
		try {
			stories = storyRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		if (stories == null || stories.isEmpty()) {
			throw new StoryNotFoundException(" No past Stories found");
		}
		return getStoryResponse(stories);
	}

	private static Logger logger = LoggerFactory.getLogger(StoryServiceImpl.class);

	@Autowired
	private HackerNewsApiService hackerNewsApiService;

	@Autowired
	private StoryRepository storyRepository;

}
