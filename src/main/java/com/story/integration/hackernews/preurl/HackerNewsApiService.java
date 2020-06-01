package com.story.integration.hackernews.preurl;

import java.sql.Timestamp;
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
import com.story.integration.model.Story;
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
	@SuppressWarnings("unchecked")
	public Object getItemByIdAndClassType(Integer id, Class<?> cls) {
		Map<String, Object>  uriVarables = new HashMap<>();
		uriVarables.put("id", id);
		try {
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HACKERNEWS_URL + "/item/{id}.json").uriVariables(uriVarables);
			ResponseEntity<Object> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null,
					Object.class);
			if (response != null && response.getBody() != null) {
				return mapper.readValue(mapper.writeValueAsString(response.getBody()), cls);
			}
		} catch (Exception e) {
			logger.error("Error in fetching item details due to {}", e.getMessage());
		}
		return null;
	}

	/*
	 * to get story details
	 * 
	 * Not used as only for Story
	 */
	@SuppressWarnings("unchecked")
	public Story getStoryById(Integer id) throws Exception {
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HACKERNEWS_URL + "/item/" + id + ".json");
		ResponseEntity<Object> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null, Object.class);
		if (response != null && response.getBody() != null) {
			Story ns = new Story();
			HashMap<String, Object> map = mapper.readValue(mapper.writeValueAsString(response.getBody()),
					HashMap.class);
			if (map != null) {
				ns.setId((int) map.getOrDefault("id", 0));
				ns.setScore(Integer.valueOf(map.getOrDefault("score", 0).toString()));
				ns.setTitle(map.get("title").toString());
				ns.setBy(map.get("by").toString());
				ns.setTime(new Timestamp(Long.valueOf(map.get("time").toString()) * 1000l));
				ns.setUrl(map.getOrDefault("url", "").toString());
				ns.setKids((ArrayList<Integer>) map.getOrDefault("kids", new ArrayList<>()));
				return ns;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getNewStoryFromHackerNews() throws Exception {
		List<Integer> storyList = new ArrayList<>();
		try {
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HACKERNEWS_URL + "/newstories.json");
			ResponseEntity<Object> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null,
					Object.class);
			if (response != null && response.getBody() != null) {
				storyList = mapper.readValue(mapper.writeValueAsString(response.getBody()), List.class);
			}
			return storyList;
		} catch (Exception e) {
			logger.error("Error in fetching new stories details due to {}", e.getMessage());
		}
		return storyList;
	}

	@SuppressWarnings("unchecked")
	public User getUserByUserId(String userId) {
		User user = null;
		try {
			UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(HACKERNEWS_URL + "/user/" + userId + ".json");
			ResponseEntity<Object> response = restTemplate.exchange(uri.toUriString(), HttpMethod.GET, null,
					Object.class);
			if (response != null && response.getBody() != null) {
				user = mapper.readValue(mapper.writeValueAsString(response.getBody()), User.class);
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
