package com.story.integration.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -550582131843770728L;
	private int id;
	private boolean deleted; // true if the item is deleted.
	private String type; // The type of item. One of "job", "story", "comment", "poll", or "pollopt".
	private String by; // The username of the item's author.
	private Timestamp time; // Creation date of the item, in Unix Time.
	private String text; // The comment, story or poll text. HTML.
	private boolean dead; // true if the item is dead.
	private int parent; // The comment's parent: either another comment or the relevant story.
	private int poll; // The pollopt's associated poll.
	private List<Integer> kids; // The ids of the item's comments, in ranked display order.
	private String url; // The URL of the story.
	private int score; // The story's score, or the votes for a pollopt.
	private String title; // The title of the story, poll or job. HTML.
	private List<Integer> parts; // A list of related pollopts, in display order.
	private int descendants; // in the case of stories or polls, the total comment count.

}
