package com.jackson.carros.repository;

import com.jackson.carros.controller.modelo.Carro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CarroRepository extends JpaRepository<Carro, Long>{

	Page<Carro> findByTipo(String tipo, Pageable pageable);
}
