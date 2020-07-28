package com.janaldous.breadforyouph.resttests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.data.DeliveryDateRepository;
import com.janaldous.breadforyouph.data.OrderStatus;
import com.janaldous.breadforyouph.testutil.OrderDtoMockFactory;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.dto.OrderConfirmation;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

@Tag("E2E")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApiE2E {

	@Value("${local.server.port}")
	private int localServerPort;

	@Autowired
	private DeliveryDateRepository deliveryDateRepository;

	private boolean isDBInitialized = false;

	@BeforeEach
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = localServerPort;

		intializeDb();
	}

	private void intializeDb() {
		if (!isDBInitialized) {
			for (int i = 0; i < 3; i++) {
				DeliveryDate deliveryDate = new DeliveryDate();
				deliveryDate.setDate(TestUtils.convertLocalDateToDate(LocalDate.now().plusDays(i)));
				deliveryDate.setOrderLimit(2);
				deliveryDateRepository.save(deliveryDate);
			}
			isDBInitialized = true;
		}
	}
	
	@Test
	public void testGetOrdersWithoutAuthentication() {
		get("/admin/order")
		.then().assertThat().statusCode(HttpStatus.UNAUTHORIZED.value());
	}

	@Test
	public void testGetOrders() {
		given().auth().preemptive().basic("admin", "admin")
		.when().get("/admin/order")
		.then().assertThat().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void testCreateAnOrderThenGetOrderDetail() {
		// get delivery date
		JsonPath deliveryListResult = get("/api/delivery?page=0&size=3").then().statusCode(HttpStatus.OK.value()).extract()
				.jsonPath();

		// should match regex pattern, since Safari cannot recognize
		// yyyy-mm-ddTHH:mm:ss.SSSZ
		String deliveryDateStr = deliveryListResult.getString("[0].date");
		assertTrue(
				Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}$").matcher(deliveryDateStr).find());

		DeliveryDate deliveryDate = deliveryListResult.getObject("[0]", DeliveryDate.class);

		// create order
		OrderDto orderDto = OrderDtoMockFactory.factory();
		orderDto.setDeliveryDateId(deliveryDate.getId());

		OrderConfirmation result = given().contentType(MediaType.APPLICATION_JSON_VALUE.toString()).body(orderDto)
				.when().post("/api/order").then().statusCode(HttpStatus.CREATED.value())
				.body("orderStatus", is(OrderStatus.REGISTERED.toString())).extract().as(OrderConfirmation.class);

		// should be able to get order detail
		given().auth().basic("admin", "admin")
		.when().get("/admin/order/" + result.getOrderNumber())
		.then().assertThat().statusCode(HttpStatus.OK.value());
	}

}
