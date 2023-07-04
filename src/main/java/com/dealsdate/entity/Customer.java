package com.dealsdate.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customer_table")
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cust_seq")
	@SequenceGenerator(name="cust_seq",sequenceName="cust_seq", allocationSize=1)
	private Integer customerId;
	
	@NotBlank(message="Name is a required field")
	private String customerName;
	
	@NotBlank(message="Mobile No is a required field")
	@Pattern(regexp="\\d{10}")
	@Column(unique = true)
	private String mobileNo;
	
	@NotBlank(message="Email is a required field")
	@Email
	private String email;
	
	// HAS - A relationship
	// One customer can have only one Address.
	@OneToOne(cascade=CascadeType.ALL) 
	@JoinColumn(name="address_id")
	private Address address;
	
	//IS - A relationship
	// Customer is a sub-class of User.
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_Id")
	private User user;
	
	// HAS - A relationship
	// Customer has one Cart
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cart_id")
	private Cart cart;
	
	// HAS - A relationship
	// Customer has one wishlist
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="wishlist_id")
	private Wishlist wishlist;
	
	// One Customer can have many Orders
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;

}
