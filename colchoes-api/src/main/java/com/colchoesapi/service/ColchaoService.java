package com.colchoesapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import com.colchoesapi.model.Colchao;

import com.colchoesapi.repository.ColchaoRepository;
import java.util.List;
import java.util.Optional;

@Service 
public class ColchaoService {
	
	@Autowired
	private ColchaoRepository colchaoRepository;
	
	@Cacheable("colchoes")
	public List<Colchao> findAll() {
		return colchaoRepository.findAll();	
	}
	
	@Cacheable(value = "colchaoById", key = "#id")
	public Optional<Colchao> findById(Long id) {
		return colchaoRepository.findById(id);	
	}
	
	@CacheEvict(value = {"colchoes", "colchaoById"}, allEntries = true)
	public Colchao save(Colchao colchao) {
		return colchaoRepository.save(colchao);
	}
	
	@CacheEvict(value = {"colchoes", "colchaoById"}, allEntries = true)
	public void delete (Long id) {
		colchaoRepository.deleteById(id);
	}
}
