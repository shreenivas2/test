package com.dealsdate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode.Exclude;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_table")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
	@SequenceGenerator(name="user_seq",sequenceName="user_seq", allocationSize=1)
	private Integer userId;
	@NotBlank(message="UserName is a required field")
	@Column(unique = true)
	private String userName;
	private String userType;
	@NotBlank(message="Password is a required field")
	private String userPassword;
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	@Exclude
	private Customer customer;
	
	public User(String userName, Integer userId, String userType, String userPassword) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.userType = userType;
		this.userPassword = userPassword;
	}

	public User(@NotBlank(message = "UserName is a required field") String userName,
			@NotBlank(message = "Password is a required field") String userPassword) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

}
