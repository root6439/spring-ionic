package com.ares.springionic.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.ares.springionic.domain.Cliente;
import com.ares.springionic.dto.ClienteDTO;
import com.ares.springionic.repositories.ClienteRepository;
import com.ares.springionic.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		List<FieldMessage> list = new ArrayList<>();
		
		Integer uriId = Integer.parseInt(map.get("id"));
		Cliente cli = repo.findByEmail(objDto.getEmail());
		
		if ( cli != null && !cli.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "email já existe no banco de dados"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
