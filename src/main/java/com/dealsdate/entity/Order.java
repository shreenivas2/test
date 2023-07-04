package com.dealsdate.entity;

import java.time.LocalDate;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "order_table")

public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="order_seq")
	@SequenceGenerator(name="order_seq",sequenceName="order_seq", allocationSize=1)
	private Integer orderId;
	
	// HAS - A relationship
	// A customer can have many orders. But one order can be ordered by only one customer.
	@ManyToOne(cascade = CascadeType.ALL) //Owning side
	@JoinColumn(name="cust_id")
	@JsonIgnore
	private Customer customer;
	
	// HAS - MANY relationship
	// An order can have many products and one product can belong to many orders.
	@ManyToMany(mappedBy = "orders")
//	@Cascade(CascadeType.SAVE_UPDATE)
//	@JsonIgnore
	private List<Product> products;
	
	private Integer totalQuantity;
	private Double totalPrice;
	private LocalDate orderDeliveryDate;
	private LocalDate orderDispatchDate;

}
