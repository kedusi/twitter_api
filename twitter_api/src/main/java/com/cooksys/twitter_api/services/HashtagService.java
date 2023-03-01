package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;

public interface HashtagService {

	List<HashtagResponseDto> GetAllHashtags();

	List<Tweet> GetTweetsByTag(String label);

}
