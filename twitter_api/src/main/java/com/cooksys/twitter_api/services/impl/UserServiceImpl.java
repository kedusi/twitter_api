package com.cooksys.twitter_api.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
import com.cooksys.twitter_api.mappers.ProfileMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
import com.cooksys.twitter_api.dtos.ProfileRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;
	private final ProfileMapper profileMapper;

	private User getUser(String username) {
		User user = userRepository.findByCredentials_Username(username);
		if (user == null) {
			throw new NotFoundException("No user with username: " + username);
		}
		return user;
	}

	private void validateUserRequest(UserRequestDto userRequestDto) {
		if (userRequestDto.getCredentials().getUsername() == null) {
			throw new BadRequestException("Must have a username to create a new user.");
		}
		if (userRequestDto.getCredentials().getPassword() == null
				|| userRequestDto.getCredentials().getPassword().length() == 0) {
			throw new BadRequestException("Must have a password to create a new user.");
		}

	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		// Check if username already exists in database
		User user = userRepository.findByCredentials_Username(userRequestDto.getCredentials().getUsername());
		if (user != null) {
			throw new BadRequestException("Username is already in use.");
		}

		validateUserRequest(userRequestDto);
		User userToSave = userMapper.requestDtoToEntity(userRequestDto);
		userToSave.setDeleted(false);

		return userMapper.entityToDto(userRepository.saveAndFlush(userToSave));
	}

	@Override
	public UserResponseDto getOneUser(String username) {
		User user = getUser(username);
		return userMapper.entityToDto(user);

	}

	@Override
	public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto deleteUser(String username, CredentialsRequestDto credentialsRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object followUser(String username, CredentialsRequestDto credentialsRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object unfollowUser(String username, CredentialsRequestDto credentialsRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto getFeed(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto getTweets(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto getMentions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto getFollowers(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto getFollowing(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
