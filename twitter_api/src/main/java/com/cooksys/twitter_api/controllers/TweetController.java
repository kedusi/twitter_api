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

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
	
//	private final TweetService tweetService;
	
	// TODO: Uncomment after creating TweetService
//	@GetMapping
//	public List<TweetResponseDto> getAllTweets() {
//		return tweetService.getAllTweets();
//	}
	
	// TODO: Uncomment and complete after creating TweetService and appropriate DTO to take in both Tweet content and Credentials from client
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public TweetResponseDto createTweet(@PathVariable Integer id, @RequestBody ?) {
//		return tweetService.createTweet(id, ?);
//	}
	
	// TODO: Uncomment after creating TweetService
//	@GetMapping("/{id}")
//	public TweetResponseDto getTweet(@PathVariable Integer id) {
//		return tweetService.getTweet(id);
//	}
	
	// TODO: Uncomment and complete after creating TweetService and appropriate DTO to take in both Tweet content and Credentials from client
//	@DeleteMapping("/{id}")
//	public TweetResponseDto deleteTweet(@PathVariable Integer id, @RequestBody ?) {
//		return tweetService.deleteTweet(id, ?);
//	}
	
	// TODO: Uncomment after creating TweetService and CredentialsDto 
//	@PostMapping("/{id}/like")
//	public void likeTweet(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto) {
//		return tweetService.likeTweet(id, credentialsDto);
//	}
	
	// TODO: Uncomment and complete after creating TweetService and appropriate DTO to take in both Tweet content and Credentials from client
//	@PostMapping("/{id}/reply")
//	public TweetResponseDto createReply(@PathVariable Integer id, @RequestBody ?) {
//		return tweetService.createReply(id, ?);
//	}
	
	// TODO: Uncomment after creating TweetService and CredentialsDto 
//	@PostMapping("/{id}/repost")
//	public TweetResponseDto createRepost(@PathVariable Integer id, @RequestBody CredentialsDto credentialsDto) {
//		return tweetService.createRepost(id, credentialsDto);
//	}
	
	// TODO: Uncomment after creating TweetService and HashtagDto
//	@GetMapping("/{id}/tags")
//	public List<HashtagDto> getHashtags(@PathVariable Integer id) {
//		return tweetService.getHashtags(id);
//	}
	
	// TODO: Uncomment after creating TweetService
//	@GetMapping("/{id}/likes")
//	public List<UserResponseDto> getLikes(@PathVariable Integer id) {
//		return tweetService.getLikes(id);
//	}
	
	// TODO: Uncomment after creating TweetService and ContextDto
//	@GetMapping("/{id}/context")
//	public ContextDto getContext(@PathVariable Integer id) {
//		return tweetService.getContext(id);
//	}
	
	// TODO: Uncomment after creating TweetService
//	@GetMapping("{id}/replies")
//	public List<TweetResponseDto> getReplies(@PathVariable Integer id) {
//		return tweetService.getReplies(id);
//	}
	
	// TODO: Uncomment after creating TweetService
//	@GetMapping("{id}/reposts")
//	public List<TweetResponseDto> getReposts(@PathVariable Integer id) {
//		return tweetService.getReposts(id);
//	}
	
	// TODO: Uncomment after creating TweetService
//	@GetMapping("{id}/mentions")
//	public List<UserResponseDto> getMentions(@PathVariable Integer id) {
//		return tweetService.getMentions(id);
//	}
	
}
