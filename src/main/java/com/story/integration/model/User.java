package com.story.integration.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6622343069668388528L;

	private String id; // The user's unique username. Case-sensitive. Required.
	private int delay; // Delay in minutes between a comment's creation and its visibility to other
						// users.
	private Timestamp created; // Creation date of the user, in Unix Time.
	private int karma; // The user's karma.
	private String about; // The user's optional self-description. HTML.
	private List<Integer> submitted; // List of the user's stories, polls and comments.

	public void setCreated(Timestamp created) {
		if (created != null) {
			created = new Timestamp(Long.valueOf(created.getTime()) * 1000);
		}
		this.created = created;
	}
}
