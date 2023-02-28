package com.cooksys.twitter_api.dtos;

import com.cooksys.twitter_api.entities.Credentials;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContentCredentialsDto {

	private String content;
	
	private Credentials credentials;
}
