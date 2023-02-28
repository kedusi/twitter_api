package com.cooksys.twitter_api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.services.HashtagService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("tags")
public class HashtagController {

	private HashtagService hashtagService;
	
	@GetMapping
	private List<Hashtag> GetAllHashtags(){
		return hashtagService.GetAllHashtags();
	}
	
}
