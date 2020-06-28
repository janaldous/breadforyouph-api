package com.janaldous.breadforyouph.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "delivery_date")
@Data
public class DeliveryDate {

	@Id
	@GeneratedValue
	private Long id;
	
	private Date date;
	
	private int limit;
	
}
