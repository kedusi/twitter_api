package com.cooksys.twitter_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.services.UserService;
import com.cooksys.twitter_api.dtos.UserResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUsers();
	}
	

}
