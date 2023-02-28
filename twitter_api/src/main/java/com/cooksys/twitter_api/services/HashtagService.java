package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.entities.Hashtag;

public interface HashtagService {

	List<Hashtag> GetAllHashtags();

	List<Hashtag> GetByTag(String label);

}
