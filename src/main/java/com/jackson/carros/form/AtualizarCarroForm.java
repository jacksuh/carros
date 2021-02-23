package com.jackson.carros.form;

import javax.validation.constraints.NotNull;

import com.jackson.carros.controller.modelo.Carro;
import com.jackson.carros.repository.CarroRepository;


public class AtualizarCarroForm {

	@NotNull
	private String nome;
	
	@NotNull
    private String tipo;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Carro atualizar(Long id, CarroRepository carroRepository) {
		Carro carro = carroRepository.getOne(id);
		
		carro.setNome(this.nome);
		carro.setTipo(this.tipo);
		
		return carro;
	}
	
}
