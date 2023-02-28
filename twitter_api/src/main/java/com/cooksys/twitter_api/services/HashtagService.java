package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;

public interface HashtagService {

	List<Hashtag> GetAllHashtags();

	List<Tweet> GetTweetsByTag(String label);

}
