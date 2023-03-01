package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	// TODO: Make hashtag label case-insensitive. Might need derived queries.
	private String label;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
	
	@ManyToMany(mappedBy = "hashtags")
	List<Tweet> tweets = new ArrayList<>();
	
}
