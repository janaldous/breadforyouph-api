package com.janaldous.breadforyouph.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "user_detail")
@Data
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotBlank(message = "Please enter first name!")
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank(message = "Please enter last name!")
	@Column(name = "last_name")
	private String lastName;
	
	private String email;
	
	@NotBlank(message = "Please enter contact number!")
	@Column(name = "contact_number")
	private String contactNumber;
	
	private String role;
	
}
