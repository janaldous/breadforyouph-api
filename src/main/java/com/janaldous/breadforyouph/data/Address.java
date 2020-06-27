package com.janaldous.breadforyouph.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message = "Please enter address line one!")
	@Column(name = "address_line_one")
	private String addressLineOne;
	
	@NotBlank(message = "Please enter address line two!")	
	@Column(name = "address_line_two")
	private String addressLineTwo;
	
	@NotBlank(message = "Please enter City!")	
	private String city;
	
	@NotBlank(message = "Please enter Province!")	
	private String province;
	
	@NotBlank(message = "Please enter country!")	
	private String country;
	
	@Column(name ="postal_code")
	@NotBlank(message = "Please enter Postal Code!")	
	private String postalCode;
	
	@Column(name ="special_instructions")
	private String specialInstructions;
	
	@Column(name="is_shipping")
	private boolean shipping;
	
	@Column(name="is_billing")
	private boolean billing;

}