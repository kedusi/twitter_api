package com.cooksys.twitter_api.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
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
	private final TweetMapper tweetMapper;
	private final CredentialsMapper credentialsMapper;
	
	// Retrieves a Tweet entity with given id from the database and throws an exception if not found
	private Tweet getTweetFromDb(Integer id) {
		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
		
		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("Unable to find tweet with id: " + id);
		}
		
		return optionalTweet.get();
	}
	
	// Checks that user with given username exists and that passwords match
	private User verifyCredentials(Credentials requestCredentials) {
		
		User user = userRepository.findByCredentials_Username(requestCredentials.getUsername());
		
		if (user == null) {
			throw new NotFoundException("User not found");
		}
		
		Credentials userCredentials = user.getCredentials();
		
		if (requestCredentials.getPassword() != userCredentials.getPassword()) {
			throw new NotAuthorizedException("Incorrect password provided");
		}
		
		return user;
		
	}
	
	@Override
	public List<TweetResponseDto> getAllTweets() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TweetResponseDto createTweet(ContentCredentialsDto contentCredentialsDto) {
		
		Credentials reqCredentials = contentCredentialsDto.getCredentials();
		
		User user = verifyCredentials(reqCredentials);
		
		// TODO: Create method for searching content string for #hashtags and @mentions!
		Tweet tweet = new Tweet();
		tweet.setDeleted(false);
		tweet.setAuthor(user);
		tweet.setContent(contentCredentialsDto.getContent());
		tweet.setPosted(new Timestamp(System.currentTimeMillis()));
		
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
		
	}

	@Override
	public TweetResponseDto getTweet(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TweetResponseDto deleteTweet(Integer id, CredentialsRequestDto credentialsRequestDto) {
		verifyCredentials(credentialsMapper.requestDtoToEntity(credentialsRequestDto));
		
		Tweet tweet = getTweetFromDb(id);
		
		tweet.setDeleted(true);
		
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
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
