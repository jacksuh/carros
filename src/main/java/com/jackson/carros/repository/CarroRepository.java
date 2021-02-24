package com.jackson.carros.repository;

import com.jackson.carros.controller.modelo.Carro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CarroRepository extends JpaRepository<Carro, Long>{

	List<Carro>findByTipo(String tipo, Pageable pageable);
}
