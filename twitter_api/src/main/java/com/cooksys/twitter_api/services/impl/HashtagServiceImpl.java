package com.cooksys.twitter_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	@Override
	public List<Hashtag> GetAllHashtags() {
		// TODO GetAllTags for htServiceImpl
		return null;
	}

	@Override
	public List<Tweet> GetTweetsByTag(String label) {
		// TODO GetTweetsByTag for HTServImpl
		return null;
	}

}
