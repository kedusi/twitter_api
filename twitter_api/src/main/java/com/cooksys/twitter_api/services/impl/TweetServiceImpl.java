package com.cooksys.twitter_api.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
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
<<<<<<< HEAD

	// Retrieves a Tweet entity with given id from the database and throws an
	// exception if not found
	private Tweet getTweetFromDb(Integer id) {
=======
	private final HashtagMapper hashtagMapper;
	private final UserMapper userMapper;
	
	// Retrieves a Tweet entity with given id from the database and throws an exception if not found
	private Tweet getTweetFromDb(Long id) {
>>>>>>> 22845b6f562496b79d9163dc2c49a76a44926e01
		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);

		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("Unable to find tweet with id: " + id);
		}

		return optionalTweet.get();
	}

	// Checks that user with given username exists and that passwords match
	private User verifyCredentials(Credentials credentials) {
		User user = userRepository.findByCredentials_Username(credentials.getUsername());

		if (user == null) {
			throw new NotFoundException("User not found");
		}

		Credentials userCredentials = user.getCredentials();

		if (credentials.getPassword() != userCredentials.getPassword()) {
			throw new NotAuthorizedException("Incorrect password provided");
		}

		return user;
<<<<<<< HEAD

=======
>>>>>>> 22845b6f562496b79d9163dc2c49a76a44926e01
	}

	// Searches a given string (using regex) and returns a list of any #hashtags
	// found
	private List<Hashtag> getHashtagsFromString(String string, Tweet tweet) {
		Pattern pattern = Pattern.compile("#(\\w+)");
		Matcher matcher = pattern.matcher(string);
		List<String> matches = new ArrayList<>();
		List<Hashtag> hashtags = new ArrayList<>();

		while (matcher.find()) {
			matches.add(matcher.group(1));
		}

		for (String match : matches) {
			// Get hashtag from DB
			Hashtag hashtag = hashtagRepository.findByLabel(match);
			long now = System.currentTimeMillis();

			// If hashtag doesn't exist in DB, create and add
			if (hashtag == null) {
				Hashtag hashtagToSave = new Hashtag();
				List<Tweet> tweets = new ArrayList<>();
				tweets.add(tweet);

				hashtagToSave.setLabel(match.toLowerCase());
				hashtagToSave.setFirstUsed(new Timestamp(now));
				hashtagToSave.setLastUsed(new Timestamp(now));
				hashtagToSave.setTweets(tweets);
<<<<<<< HEAD
				hashtagToSave.setTweets(new ArrayList<>());

=======
				
>>>>>>> 22845b6f562496b79d9163dc2c49a76a44926e01
				hashtags.add(hashtagRepository.saveAndFlush(hashtagToSave));
			} else {
				List<Tweet> tweets = hashtag.getTweets();
				tweets.add(tweet);
				hashtag.setTweets(tweets);
				hashtag.setLastUsed(new Timestamp(now));
				
				hashtags.add(hashtagRepository.saveAndFlush(hashtag));
			}
		}

		return hashtags;
	}

	// Searches a given string (using regex) and returns a list of any @mentions
	// (Users) found
	private List<User> getMentionsFromString(String string, Tweet tweet) {
		Pattern pattern = Pattern.compile("@(\\w+)");
		Matcher matcher = pattern.matcher(string);
		List<String> matches = new ArrayList<>();
		List<User> users = new ArrayList<>();

		while (matcher.find()) {
			matches.add(matcher.group(1));
		}

		for (String match : matches) {
			User user = userRepository.findByCredentials_Username(match);
			if (user == null) {
				continue;
			}

			List<Tweet> tweetsMentionedIn = user.getTweetMentions();
			tweetsMentionedIn.add(tweet);
			user.setTweetMentions(tweetsMentionedIn);
			users.add(userRepository.saveAndFlush(user));
		}

		return users;
	}

	@Override
	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper
				.entitiesToDtos(tweetRepository.findAllByDeletedFalse().stream().filter(tweet -> !tweet.isDeleted())
						.sorted((tweet1, tweet2) -> tweet2.getPosted().compareTo(tweet1.getPosted())).toList());
	}

	@Override
<<<<<<< HEAD
	public TweetResponseDto createTweet(ContentCredentialsDto contentCredentialsDto) {
		Credentials reqCredentials = contentCredentialsDto.getCredentials();
		String content = contentCredentialsDto.getContent();

=======
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		Credentials reqCredentials = tweetRequestDto.getCredentials();
		String content = tweetRequestDto.getContent();
		
>>>>>>> 22845b6f562496b79d9163dc2c49a76a44926e01
		if (content == null || content.length() == 0) {
			throw new BadRequestException("Unable to create tweet without content");
		}

		User user = verifyCredentials(reqCredentials);

		Tweet tweet = new Tweet();
		tweet.setDeleted(false);
		tweet.setAuthor(user);
		tweet.setContent(content);
		tweet.setPosted(new Timestamp(System.currentTimeMillis()));
		tweet.setHashtags(getHashtagsFromString(content, tweet));
		tweet.setMentions(getMentionsFromString(content, tweet));
<<<<<<< HEAD
		tweet.setLikes(new ArrayList<>());

=======
		
>>>>>>> 22845b6f562496b79d9163dc2c49a76a44926e01
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public TweetResponseDto getTweet(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsRequestDto) {
		verifyCredentials(credentialsMapper.requestDtoToEntity(credentialsRequestDto));

		Tweet tweet = getTweetFromDb(id);

		tweet.setDeleted(true);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public void likeTweet(Long id, UserRequestDto userRequestDto) {
		// TODO Auto-generated method stub

	}

	@Override
<<<<<<< HEAD
	public TweetResponseDto createReply(Integer id, ContentCredentialsDto contentCredentialsDto) {
		Credentials reqCredentials = contentCredentialsDto.getCredentials();
		String content = contentCredentialsDto.getContent();

=======
	public TweetResponseDto createReply(Long id, TweetRequestDto tweetRequestDto) {
		Credentials reqCredentials = tweetRequestDto.getCredentials();
		String content = tweetRequestDto.getContent();
		
>>>>>>> 22845b6f562496b79d9163dc2c49a76a44926e01
		if (content == null || content.length() == 0) {
			throw new BadRequestException("Unable to create reply without content");
		}

		User user = verifyCredentials(reqCredentials);
		Tweet tweetRepliedTo = getTweetFromDb(id);

		Tweet tweet = new Tweet();
		tweet.setDeleted(false);
		tweet.setAuthor(user);
		tweet.setContent(content);
		tweet.setPosted(new Timestamp(System.currentTimeMillis()));
		tweet.setInReplyTo(tweetRepliedTo);
		tweet.setHashtags(getHashtagsFromString(content, tweet));
		tweet.setMentions(getMentionsFromString(content, tweet));
<<<<<<< HEAD
		tweet.setLikes(new ArrayList<>());

=======
		
>>>>>>> 22845b6f562496b79d9163dc2c49a76a44926e01
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public TweetResponseDto createRepost(Long id, CredentialsDto credentialsRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HashtagResponseDto> getHashtags(Long id) {
		Tweet tweet = getTweetFromDb(id);
		return hashtagMapper.entitiesToResponseDtos(tweet.getHashtags());
	}

	@Override
	public List<UserResponseDto> getLikes(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ContextDto getContext(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweetResponseDto> getReplies(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweetResponseDto> getReposts(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResponseDto> getMentions(Long id) {
		Tweet tweet = getTweetFromDb(id);
		return userMapper.entitiesToDtos(tweet.getMentions());
	}
}
