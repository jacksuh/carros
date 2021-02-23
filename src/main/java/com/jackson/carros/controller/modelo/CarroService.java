package com.jackson.carros.controller.modelo;



import com.jackson.carros.controller.dto.CarroDto;
import com.jackson.carros.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;
	
	
	public List<CarroDto> getCarros() {

		return repository.findAll().stream().map(CarroDto::create).collect(Collectors.toList());
    }


	public Optional<CarroDto> getCarroById(Long id) {
		return repository.findById(id).map(CarroDto::create);

	}


	public List<CarroDto> getCarroByTipo(String tipo) {

		return repository.findByTipo(tipo).stream().map(CarroDto::create).collect(Collectors.toList());
	}


	public Carro insert(Carro carro) {
		return repository.save(carro);
		
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
