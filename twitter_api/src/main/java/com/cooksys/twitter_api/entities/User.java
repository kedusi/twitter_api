package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user_table")
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, updatable = false)
	private Timestamp joined;

	@Column(nullable = false)
	private boolean deleted;
	
	@Embedded
	private Profile profile;
	
	@Embedded
	private Credentials credentials;

}
