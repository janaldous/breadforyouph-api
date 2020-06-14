package com.janaldous.breadforyouph.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Table(name = "order_item")
@Data
public class OrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Product product;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderDetail orderDetail;

	@Column (name = "buying_price")
	private BigDecimal buyingPrice;
	
	@Column (name = "product_count")
	private int productCount;
	
	private BigDecimal total;
}
