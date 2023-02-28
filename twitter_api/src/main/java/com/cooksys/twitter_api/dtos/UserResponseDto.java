package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {

	private String username;
	
	private Profile profile;
	
	private Timestamp joined;
}
