package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;

@Mapper(componentModel = "spring", uses = {HashtagMapper.class})
public interface HashtagMapper {

	HashtagResponseDto hashtagEntityToDto(Hashtag hashtag);
}
