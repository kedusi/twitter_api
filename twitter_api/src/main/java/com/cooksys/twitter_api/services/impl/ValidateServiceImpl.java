package com.cooksys.twitter_api.services.impl;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

	@Override
	public boolean validateHashtagExists(String label) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateUsernameExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateUsernameAvailability(String username) {
		// TODO Auto-generated method stub
		return false;
	}


}