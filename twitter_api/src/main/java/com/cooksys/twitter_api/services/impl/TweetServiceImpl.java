package com.cooksys.twitter_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.ContentCredentialsDto;
import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {
	
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;
	
	// Retrieves a Tweet entity with given id from the database and throws an exception if not found
	private Tweet getTweetFromDb(Integer id) {
		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
		
		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("Unable to find tweet with id: " + id);
		}
		
		return optionalTweet.get();
	}
	
	@Override
	public List<TweetResponseDto> getAllTweets() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TweetResponseDto createTweet(Integer id, ContentCredentialsDto contentCredentialsDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto getTweet(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TweetResponseDto deleteTweet(Integer id, CredentialsRequestDto credentialsRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void likeTweet(Integer id, UserRequestDto userRequestDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TweetResponseDto createReply(Integer id, ContentCredentialsDto contentCredentialsDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto createRepost(Integer id, CredentialsRequestDto credentialsRequestDto) {
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
