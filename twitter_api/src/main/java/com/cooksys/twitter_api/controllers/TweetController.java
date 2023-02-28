package com.cooksys.twitter_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.dtos.ContentCredentialsDto;
import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.CredentialsRequestDto;
import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
	
	private final TweetService tweetService;
	
	@GetMapping
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TweetResponseDto createTweet(@PathVariable Integer id, @RequestBody ContentCredentialsDto contentCredentialsDto) {
		return tweetService.createTweet(id, contentCredentialsDto);
	}
	
	@GetMapping("/{id}")
	public TweetResponseDto getTweet(@PathVariable Integer id) {
		return tweetService.getTweet(id);
	}
	
	@DeleteMapping("/{id}")
	public TweetResponseDto deleteTweet(@PathVariable Integer id, @RequestBody CredentialsRequestDto credentialsRequestDto) {
		return tweetService.deleteTweet(id, credentialsRequestDto);
	}
	

	@PostMapping("/{id}/like")
	public void likeTweet(@PathVariable Integer id, @RequestBody CredentialsRequestDto credentialsRequestDto) {
		
	}
	
	@PostMapping("/{id}/reply")
	public TweetResponseDto createReply(@PathVariable Integer id, @RequestBody ContentCredentialsDto contentCredentialsDto) {
		return tweetService.createReply(id, contentCredentialsDto);
	}
	
	@PostMapping("/{id}/repost")
	public TweetResponseDto createRepost(@PathVariable Integer id, @RequestBody CredentialsRequestDto credentialsRequestDto) {
		return tweetService.createRepost(id, credentialsRequestDto);
	}
	
	@GetMapping("/{id}/tags")
	public List<HashtagResponseDto> getHashtags(@PathVariable Integer id) {
		return tweetService.getHashtags(id);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getLikes(@PathVariable Integer id) {
		return tweetService.getLikes(id);
	}
	
	@GetMapping("/{id}/context")
	public ContextDto getContext(@PathVariable Integer id) {
		return tweetService.getContext(id);
	}
	
	@GetMapping("{id}/replies")
	public List<TweetResponseDto> getReplies(@PathVariable Integer id) {
		return tweetService.getReplies(id);
	}
	
	@GetMapping("{id}/reposts")
	public List<TweetResponseDto> getReposts(@PathVariable Integer id) {
		return tweetService.getReposts(id);
	}
	
	@GetMapping("{id}/mentions")
	public List<UserResponseDto> getMentions(@PathVariable Integer id) {
		return tweetService.getMentions(id);
	}
	
}
