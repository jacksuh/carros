package com.jackson.carros;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jackson.carros.controller.modelo.Carro;
import com.jackson.carros.controller.modelo.CarroService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosServiceTest {

	@Autowired
    private CarroService repository;
	
	@Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        Carro c = repository.insert(carro);

        Assert.assertNotNull(c);

        Long id = c.getId();
        Assert.assertNotNull(id);

        // Buscar o objeto
        Optional<Carro> op = repository.getCarroById(id);
        Assert.assertTrue(op.isPresent());
        
        c = op.get();
        Assert.assertEquals("Porshe", c.getNome());
        Assert.assertEquals("esportivos",c.getTipo());

        // Deletar o objeto
        repository.delete(id);

        // Verificar se deletou
            repository.getCarroById(id);
          
    }

    @Test
    public void testLista() {

        Page<Carro> carros = repository.getCarros(PageRequest.of(0,29));

        Assert.assertEquals(29, carros.getSize());
    }

   

}
