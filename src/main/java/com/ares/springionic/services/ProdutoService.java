package com.ares.springionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ares.springionic.domain.Categoria;
import com.ares.springionic.domain.Produto;
import com.ares.springionic.repositories.CategoriaRepository;
import com.ares.springionic.repositories.ProdutoRepository;
import com.ares.springionic.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepo;

	public List<Produto> findAll() {
		return repo.findAll();
	}

	public Produto find(Integer id) {
		Optional<Produto> p = repo.findById(id);
		return p.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado, ID: " + id));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
