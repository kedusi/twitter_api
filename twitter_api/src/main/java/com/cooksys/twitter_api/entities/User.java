package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	@CreationTimestamp
	private Timestamp joined;

	private boolean deleted = false;
	
	@Embedded
	@AttributeOverrides({
		  @AttributeOverride( name = "firstName", column = @Column(name = "profile_first_name")),
		  @AttributeOverride( name = "lastName", column = @Column(name = "profile_last_name")),
		  @AttributeOverride( name = "phone", column = @Column(name = "profile_phone")),
		  @AttributeOverride( name = "email", column = @Column(name = "profile_email"))
		})
	private Profile profile;
	
	@Embedded
	@AttributeOverrides({
		  @AttributeOverride( name = "username", column = @Column(name = "credentials_username")),
		  @AttributeOverride( name = "password", column = @Column(name = "credentials_password"))
	})
	private Credentials credentials;
	
	@ManyToMany
	@JoinTable(name = "followers_following", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "following_id"))
	List<User> followers;
	
	@ManyToMany
	@JoinTable(name = "followers_following", joinColumns = @JoinColumn(name = "following_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
	List<User> following;

}
