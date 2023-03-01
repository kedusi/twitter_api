package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.User;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface CredentialsMapper {
	
	Credentials requestDtoToEntity(CredentialsDto credentialsRequestDto);
	
}
