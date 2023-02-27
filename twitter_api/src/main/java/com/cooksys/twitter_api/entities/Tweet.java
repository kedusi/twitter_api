package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	// TODO: Uncomment below once User entity is created, then delete this comment :)
//	@Column(nullable = false)
//	private User author;
	
	@Column(nullable = false)
	private Timestamp posted;
	
	@Column(nullable = false)
	private boolean deleted;
	
	private String content;
	
	private Tweet inReplyTo;
	
	private Tweet repostOf;
	
}
