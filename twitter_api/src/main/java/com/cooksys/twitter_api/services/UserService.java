package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;


public interface UserService {

	List<UserResponseDto> getAllUsers();

	UserResponseDto createUser(UserRequestDto userRequestDto);

	UserResponseDto getOneUser(String username);

	UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto);

	UserResponseDto deleteUser(String username, CredentialsRequestDto credentialsRequestDto);

	Object followUser(String username, CredentialsRequestDto credentialsRequestDto);

	Object unfollowUser(String username, CredentialsRequestDto credentialsRequestDto);

}
