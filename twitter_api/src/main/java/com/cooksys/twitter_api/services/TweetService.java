package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

public interface TweetService {

	public List<TweetResponseDto> getAllTweets();

	public TweetResponseDto getTweet(Integer id);

	public List<UserResponseDto> getLikes(Integer id);

	public List<TweetResponseDto> getReplies(Integer id);

	public List<TweetResponseDto> getReposts(Integer id);

	public List<UserResponseDto> getMentions(Integer id);

}
