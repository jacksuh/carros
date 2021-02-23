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
	
	
	public List<CarroDto> getCarros(Pageable pageable) {

		return repository.findAll().stream().map(CarroDto::new).collect(Collectors.toList());
    }


	public Optional<Carro> getCarroById(Long id) {
		return repository.findById(id);

	}


	public List<CarroDto> getCarroByTipo(String tipo) {

		return repository.findByTipo(tipo).stream().map(CarroDto::new).collect(Collectors.toList());
	}


	public Carro insert(Carro carro) {
		return repository.save(carro);
		
	}


	public Carro update(Carro carro, Long id) {
		Optional<Carro> optional = getCarroById(id);
		if(optional.isPresent()){
			Carro db = optional.get();
			
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			
			repository.save(db);
			
			return db;
		}else {
			throw new RuntimeException("Não foi possivel atualizar o registro");
		}
		
	}


	public void delete(Long id) {
		Optional<Carro> carro = getCarroById(id);
		if(carro.isPresent()){
			repository.deleteById(id);
		}
	}
	
	
	
}
