package com.jackson.carros.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jackson.carros.controller.modelo.Carro;
import com.jackson.carros.controller.modelo.CarroService;



@RestController
@RequestMapping("/carros")
public class CarrosController {

	@Autowired
	private CarroService service;
	
	
	@GetMapping
	@Cacheable(value = "listaDeCarros")
	public ResponseEntity<Page<Carro>> get(@RequestParam(value = "page", defaultValue = "0")
	Integer page, @RequestParam(value = "size", defaultValue = "10" ) Integer size ){
		
		Page<Carro> carros = service.getCarros(PageRequest.of(page, size));
		return ResponseEntity.ok(carros);

	}
	
	
	@GetMapping("/{id}")
	@CacheEvict(value = "listaDeCarros", allEntries = true)
	public ResponseEntity get(@PathVariable("id") Long id){
		Optional<Carro> carro = service.getCarroById(id);
		
		return carro.isPresent()?
				ResponseEntity.ok(carro.get()) :
					ResponseEntity.notFound().build();		
	}

	
	@GetMapping("/tipo/{tipo}")
	@CacheEvict(value = "listaDeCarros")
	public ResponseEntity get(@PathVariable("tipo") String tipo){
		List<Carro> carros = service.getCarroByTipo(tipo);
		return carros.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(carros);
		
	}
		
	@CacheEvict(value = "listaDeCarros")
	@PostMapping
	public String post(@RequestBody Carro carro){
		Carro c = service.insert(carro);
		
		return "Carro Salvo com Sucesso" + c.getId();
	}
	
	
	@CacheEvict(value = "listaDeCarros")
	@PutMapping("/{id}")
	public String put(@PathVariable("id") Long id, @RequestBody Carro carro){
		
		Carro c = service.update(carro, id);
		
		return "Carro Atualizado com Sucesso" + c.getId();
	}
	
	
	@CacheEvict(value = "listaDeCarros")
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id){
		
		service.delete(id);
		
		return "Carro Deletado com Sucesso";
	}
	
	
	
}
