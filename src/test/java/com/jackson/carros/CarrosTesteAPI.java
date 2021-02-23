package com.jackson.carros;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jackson.carros.controller.modelo.Carro;
import com.jackson.carros.controller.modelo.CarroService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosTesteAPI {

	@Autowired
    protected TestRestTemplate rest;

	@Autowired
    private CarroService repository;
	
	
	private ResponseEntity<Carro> getCarro(String url) {
        return
                rest.withBasicAuth("jackson","123456").getForEntity(url, Carro.class);
    }
	
	
    private ResponseEntity<List<Carro>> getCarros(String url) {
        return rest.withBasicAuth("user","123").exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Carro>>() {
                });
    }
    
    @Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        // Insert
        ResponseEntity response = rest.withBasicAuth("jackson","123456").postForEntity("/carros", carro, null);
        System.out.println(response);

        // Verifica se criou
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        Carro c = getCarro(location).getBody();

        Assert.assertNotNull(c);
        Assert.assertEquals("Porshe", c.getNome());
        Assert.assertEquals("esportivos", c.getTipo());

        // Deletar o objeto
        rest.withBasicAuth("jackson","123456").delete(location);

        // Verificar se deletou
        Assert.assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
    }
    
    
    
    @Test
    public void testLista() {
        List<Carro> carros = getCarros("/carros").getBody();
        Assert.assertNotNull(carros);
        Assert.assertEquals(10, carros.size());
    }

    @Test
    public void testListaPorTipo() {

        assertEquals(5, getCarros("/carros/tipo/classicos").getBody().size());
        assertEquals(5, getCarros("/carros/tipo/esportivos").getBody().size());
        assertEquals(5, getCarros("/carros/tipo/luxo").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getCarros("/carros/tipo/xxx").getStatusCode());
    }

    @Test
    public void testGetOk() {

        ResponseEntity<Carro> response = getCarro("/carros/11");
 
        Carro c = response.getBody();
        assertEquals("Cadillac Eldorado", c.getNome());
    }
    
    
    @Test
    public void testGetNotFound() {

      ResponseEntity response = getCarro("/carros/1100");
       assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

}
