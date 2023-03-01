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

	public TweetResponseDto getTweet(Long id);
	
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsRequestDto);
	
	public void likeTweet(Long id, UserRequestDto userRequestDto);
	
	public TweetResponseDto createReply(Long id, TweetRequestDto tweetRequestDto);
	
	public TweetResponseDto createRepost(Long id, CredentialsDto credentialsRequestDto);
	
	public List<HashtagResponseDto> getHashtags(Long id);

	public List<UserResponseDto> getLikes(Long id);
	
	public ContextDto getContext(Long id);

	public List<TweetResponseDto> getReplies(Long id);

	public List<TweetResponseDto> getReposts(Long id);

	public List<UserResponseDto> getMentions(Long id);

}
