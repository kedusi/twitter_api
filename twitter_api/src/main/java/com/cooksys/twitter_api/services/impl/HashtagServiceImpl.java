package com.cooksys.twitter_api.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;

	@Override
	public List<HashtagResponseDto> getAllTags() {
		return hashtagMapper.entitiesToResponseDtos(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> getTweetsByTag(String label) {
//		return tweetRepository.findAllByDeletedFalseAndHashtags_Label(label);
//		List<Tweet> tweets = tweetRepository.findAllByDeletedFalse();
//		List<Tweet> tweetsByTag = new ArrayList<>();
//		// TODO: get tweets from hashtag
//		for (Tweet tweet : tweets) {
//			for(Hashtag tag : tweet.getHashtags()) {
//				if(tag.getLabel() == label) {
//					tweetsByTag.add(tweet);
//				}
//			}
//		}
//		tweetsByTag.sort(Comparator.comparing(Tweet::getPosted).reversed());
//		return tweetMapper.entitiesToDtos(tweetsByTag);
		Hashtag tag = hashtagRepository.findByLabelIgnoreCase(label);
		List<Tweet> tweetsByTag = tag.getTweets();
		tweetsByTag.sort(Comparator.comparing(Tweet::getPosted).reversed());
		return tweetMapper.entitiesToDtos(tweetsByTag);
	}

}
