package com.janaldous.breadforyouph.resttests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.janaldous.breadforyouph.service.OrderDtoMockFactory;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

import io.restassured.RestAssured;

@Tag("IntegrationTest")
public class OrderApiIT {

	@BeforeEach
	public void setup() {
	    RestAssured.baseURI = "https://breadforyouph-api.herokuapp.com";
	    RestAssured.port = 443;
	}
	
	@Test
	public void testGetOrders() {
		get("/order").then().statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testGetAnOrder() {
		OrderDto orderDto = OrderDtoMockFactory.factory();
		orderDto.setDeliveryDate(TestUtils.convertLocalDateToDate(LocalDate.now()));
		
		OrderConfirmation result = given().contentType(MediaType.APPLICATION_JSON_VALUE.toString()).body(orderDto)
			.when().post("/order")
			.then().statusCode(HttpStatus.CREATED.value())
			.extract().as(OrderConfirmation.class);
		
		get("/order/" + result.getOrderNumber()).then().statusCode(HttpStatus.OK.value());
	}
	
}
