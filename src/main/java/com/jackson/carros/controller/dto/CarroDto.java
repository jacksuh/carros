package com.jackson.carros.controller.dto;


import com.jackson.carros.controller.modelo.Carro;
import lombok.Data;


public class CarroDto {

	private Long id;
    private String nome;
    private String tipo;
 
    
    public CarroDto(Carro carro){
    	this.id = carro.getId();
    	this.nome = carro.getNome();
    	this.tipo = carro.getTipo();
    
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}
