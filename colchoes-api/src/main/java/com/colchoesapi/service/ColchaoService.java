package com.colchoesapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colchoesapi.model.Colchao;

import org.springframework.beans.factory.annotation.Autowired;
import com.colchoesapi.repository.ColchaoRepository;
import java.util.List;
import java.util.Optional;

@Service 
public class ColchaoService {
	
	@Autowired
	private ColchaoRepository colchaoRepository;
	
	public List<Colchao> findAll() {
		return colchaoRepository.findAll();	
	}
	
	public Optional<Colchao> findById(Long id) {
		return colchaoRepository.findById(id);	
	}
	
	public Colchao save(Colchao colchao) {
		return colchaoRepository.save(colchao);
	}
	
	public void delete (Long id) {
		colchaoRepository.deleteById(id);
	}
}
