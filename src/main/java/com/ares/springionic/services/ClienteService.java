package com.ares.springionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ares.springionic.domain.Categoria;
import com.ares.springionic.domain.Cliente;
import com.ares.springionic.repositories.ClienteRepository;
import com.ares.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado. Id: " + id + ", tipo: " + Categoria.class.getName()));
	}

}
