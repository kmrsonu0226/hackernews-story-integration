package com.story.integration.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Entity(name = "story")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Story implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private String title;
	private String url;
	private int score;
	private Timestamp time;
	private String by;

	@Transient
	private List<Integer> kids;

	public void setTime(Timestamp time) {
		if (time != null) {
			time = new Timestamp(Long.valueOf(time.getTime()) * 1000);
		}
		this.time = time;
	}

}
