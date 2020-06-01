package com.story.integration.hackernews.preurl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.story.integration.model.User;

@Service
public class HackerNewsApiService {

	@Value("${endpoints.hackernews-api}")
	private String HACKERNEWS_URL;

	/*
	 * to get item details
	 * 
	 * generic response for all items whether it is comment or story should be
	 * casted to the desired object
	 */
	public Object getItemByIdAndClassType(Integer id, Class<?> cls) {
		Map<String, Object>  uriVariables = new HashMap<>();
		uriVariables.put("id", id);
		try {
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HACKERNEWS_URL + "/item/{id}.json").uriVariables(uriVariables);
			ResponseEntity<String> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null,
					String.class);
			if (response != null && response.getBody() != null) {
				return mapper.readValue(response.getBody(), cls);
			}
		} catch (Exception e) {
			logger.error("Error in fetching item details due to {}", e.getMessage());
		}
		return null;
	}

	/*
	 * to get all new stories id list from hacker news
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getNewStoryFromHackerNews() {
		List<Integer> storyList = null;
		try {
			storyList = new ArrayList<>();
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HACKERNEWS_URL + "/newstories.json");
			ResponseEntity<String> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null,
					String.class);
			if (response != null && response.getBody() != null) {
				storyList = mapper.readValue(response.getBody(), List.class);
			}
			return storyList;
		} catch (Exception e) {
			logger.error("Error in fetching new stories details due to {}", e.getMessage());
		}
		return storyList;
	}

	/*
	 * to get user details from the hackernews
	 */
	public User getUserByUserId(String userId) {
		Map<String, Object>  uriVariables = new HashMap<>();
		uriVariables.put("userId", userId);
		User user = null;
		try {
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HACKERNEWS_URL + "/user/{userId}.json").uriVariables(uriVariables);
			ResponseEntity<String> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null,
					String.class);
			if (response != null && response.getBody() != null) {
				user = mapper.readValue(response.getBody(), User.class);
			}
			return user;
		} catch (Exception e) {
			logger.error("Error in fetching user details due to {}", e.getMessage());
		}
		return user;
	}

	private ObjectMapper mapper = new ObjectMapper();

	private static Logger logger = LoggerFactory.getLogger(HackerNewsApiService.class);

	@Autowired
	private RestTemplate restTemplate;

}
