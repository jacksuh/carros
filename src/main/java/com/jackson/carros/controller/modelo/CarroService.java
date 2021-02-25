package com.jackson.carros.controller.modelo;



import com.jackson.carros.controller.dto.CarroDto;
import com.jackson.carros.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;
	
	
	public Collection<CarroDto> getCarros(Pageable pageable) {

		return repository.findAll(pageable).stream().map(CarroDto::create).collect(Collectors.toList());
    }


	public Optional<CarroDto> getCarroById(Long id) {
		return repository.findById(id).map(CarroDto::create);

	}


	public Collection<CarroDto> getCarroByTipo(String tipo, Pageable pageable) {

		return repository.findByTipo(tipo,pageable).stream().map(CarroDto::create).collect(Collectors.toList());
	}


	public CarroDto insert(Carro carro) {
		return CarroDto.create(repository.save(carro));
		
	}

	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();
	}


	public CarroDto update(Carro carro, Long id) {
		Optional<Carro> optional = repository.findById(id);
		if(optional.isPresent()){
			Carro db = optional.get();
			
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			
			repository.save(db);
			
			return CarroDto.create(db);
		}else {
			throw new RuntimeException("Não foi possivel atualizar o registro");
		}
		
	}


	public void delete(Long id) {
		Optional<Carro> carro = repository.findById(id);
		if(carro.isPresent()){
			repository.deleteById(id);
		}
	}
	
	
	
}
