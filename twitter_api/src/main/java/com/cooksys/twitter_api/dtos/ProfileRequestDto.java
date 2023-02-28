package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileRequestDto {
	
	private String email;
	
	private String firstName;
	
	private String lastName;
	
	private String phone;
}
