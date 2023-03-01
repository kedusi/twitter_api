package com.cooksys.twitter_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;

	@Override
	public List<HashtagResponseDto> GetAllHashtags() {
		return hashtagMapper.entitiesToResponseDtos(hashtagRepository.findAll());
	}

	@Override
	public List<Tweet> GetTweetsByTag(String label) {
		// TODO GetTweetsByTag for HTServImpl
		return null;
	}

}
