package com.janaldous.breadforyouph.webfacade;

import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.breadforyouph.data.DeliveryType;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.webfacade.dto.AddressDto;
import com.janaldous.breadforyouph.webfacade.dto.OrderDto;
import com.janaldous.breadforyouph.webfacade.dto.UserDto;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { OrderController.class, ExceptionTranslator.class })
@WebMvcTest
public class OrderControllerIT {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OrderControllerIT.class);

	@MockBean
	private OrderService orderService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void testOrderInvalidUser() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.setUser(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.validation.user", containsString("must not be null")))
				.andReturn();
	}

	@Test
	public void testOrderInvalidUserContactNumber() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.getUser().setContactNumber(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.validation['user.contactNumber']", containsString("must not be null")))
				.andReturn();

	}

	@Test
	public void testOrderInvalidAddress() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.setAddress(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.validation.address", containsString("must not be null")))
				.andReturn();
	}

	@Test
	public void testOrderInvalidAddressLine1() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.getAddress().setLine1(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.validation['address.line1']", containsString("must not be null")))
				.andReturn();

	}

	@Test
	public void testValidOrder() throws Exception {

		OrderDto orderMock = getMockOrder();

		mockMvc.perform(MockMvcRequestBuilders.post("/order").content(mapper.writeValueAsString(orderMock))
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
		UserDto user = new UserDto();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setContactNumber("1234567890");
		orderMock.setUser(user);
		orderMock.setPaymentType(PaymentType.CASH);
		orderMock.setQuantity(1l);
		return orderMock;
	}
}
