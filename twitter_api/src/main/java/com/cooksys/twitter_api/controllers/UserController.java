package com.cooksys.twitter_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.services.UserService;
import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}
	
	@GetMapping("/{username}")
	public UserResponseDto getOneUser(@PathVariable String username) {
		return userService.getOneUser(username);
	}
	
	@PatchMapping("/{username}")
	public UserResponseDto updateUserProfile(@PathVariable String username, UserRequestDto userRequestDto) {
		return userService.updateUserProfile(username, userRequestDto);
	}
	
	@DeleteMapping("/{username}")
	public UserResponseDto deleteUser(@PathVariable String username, CredentialsRequestDto credentialsRequestDto) {
		return userService.deleteUser(username, credentialsRequestDto);
	}
	
	@PostMapping("/{username}/follow")
	public Object followUser(@PathVariable String username) {
		return userService.followUser(username);
	}
	@PostMapping("/{username}/unfollow")
	public Object unfollowUser(@PathVariable String username) {
		return userService.unfollowUser(username);
	}
	

}
