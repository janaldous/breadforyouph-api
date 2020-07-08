package com.janaldous.breadforyouph.webfacade.admin;

import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.breadforyouph.config.BasicSecurityConfiguration;
import com.janaldous.breadforyouph.data.DeliveryDate;
import com.janaldous.breadforyouph.service.DeliveryDateService;
import com.janaldous.breadforyouph.testutil.WithAdminUser;
import com.janaldous.breadforyouph.webfacade.ExceptionTranslator;
import com.janaldous.breadforyouph.webfacade.dto.DeliveryDateDto;

@WebMvcTest(DeliveryController.class)
@Import({ ExceptionTranslator.class, BasicSecurityConfiguration.class })
class DeliveryControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeliveryDateService deliveryDateService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@WithAdminUser
	public void testCreateDeliveryDate() throws Exception {
		DeliveryDateDto deliveryDateDto = new DeliveryDateDto();
		DeliveryDate mockDeliveryDateResult = new DeliveryDate();
		mockDeliveryDateResult.setDate(new Date());
		mockDeliveryDateResult.setId(1l);
		mockDeliveryDateResult.setOrderLimit(6);
		Mockito.when(deliveryDateService.createDeliveryDate(deliveryDateDto)).thenReturn(mockDeliveryDateResult);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/delivery").content(mapper.writeValueAsString(deliveryDateDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderLimit", is(6))).andReturn();
	}
	
	@Test
	public void testCreateDeliveryDateWithoutLogin() throws JsonProcessingException, Exception {
		DeliveryDateDto deliveryDateDto = new DeliveryDateDto();
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/delivery").content(mapper.writeValueAsString(deliveryDateDto))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

}
