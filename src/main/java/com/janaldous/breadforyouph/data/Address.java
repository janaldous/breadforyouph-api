package com.janaldous.breadforyouph.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "address_line_one")
	private String addressLineOne;
	
	@Column(name = "address_line_two")
	private String addressLineTwo;
	
	private String city;
	
	private String province;
	
	private String country;
	
	@Column(name ="postal_code")
	private String postalCode;
	
	@Column(name ="special_instructions")
	private String specialInstructions;
	
	@Column(name="is_shipping")
	private boolean shipping;
	
	@Column(name="is_billing")
	private boolean billing;

}