package com.jackson.carros.controller.dto;


import com.jackson.carros.controller.modelo.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarroDto {

	private Long id;
    private String nome;
    private String tipo;


    /**public CarroDto(Carro carro){
     this.id = carro.getId();
     this.nome = carro.getNome();
     this.tipo = carro.getTipo();

     /* *}
     *
     */

    public static CarroDto create(Carro carro) {
        ModelMapper modelMapper = new ModelMapper() {
            return modelMapper.map(carro, CarroDto.class);
        };
}
