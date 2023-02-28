package com.cooksys.twitter_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponseDto entityToDto(User user);
	
	List<UserResponseDto> entitiesToDtos(List<User> users);
	
	User dtoToEntity(UserRequestDto userRequestDto);

	
}
