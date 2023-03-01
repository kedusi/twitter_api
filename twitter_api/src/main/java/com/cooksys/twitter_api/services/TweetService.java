package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

public interface TweetService {

	public List<TweetResponseDto> getAllTweets();
	
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	public TweetResponseDto getTweet(Integer id);
	
	public TweetResponseDto deleteTweet(Integer id, CredentialsDto credentialsRequestDto);
	
	public void likeTweet(Integer id, UserRequestDto userRequestDto);
	
	public TweetResponseDto createReply(Integer id, TweetRequestDto tweetRequestDto);
	
	public TweetResponseDto createRepost(Integer id, CredentialsDto credentialsRequestDto);
	
	public List<HashtagResponseDto> getHashtags(Integer id);

	public List<UserResponseDto> getLikes(Integer id);
	
	public ContextDto getContext(Integer id);

	public List<TweetResponseDto> getReplies(Integer id);

	public List<TweetResponseDto> getReposts(Integer id);

	public List<UserResponseDto> getMentions(Integer id);

}
