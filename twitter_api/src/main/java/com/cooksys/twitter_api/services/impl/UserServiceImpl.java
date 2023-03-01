package com.cooksys.twitter_api.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
import com.cooksys.twitter_api.mappers.ProfileMapper;
//import com.cooksys.twitter_api.mappers.ProfileMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.ProfileDto;
//import com.cooksys.twitter_api.dtos.ProfileRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;
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
	private final ProfileMapper profileMapper;

	// Helper method to verify and return the user from the database
	private User getUserFromDatabase(String username) {
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
		/*if (userRequestDto.getCredentialsRequestDto() == null) {
			throw new BadRequestException("Must provide your credentials.");
		}
		if (userRequestDto.getProfileRequestDto() == null) {
			throw new BadRequestException("Must provide your profile.");
		}
		
		if (userRequestDto.getCredentialsRequestDto().getUsername() == null) {
			throw new BadRequestException("Must provide your username.");
		}
		if (userRequestDto.getCredentialsRequestDto().getPassword() == null
				|| userRequestDto.getCredentialsRequestDto().getPassword().length() == 0) {
			throw new BadRequestException("Must provide your password.");
		}
		
		if (userRequestDto.getProfileRequestDto().getEmail() == null) {
			throw new BadRequestException("Must provide your email.");
		}
		*/
	}
	private void validateCredentials(CredentialsDto credentialsRequestDto) {
		if (credentialsRequestDto.getUsername() == null) {
			throw new BadRequestException("Must provide your username.");
		}
		if (credentialsRequestDto.getPassword() == null) {
			throw new BadRequestException("Must provide your password.");
		}
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}
	
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {

		validateCredentials(userRequestDto.getCredentials());
		
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
		User user = getUserFromDatabase(username);
		return userMapper.entityToDto(user);
	}

	@Override
	public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);
		User userToUpdate = getUserFromDatabase(username);
		Profile profile = profileMapper.requestDtoToEntity(userRequestDto.getProfile());
		
		if (!userToUpdate.getCredentials().getPassword().equals(userRequestDto.getCredentials().getPassword())) {
			throw new NotAuthorizedException("Password is incorrect for username: " + username);
		}
		userToUpdate.setProfile(profile);
		
		return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
	}

	@Override
	public UserResponseDto deleteUser(String username, CredentialsDto credentialsRequestDto) {
		User userToDelete = getUserFromDatabase(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);
		
		if (!userToDelete.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect for username: " + username);
		}
		userToDelete.setDeleted(true);
		return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
	}

	@Override
	public void followUser(String username, CredentialsDto credentialsRequestDto) {
		validateCredentials(credentialsRequestDto);
		User userToFollow = getUserFromDatabase(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);
		
		User currentUser = getUserFromDatabase(credentials.getUsername());
		
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
		
		userMapper.entityToDto(userRepository.saveAndFlush(currentUser));
		userMapper.entityToDto(userRepository.saveAndFlush(userToFollow));
      
	}

	@Override
	public void unfollowUser(String username, CredentialsDto credentialsRequestDto) {
		validateCredentials(credentialsRequestDto);
		User userToUnfollow = getUserFromDatabase(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);
		
		
		User currentUser = getUserFromDatabase(credentials.getUsername());
		
		if (!currentUser.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect for username: " + username);
		}
		
		if (!userToUnfollow.getFollowers().contains(currentUser)) {
			throw new BadRequestException("You do not follow this user.");
		}
		
		userToUnfollow.getFollowers().remove(currentUser);
		
		userMapper.entityToDto(userRepository.saveAndFlush(currentUser));
		userMapper.entityToDto(userRepository.saveAndFlush(userToUnfollow));
     
	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweetResponseDto> getTweets(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TweetResponseDto> getMentions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {
		User user = getUserFromDatabase(username);
		return userMapper.entitiesToDtos(user.getFollowers());
	}

	@Override
	public List<UserResponseDto> getFollowing(String username) {
		User user = getUserFromDatabase(username);
		return userMapper.entitiesToDtos(user.getFollowing());
	}

}
