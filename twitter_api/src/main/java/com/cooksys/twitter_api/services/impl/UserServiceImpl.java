package com.cooksys.twitter_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
//import com.cooksys.twitter_api.mappers.ProfileMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
//import com.cooksys.twitter_api.dtos.ProfileRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Credentials;
//import com.cooksys.twitter_api.entities.Credentials;
//import com.cooksys.twitter_api.entities.Profile;
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
	// private final ProfileMapper profileMapper;

	private User getUser(String username) {
		User user = userRepository.findByCredentials_Username(username);
		if (user == null) {
			throw new NotFoundException("No user with username: " + username);
		}
		if (user.isDeleted()) {
			throw new NotFoundException("User with username: " + username + " has been deleted.");
		}
		return user;
	}

	private void validateUserRequest(UserRequestDto userRequestDto) {
		if (userRequestDto.getCredentials() == null) {
			throw new BadRequestException("Must have credentials to create a new user.");
		}
		if (userRequestDto.getProfile() == null) {
			throw new BadRequestException("Must have profile to create a new user.");
		}
		if (userRequestDto.getCredentials().getUsername() == null) {
			throw new BadRequestException("Must have a username to create a new user.");
		}
		if (userRequestDto.getCredentials().getPassword() == null
				|| userRequestDto.getCredentials().getPassword().length() == 0) {
			throw new BadRequestException("Must have a password to create a new user.");
		}
		if (userRequestDto.getProfile().getEmail() == null) {
			throw new BadRequestException("Must have an email to create a new user.");
		}
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);
		
		// Check if username already exists in database
		User user = userRepository.findByCredentials_Username(userRequestDto.getCredentials().getUsername());
		if (user != null) {
			throw new BadRequestException("Username is already in use.");
		}
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
		validateUserRequest(userRequestDto);
		User userToUpdate = getUser(username);
		
		if (!userToUpdate.getCredentials().getPassword().equals(userRequestDto.getCredentials().getPassword())) {
			throw new NotAuthorizedException("Password is incorrect for username: " + username);
		}
		userToUpdate.setProfile(userRequestDto.getProfile());
		
		return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
	}

	@Override
	public UserResponseDto deleteUser(String username, CredentialsRequestDto credentialsRequestDto) {
		User userToDelete = getUser(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);
		
		if (!userToDelete.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect for username: " + username);
		}
		userToDelete.setDeleted(true);
		return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
	}

	@Override
	public Object followUser(String username, CredentialsRequestDto credentialsRequestDto) {
		
		User userToFollow = getUser(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);
		
		User currentUser = getUser(credentials.getUsername());
		
		if (!currentUser.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect for username: " + username);
		}
		
		// Ensures there is not already a following relationship between the 2 users
		for (User u : currentUser.getFollowing()) {
			if (u.getCredentials().getUsername().equalsIgnoreCase(userToFollow.getCredentials().getUsername())) {
				throw new BadRequestException("You already follow this user.");
			}
		}
		
		userToFollow.getFollowers().add(currentUser);
		currentUser.getFollowing().add(userToFollow);
		
		userMapper.entityToDto(userRepository.saveAndFlush(currentUser));
		userMapper.entityToDto(userRepository.saveAndFlush(userToFollow));
       
		return null;
	}

	@Override
	public Object unfollowUser(String username, CredentialsRequestDto credentialsRequestDto) {
		User userToUnfollow = getUser(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);
		
		User currentUser = getUser(credentials.getUsername());
		
		if (!currentUser.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect for username: " + username);
		}
		
		if (!userToUnfollow.getFollowers().contains(currentUser)) {
			throw new BadRequestException("You do not follow this user.");
		}
		
		userToUnfollow.getFollowers().remove(currentUser);
		currentUser.getFollowing().remove(userToUnfollow);
		
		userMapper.entityToDto(userRepository.saveAndFlush(currentUser));
		userMapper.entityToDto(userRepository.saveAndFlush(userToUnfollow));
       
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
