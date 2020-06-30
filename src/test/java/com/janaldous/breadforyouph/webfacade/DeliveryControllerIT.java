package com.janaldous.breadforyouph.webfacade;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.service.DeliveryDateService;
import com.janaldous.breadforyouph.testutil.TestUtils;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DeliveryDateService deliveryDateService;
	
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

		mockMvc.perform(MockMvcRequestBuilders.get("/delivery?page=0&size=5").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)))
				.andReturn();
	}
	
	@Test
	public void testDeliveryDateWihNoResult() throws Exception {
		List<DeliveryDate> availableDates = new ArrayList<>();
		Page<DeliveryDate> page = new PageImpl<>(availableDates);
		Mockito.when(deliveryDateService.getDeliveryDates(0, 5)).thenReturn(page);

		mockMvc.perform(MockMvcRequestBuilders.get("/delivery?page=0&size=5").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)))
				.andReturn();
	}
	
	@Test
	public void testDeliveryDateWith404() throws Exception {
		List<DeliveryDate> availableDates = new ArrayList<>();
		Page<DeliveryDate> page = new PageImpl<>(availableDates);
		Mockito.when(deliveryDateService.getDeliveryDates(1, 5)).thenReturn(page);
		
		assertEquals(1, page.getTotalPages());

		mockMvc.perform(MockMvcRequestBuilders.get("/delivery?page=1&size=5").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();
	}
	
	@Test
	public void testCreateDeliveryDate() throws Exception {
		DeliveryDateDto deliveryDateDto = new DeliveryDateDto();
		DeliveryDate mockDeliveryDateResult = new DeliveryDate();
		mockDeliveryDateResult.setDate(new Date());
		mockDeliveryDateResult.setId(1l);
		mockDeliveryDateResult.setOrderLimit(6);
		Mockito.when(deliveryDateService.createDeliveryDate(deliveryDateDto)).thenReturn(mockDeliveryDateResult);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/delivery")
				.content(mapper.writeValueAsString(deliveryDateDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderLimit", is(6)))
				.andReturn();
	}

}
