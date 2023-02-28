package com.cooksys.twitter_api.dtos;

import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {

	private Credentials credentials;
	
	private Profile profile;
	
}
