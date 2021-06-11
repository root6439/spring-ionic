package com.ares.springionic.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ares.springionic.domain.Categoria;
import com.ares.springionic.domain.Cidade;
import com.ares.springionic.domain.Cliente;
import com.ares.springionic.domain.Endereco;
import com.ares.springionic.domain.enums.TipoCliente;
import com.ares.springionic.dto.ClienteDTO;
import com.ares.springionic.dto.ClienteNewDTO;
import com.ares.springionic.repositories.ClienteRepository;
import com.ares.springionic.repositories.EnderecoRepository;
import com.ares.springionic.services.exceptions.DataIntegrityException;
import com.ares.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository endRepo;

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado. Id: " + id + ", tipo: " + Categoria.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cli) {
		cli.setId(null);
		endRepo.saveAll(cli.getEnderecos());
		return repository.save(cli);
	}

	public void update(Cliente cli) {
		Cliente newCli = find(cli.getId());
		updateData(newCli, cli);
		repository.save(newCli);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente com pedidos");
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pagerequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pagerequest);
	}

	public Cliente fromDTO(ClienteDTO cli) {
		Cliente c = new Cliente();
		c.setId(cli.getId());
		c.setNome(cli.getNome());
		c.setEmail(cli.getEmail());
		return c;
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfCnpj(),
				TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
		
	}

	private void updateData(Cliente newCli, Cliente cli) {
		newCli.setNome(cli.getNome());
		newCli.setEmail(cli.getEmail());
	}

}
