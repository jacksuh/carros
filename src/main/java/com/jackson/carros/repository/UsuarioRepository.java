package com.jackson.carros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jackson.carros.controller.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);
}
