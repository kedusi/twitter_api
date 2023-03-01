package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.User;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
	
	Credentials requestDtoToEntity(CredentialsRequestDto credentialsRequestDto);
	
}
