package com.cooksys.twitter_api.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
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
	private final HashtagMapper hashtagMapper;
	private final UserMapper userMapper;
	
	// Retrieves a Tweet entity with given id from the database and throws an exception if not found
	private Tweet getTweetFromDb(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);

		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("Unable to find tweet with id: " + id);
		}

		return optionalTweet.get();
	}

	// Checks that user with given username exists and that passwords match
	private User verifyCredentials(Credentials credentials) {
		if (credentials.getUsername() == null || credentials.getPassword() == null) {
			throw new NotAuthorizedException("Must provide username and password");
		}

		User user = userRepository.findByCredentials_Username(credentials.getUsername());
		if (user == null) {
			throw new NotFoundException("User not found");
		}
		
		Credentials userCredentials = user.getCredentials();
		if (!credentials.getPassword().equals(userCredentials.getPassword())) {
			throw new NotAuthorizedException("Incorrect password provided");
		}

		return user;

	}

	// Searches a given string (using regex) and returns a list of any Hashtags
	// found (labels exclude # symbols)
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
			Hashtag hashtag = hashtagRepository.findByLabelIgnoreCase(match);
			long now = System.currentTimeMillis();

			// If hashtag doesn't exist in DB, create and add
			if (hashtag == null) {
				Hashtag hashtagToSave = new Hashtag();
				List<Tweet> tweets = new ArrayList<>();
				tweets.add(tweet);

				hashtagToSave.setLabel(match);
				hashtagToSave.setFirstUsed(new Timestamp(now));
				hashtagToSave.setLastUsed(new Timestamp(now));
				hashtagToSave.setTweets(tweets);

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
		// TODO: refactor
		// this works but I've seen a better way -KS
		return tweetMapper
				.entitiesToDtos(tweetRepository.findAllByDeletedFalse().stream().filter(tweet -> !tweet.isDeleted())
						.sorted((tweet1, tweet2) -> tweet2.getPosted().compareTo(tweet1.getPosted())).toList());
	}

	@Override

	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		Credentials credentials = tweetRequestDto.getCredentials();
		if (credentials == null) {
			throw new BadRequestException("Must provide credentials");
		}
		
		String content = tweetRequestDto.getContent();
		
		if (content == null || content.length() == 0) {
			throw new BadRequestException("Unable to create tweet without content");
		}

		User user = verifyCredentials(credentials);

		Tweet tweet = new Tweet();
		tweet.setDeleted(false);
		tweet.setAuthor(user);
		tweet.setContent(content);
		tweet.setPosted(new Timestamp(System.currentTimeMillis()));
		tweet.setHashtags(getHashtagsFromString(content, tweet));
		tweet.setMentions(getMentionsFromString(content, tweet));
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public TweetResponseDto getTweet(Long id) {
		Optional<Tweet> tweet = tweetRepository.findById(id);
		if(tweet.isEmpty()) {
			throw new NotFoundException("No tweet exists with that ID.");
		}
		return tweetMapper.entityToDto(tweet.get());
	}

	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsRequestDto) {
		verifyCredentials(credentialsMapper.requestDtoToEntity(credentialsRequestDto));

		Tweet tweet = getTweetFromDb(id);

		tweet.setDeleted(true);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
	}

	@Override
	public void likeTweet(Long id, CredentialsDto credentialsDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public TweetResponseDto createReply(Long id, TweetRequestDto tweetRequestDto) {
		Credentials credentials = tweetRequestDto.getCredentials();
		if (credentials == null) {
			throw new BadRequestException("Must provide credentials");
		}
		
		
		String content = tweetRequestDto.getContent();
		if (content == null || content.length() == 0) {
			throw new BadRequestException("Unable to create reply without content");
		}

		User user = verifyCredentials(credentials);
		Tweet tweetRepliedTo = getTweetFromDb(id);

		Tweet tweet = new Tweet();
		tweet.setDeleted(false);
		tweet.setAuthor(user);
		tweet.setContent(content);
		tweet.setPosted(new Timestamp(System.currentTimeMillis()));
		tweet.setInReplyTo(tweetRepliedTo);
		tweet.setHashtags(getHashtagsFromString(content, tweet));
		tweet.setMentions(getMentionsFromString(content, tweet));
		
		Tweet savedReply = tweetRepository.saveAndFlush(tweet);
		
		// Add new reply to replies list on tweetRepliedTo
		List<Tweet> replies = tweetRepliedTo.getReplies();
		replies.add(savedReply);
		tweetRepliedTo.setReplies(replies);
		tweetRepository.saveAndFlush(tweetRepliedTo);
		
		return tweetMapper.entityToDto(savedReply);
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
		// target = tweet w/ given id
		// before = the chain of replies leading to the target tweet
		// after = the chain of replies following the target tweet
		// All branches of replies must be flattened into single list
		// Deleted tweets should be excluded, but non-deleted replies to deleted tweets should be included
		
		Tweet target = getTweetFromDb(id);
		
		// Get reply chain before target
		List<Tweet> before = new ArrayList<>();
		if (target.getInReplyTo() != null) {
			Tweet current = target.getInReplyTo();
			while (current != null) {
				before.add(current);
				current = current.getInReplyTo();
			}
		}
		before.sort(Comparator.comparing(Tweet::getPosted));
		
		List<Tweet> replies = target.getReplies();
		for (Tweet reply : replies) {
			replies.addAll(reply.getReplies());
		}
		
		List<Tweet> after = new ArrayList<>();
		for (Tweet reply : replies) {
			if (reply.isDeleted() == false) {
				after.add(reply);
			}
		}
		after.sort(Comparator.comparing(Tweet::getPosted));
		
		ContextDto context = new ContextDto();
		context.setBefore(tweetMapper.entitiesToDtos(before));
		context.setTarget(tweetMapper.entityToDto(target));
		context.setAfter(tweetMapper.entitiesToDtos(after));
		return context;
	}

	@Override
	public List<TweetResponseDto> getReplies(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweetResponseDto> getReposts(Long id) {
		Tweet tweet = getTweetFromDb(id);
		List<Tweet> reposts = tweet.getReposts();
		List<Tweet> nonDeletedReposts = new ArrayList<>();
		
		for (Tweet repost : reposts) {
			Optional<Tweet> nonDeletedRepost = tweetRepository.findByIdAndDeletedFalse(repost.getId());
			if (nonDeletedRepost.isPresent()) {
				nonDeletedReposts.add(nonDeletedRepost.get());
			}
		}
		
		return tweetMapper.entitiesToDtos(nonDeletedReposts);
	}

	@Override
	public List<UserResponseDto> getMentions(Long id) {
		Tweet tweet = getTweetFromDb(id);
		List<User> users = tweet.getMentions();
		List<User> nonDeletedUsers = new ArrayList<>();
		
		for (User user : users) {
			Optional<User> nonDeletedUser = userRepository.findByIdAndDeletedFalse(user.getId());
			if (nonDeletedUser.isPresent()) {
				nonDeletedUsers.add(nonDeletedUser.get());
			}
		}
		
		return userMapper.entitiesToDtos(nonDeletedUsers);
	}
}
