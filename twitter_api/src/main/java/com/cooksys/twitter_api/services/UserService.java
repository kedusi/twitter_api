package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.UserResponseDto;


public interface UserService {

	List<UserResponseDto> getAllUsers();

}
