package com.janaldous.breadforyouph.webfacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.janaldous.breadforyouph.webfacade.dto.OrderDto;

@Component("beforeCreateOrderDtoValidator")
public class OrderDtoValidator implements Validator {
	
	private final static Logger logger = LoggerFactory.getLogger(OrderDtoValidator.class);
	
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDto.class.equals(clazz);
    }
 
    @Override
    public void validate(Object obj, Errors errors) {
    	OrderDto orderDto = (OrderDto) obj;
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "field.required");
//        if (checkInputString(orderDto.getName())) {
//            errors.rejectValue("name", "name.empty");
//        }
        
        logger.info("im here");
        
//    
        if (notNull(orderDto.getQuantity())) {
            errors.rejectValue("quantity", "quantity.empty");
        }
    }
 
    private boolean notNull(Object obj) { return obj != null; }
    
    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
}