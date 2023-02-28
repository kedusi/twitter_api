package com.cooksys.twitter_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private User getUser(Long id) {
		Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("No user with id: " + id);
		}
		return optionalUser.get();
	}
	
	private void validateUserRequest(UserRequestDto userRequestDto) {
		if (userRequestDto.getCredentialsRequestDto().getUsername() == null) {
			throw new BadRequestException("Must have a username to create a user.");
		}
		if (userRequestDto.getCredentialsRequestDto().getPassword() == null) {
			throw new BadRequestException("Must have a password to create a user.");
		}
		if (userRequestDto.getProfileRequestDto().getEmail() == null) {
			throw new BadRequestException("Must have an email address to create a user.");
		}
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);
		return null;
	}

	@Override
	public UserResponseDto getOneUser(String username) {
		// TODO Auto-generated method stub
		return null;
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
