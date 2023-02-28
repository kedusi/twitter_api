package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "user_table_id", nullable = false)
	private User author;
	
	@Column(nullable = false)
	private Timestamp posted;
	
	@Column(nullable = false)
	private boolean deleted;
	
	private String content;
	
	private Tweet inReplyTo;
	
	private Tweet repostOf;
	
	@ManyToMany(mappedBy="hashtag_id")
	List<Hashtag> hashtags;
	
}
