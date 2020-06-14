package com.janaldous.breadforyouph.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "order_total")
	private BigDecimal total;

	@ManyToOne
	private Address shipping;

	@ManyToOne
	private Address billing;

	@Enumerated(EnumType.STRING)
	private DeliveryType deliveryType;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@OneToMany(mappedBy = "orderDetail", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Column(name = "order_date")
	private Date orderDate;
	
	@OneToOne
	private OrderTracking tracking;
}
