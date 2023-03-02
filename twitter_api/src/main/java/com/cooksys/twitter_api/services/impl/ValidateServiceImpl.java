package com.cooksys.twitter_api.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final UserRepository userRepository;
	

	@Override
	public boolean validateHashtagExists(String label) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateUsernameExists(String username) {
		return userRepository.findByCredentials_Username(username) != null;
	}

	@Override
	public boolean validateUsernameAvailability(String username) {
		if (userRepository.findByCredentials_Username(username) != null) {
			return false;
		}
		return true;
	}


}