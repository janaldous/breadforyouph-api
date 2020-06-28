package com.janaldous.breadforyouph.webfacade;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import com.janaldous.breadforyouph.data.OrderDetail;
import com.janaldous.breadforyouph.data.OrderStatus;
import com.janaldous.breadforyouph.data.PaymentType;
import com.janaldous.breadforyouph.service.OrderService;
import com.janaldous.breadforyouph.service.ResourceNotFoundException;
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
	public void testOrderNullUserContactNumber() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.getUser().setContactNumber(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.validation['user.contactNumber']", containsString("Invalid mobile number")))
				.andReturn();

	}
	
	@Test
	public void testOrderInvalidUserContactNumber() throws Exception {

		OrderDto orderMock = getMockOrder();
		orderMock.getUser().setContactNumber("011212121");

		mockMvc.perform(MockMvcRequestBuilders.post("/order").content(mapper.writeValueAsString(orderMock))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(MockMvcResultMatchers
						.jsonPath("$.validation['user.contactNumber']", containsString("Invalid mobile number")))
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

	@Test
	public void testGetAllOrders() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/order").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void testUpdateNonExistingOrder() throws Exception {

		Mockito.when(orderService.updateOrder(Mockito.anyLong(), Mockito.any(OrderUpdateDto.class)))
				.thenThrow(ResourceNotFoundException.class);

		OrderUpdateDto orderUpdate = new OrderUpdateDto();
		orderUpdate.setStatus(OrderStatus.COOKING);

		mockMvc.perform(MockMvcRequestBuilders.put("/order/1234").content(mapper.writeValueAsString(orderUpdate))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	@Test
	public void testUpdateInvalidOrderThenThrowBadRequest() throws Exception {

		OrderUpdateDto orderUpdate = new OrderUpdateDto();
		orderUpdate.setStatus(null);

		mockMvc.perform(MockMvcRequestBuilders.put("/order/1234").content(mapper.writeValueAsString(orderUpdate))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.validation.status", containsString("must not be null")))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}

	@Test
	public void testGetOrder() throws Exception {
		OrderDetail mockOrderDetail = new OrderDetail();
		mockOrderDetail.setId(1234l);
		Mockito.when(orderService.getOrder(Mockito.anyLong())).thenReturn(mockOrderDetail);

		mockMvc.perform(MockMvcRequestBuilders.get("/order/1234").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1234)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
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
		user.setContactNumber("09123456789");
		orderMock.setUser(user);
		orderMock.setPaymentType(PaymentType.CASH);
		orderMock.setQuantity(1l);
		return orderMock;
	}
}
