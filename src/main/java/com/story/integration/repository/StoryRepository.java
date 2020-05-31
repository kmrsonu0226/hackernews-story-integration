package com.story.integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.story.integration.model.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, String> {

	List<Story> findAll();

}
