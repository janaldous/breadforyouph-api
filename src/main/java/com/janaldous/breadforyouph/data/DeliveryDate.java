package com.janaldous.breadforyouph.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "delivery_date")
@Data
public class DeliveryDate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "delivery_date", unique = true)
	private Date date;
	
	/**
	 * Limit of orders on this date
	 */
	@Column(name = "order_limit")
	private int orderLimit = 6;
	
	@JsonIgnore
	@OneToMany(mappedBy = "deliveryDate", fetch = FetchType.EAGER)
	private List<OrderDetail> orders;
	
}
