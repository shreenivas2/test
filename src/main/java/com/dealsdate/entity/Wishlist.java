package com.dealsdate.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wishlist_table")
public class Wishlist {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="wishlist_seq")
	@SequenceGenerator(name="wishlist_seq",sequenceName="wishlist_seq", allocationSize=1)
	private Integer wishlistId;
	
	// HAS - A relationship
	// One wishlist can have only one customer.
	@OneToOne(mappedBy = "wishlist")
	@JoinColumn(name = "customer_id")
	@JsonIgnore
	private Customer customer;
	
	// HAS - A relationship
	// One wishlist can have many products.
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="wishList_products", joinColumns= {@JoinColumn(name="wishListId")}, inverseJoinColumns = {@JoinColumn(name="productId")})
	private List<Product> products;
	
	private Integer quantity;
	
	public Wishlist(Integer wishlistId, List<Product> products, Integer quantity) {
		super();
		this.wishlistId = wishlistId;
		this.products = products;
		this.quantity = quantity;
	}
	

}
