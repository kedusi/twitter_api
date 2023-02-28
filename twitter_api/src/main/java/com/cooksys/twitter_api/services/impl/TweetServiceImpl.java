package com.cooksys.twitter_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	@Override
	public List<TweetResponseDto> getAllTweets() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TweetResponseDto createTweet(Integer id, UserRequestDto userRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto getTweet(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TweetResponseDto deleteTweet(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void likeTweet(Integer id, UserRequestDto userRequestDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TweetResponseDto createReply(Integer id, UserRequestDto userRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto createRepost(Integer id, UserRequestDto userRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResponseDto> getLikes(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ContextDto getContext(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweetResponseDto> getReplies(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweetResponseDto> getReposts(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResponseDto> getMentions(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HashtagResponseDto> getHashtags(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
