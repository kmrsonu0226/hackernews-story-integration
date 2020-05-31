package com.story.integration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.story.integration.exception.NoCommentFoundException;
import com.story.integration.exception.StoryNotFoundException;
import com.story.integration.hackernews.preurl.HackerNewsApiService;
import com.story.integration.model.Comment;
import com.story.integration.model.Story;
import com.story.integration.model.User;
import com.story.integration.repository.StoryRepository;
import com.story.integration.service.impl.StoryServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { StoryIntegrationApplication.class })
public class StoryServiceImplTest {
	
	
	Map<String,User> userMap = new HashMap<>();
	User usr = new User();
	{
		usr.setId("usr1");
		usr.setCreated(new Timestamp((System.currentTimeMillis() - (2L*365L*24L*60L*60L*1000L))/ 1000));
		userMap.put(usr.getId(), usr);
	}
	
	
	List<Integer> replycommentIds = new ArrayList<>();
	{
		for (int i = 500; i < 520; i++) {
			replycommentIds.add(i);
		}
	}
	
	List<Integer> commentIds = new ArrayList<>();
	{
		for (int i = 400; i < 420; i++) {
			commentIds.add(i);
		}
	}
	
	List<Integer> allCommentIds = new ArrayList<>();{
		allCommentIds.addAll(commentIds);
		allCommentIds.addAll(replycommentIds);
	}
	Map<Integer, Comment> replyCommentMap = new HashMap<>();
	{
		for (int i = 500; i < 520; i++) {
			Comment comment = new Comment();
			comment.setId(i);
			comment.setTime(new Timestamp(System.currentTimeMillis() - (new Random().nextInt(15)) / 1000));
			comment.setText("text" + i);
			replyCommentMap.put(i, comment);
		}
	}
	
	Map<Integer, Comment> commentMap = new HashMap<>();
	{
		for (int i = 400; i < 420; i++) {
			int randomReplyIdIndex = new Random().nextInt(replycommentIds.size());
			Comment comment = new Comment();
			comment.setId(i);
			comment.setTime(new Timestamp(System.currentTimeMillis() - (new Random().nextInt(15)) / 1000));
			comment.setText("text" + i);
			comment.setBy(usr.getId());
			comment.setKids(Arrays.asList(replycommentIds.get(randomReplyIdIndex)));
			replycommentIds.remove(randomReplyIdIndex);
			commentMap.put(i, comment);
		}
	}
	
	Map<Integer, Comment> allCommentMap = new HashMap<>();
	{
		allCommentMap.putAll(commentMap);
		allCommentMap.putAll(replyCommentMap);
	}

	List<Integer> storyIds = new ArrayList<>();
	{
		for (int i = 0; i < 20; i++) {
			storyIds.add(i);
		}
	}
	Map<Integer, Story> storyMap = new HashMap<>();
	{
		for (int i = 0; i < 20; i++) {
			int randomCommentIdIndex = new Random().nextInt(commentIds.size());
			Story story = new Story();
			story.setId(i);
			story.setScore(new Random().nextInt(50));
			story.setTime(new Timestamp((System.currentTimeMillis()) / 1000));
			story.setTitle("Title1" + i);
			story.setKids(Arrays.asList(commentIds.get(randomCommentIdIndex)));
			commentIds.remove(randomCommentIdIndex);
			storyMap.put(i, story);
		}
	}
	
	@Test(expected = StoryNotFoundException.class)
	public void getStoriesTest() throws Exception{
			Mockito.when(storyServiceImpl.getTopStoriesDetails(10)).thenReturn(null);
			List<Story> stories = storyServiceImpl.getTopStories();
//			Story s = (Story)hackerNewsApiService.getItemByIdAndClassType(id, Story.class);
			System.out.println(stories);
	}
	
	@Test
	public void getStoriesTest0() {
		try {

			Mockito.when(hackerNewsApiService.getNewStoryFromHackerNews()).thenReturn(storyIds);
			for (Integer id : storyIds) {
				Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
						.thenReturn(storyMap.get(id));
			}
			List<Story> stories = storyServiceImpl.getTopStories();
//			Story s = (Story)hackerNewsApiService.getItemByIdAndClassType(id, Story.class);
			System.out.println(stories);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void getStoriesTest1() {
		try {

			Mockito.when(hackerNewsApiService.getNewStoryFromHackerNews()).thenReturn(storyIds);
			for (Integer id : storyIds) {
				Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
						.thenReturn(storyMap.get(id));
			}
			List<Story> stories = storyServiceImpl.getTopStoriesDetails(10);
//			Story s = (Story)hackerNewsApiService.getItemByIdAndClassType(id, Story.class);
			System.out.println(stories);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test(expected = StoryNotFoundException.class)
	public void getStoriesTest2() throws Exception {
		Mockito.when(hackerNewsApiService.getNewStoryFromHackerNews()).thenReturn(null);
		for (Integer id : storyIds) {
			Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
					.thenReturn(storyMap.get(id));
		}
		List<Story> stories = storyServiceImpl.getTopStoriesDetails(10);

	}

	@Test(expected = IllegalArgumentException.class)
	public void getCommentStoryTest() throws Exception {
		int id = 2;
		Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
				.thenReturn(storyMap.get(id));
		List<Comment> comments = storyServiceImpl.getCommentsOnTheStory(-1);

	}
	
	@Test(expected = StoryNotFoundException.class)
	public void getCommentStoryTest0() throws Exception {
		int id = 2;
		Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
				.thenReturn(null);
		List<Comment> comments = storyServiceImpl.getCommentsOnTheStory(id);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getCommentStoryTest1() throws Exception {
		int id = 2;
		Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
				.thenReturn(storyMap.get(id));
		List<Comment> comments = storyServiceImpl.getCommentsOnTheStory(null);

	}


	@Test(expected = NoCommentFoundException.class)
	public void getCommentStoryTest2() throws Exception {
		int id = 2;
		Story str = storyMap.get(id);
		str.setKids(null);
		storyMap.put(id, str);
		Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
				.thenReturn(storyMap.get(id));
		List<Comment> comments = storyServiceImpl.getCommentsOnTheStory(id);

	}
	
	@Test
	public void getCommentStoryTest3() throws Exception {
		int id = 2;
		Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
				.thenReturn(storyMap.get(id));
		for (Integer comid : allCommentIds) {
			Mockito.when((Comment) hackerNewsApiService.getItemByIdAndClassType(comid, Comment.class))
					.thenReturn(allCommentMap.get(comid));
		}
		Mockito.when(hackerNewsApiService.getUserByUserId(usr.getId())).thenReturn(usr);
		List<Comment> comments = storyServiceImpl.getCommentsOnTheStory(id);
		System.out.println(comments);
	}

	@Test
	public void getCommentStoryTest4() throws Exception {
		int id = 2;
		Story str = storyMap.get(id);
		str.setKids(Arrays.asList(401,402,403,405,406,407,408,409,410,412,417));
		storyMap.put(id, str);
		Mockito.when((Story) hackerNewsApiService.getItemByIdAndClassType(id, Story.class))
				.thenReturn(storyMap.get(id));
		for (Integer comid : allCommentIds) {
			Mockito.when((Comment) hackerNewsApiService.getItemByIdAndClassType(comid, Comment.class))
					.thenReturn(allCommentMap.get(comid));
		}
		Mockito.when(hackerNewsApiService.getUserByUserId(usr.getId())).thenReturn(usr);
		List<Comment> comments = storyServiceImpl.getCommentsOnTheStory(id);
		System.out.println(comments);
	}

	@Test(expected = StoryNotFoundException.class)
	public void getPastStories() throws Exception {
		Mockito.when(storyRepository.findAll()).thenReturn(null);
		List<Story> stories = storyServiceImpl.getPastStories();
		System.out.println(stories);
	}
	
	
	@Autowired
	StoryServiceImpl storyServiceImpl;

	@MockBean
	HackerNewsApiService hackerNewsApiService;
	
	@MockBean
	StoryRepository storyRepository;
	
	

}
