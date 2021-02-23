package com.jackson.carros.controller;


import java.util.List;
import java.util.Optional;

import com.jackson.carros.controller.dto.CarroDto;
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
	public ResponseEntity<List<CarroDto>> get(){
		
		List<CarroDto> carros = service.getCarros();
		return ResponseEntity.ok(carros);

	}
	
	
	@GetMapping("/{id}")
	@CacheEvict(value = "listaDeCarros", allEntries = true)
	public ResponseEntity get(@PathVariable("id") Long id){
		Optional<CarroDto> carro = service.getCarroById(id);
		return ResponseEntity.ok(carro);
	}

	
	@GetMapping("/tipo/{tipo}")
	@CacheEvict(value = "listaDeCarros")
	public ResponseEntity get(@PathVariable("tipo") String tipo){
		List<CarroDto> carros = service.getCarroByTipo(tipo);
		return carros.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(carros);
		
	}
		
	@CacheEvict(value = "listaDeCarros")
	@PostMapping
	public String post(@RequestBody Carro carro){
		CarroDto c = service.insert(carro);

		return "Carro Salvo com Sucesso" + c.getId();
	}

	
	@CacheEvict(value = "listaDeCarros")
	@PutMapping("/{id}")
	public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro){

		carro.setId(id);

		CarroDto c = service.update(carro, id);
		
		return c != null ?
				ResponseEntity.ok(c) :
				ResponseEntity.notFound().build();
	}
	
	
	@CacheEvict(value = "listaDeCarros")
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable("id") Long id){
		
		service.delete(id);
		
		return ResponseEntity.ok().build();
	}
	
	
	
}
