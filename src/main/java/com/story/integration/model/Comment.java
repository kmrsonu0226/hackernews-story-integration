package com.story.integration.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8547123311325712954L;
	private int id;
	private String type;
	private String by;
	private Timestamp time;
	private String text;
	private int parent;
	private List<Integer> kids;
	private int replyCount;
	private long userAge;

	public void setTime(Timestamp time) {
		if (time != null) {
			time = new Timestamp(Long.valueOf(time.getTime()) * 1000);
		}
		this.time = time;
	}
}
