package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.ProfileRequestDto;
import com.cooksys.twitter_api.entities.Profile;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface ProfileMapper {
	
	Profile requestDtoToEntity(ProfileRequestDto profileRequestDto);

}
