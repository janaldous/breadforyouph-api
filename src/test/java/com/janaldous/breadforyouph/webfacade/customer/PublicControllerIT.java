package com.janaldous.breadforyouph.webfacade.customer;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.breadforyouph.config.BasicSecurityConfiguration;
import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.service.DeliveryDateService;
import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.ExceptionTranslator;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

@WebMvcTest(PublicController.class)
@Import({ ExceptionTranslator.class, BasicSecurityConfiguration.class })
public class PublicControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeliveryDateService deliveryDateService;

	@MockBean
	private OrderService orderService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testDeliveryDateWithResult() throws Exception {
		List<DeliveryDate> availableDates = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			DeliveryDate date = new DeliveryDate();
			date.setDate(new Date(TestUtils.getTimeAsMilis(i)));
			date.setOrderLimit(6);
			availableDates.add(date);
		}
		Page<DeliveryDate> page = new PageImpl<>(availableDates);
		Mockito.when(deliveryDateService.getDeliveryDates(0, 5)).thenReturn(page);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery?page=0&size=5").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5))).andReturn();
	}

	@Test
	public void testDeliveryDateWihNoResult() throws Exception {
		List<DeliveryDate> availableDates = new ArrayList<>();
		Page<DeliveryDate> page = new PageImpl<>(availableDates);
		Mockito.when(deliveryDateService.getDeliveryDates(0, 5)).thenReturn(page);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery?page=0&size=5").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0))).andReturn();
	}

	@Test
	public void testDeliveryDateWith404() throws Exception {
		List<DeliveryDate> availableDates = new ArrayList<>();
		Page<DeliveryDate> page = new PageImpl<>(availableDates);
		Mockito.when(deliveryDateService.getDeliveryDates(1, 5)).thenReturn(page);

		assertEquals(1, page.getTotalPages());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery?page=1&size=5").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	@Test
	public void testOrderInvalidUser() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.setUser(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.validation.user", containsString("must not be null")))
				.andReturn();
	}

	@Test
	public void testOrderNullUserContactNumber() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.getUser().setContactNumber(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.validation['user.contactNumber']", containsString("Invalid mobile number")))
				.andReturn();

	}

	@Test
	public void testOrderInvalidUserContactNumber() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.getUser().setContactNumber("011212121");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.validation['user.contactNumber']", containsString("Invalid mobile number")))
				.andReturn();

	}

	@Test
	public void testOrderInvalidAddress() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.setAddress(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.validation.address", containsString("must not be null")))
				.andReturn();
	}

	@Test
	public void testOrderInvalidOrderDate() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.setDeliveryDateId(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.validation['deliveryDateId']", containsString("must not be null")))
				.andReturn();

	}

	@Test
	public void testOrderInvalidAddressLine1() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.getAddress().setLine1(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.validation['orderDto']",
						containsString("Address fields should not be null when Delivery is chosen")))
				.andReturn();

	}

	@Test
	public void testValidOrder() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.setDeliveryDateId(1l);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
	}

	private OrderDto getMockOrder() {
		OrderDto orderMock = new OrderDto();
		AddressDto address = new AddressDto();
		address.setLine1("Main Street");
		address.setVillage("Mickey Mouse Clubhouse");
		address.setCity("Sta Rosa");
		address.setProvince("Murica");
		address.setPostcode("4026");
		orderMock.setAddress(address);
		orderMock.setDeliveryType(DeliveryType.DELIVER);
		orderMock.setDeliveryDateId(null);
		UserDto user = new UserDto();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setContactNumber("09123456789");
		orderMock.setUser(user);
		orderMock.setPaymentType(PaymentType.CASH);
		orderMock.setQuantity(1l);
		return orderMock;
	}
}
