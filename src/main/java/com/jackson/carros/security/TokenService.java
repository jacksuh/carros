package com.jackson.carros.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jackson.carros.controller.modelo.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	//ler arquivo properties
		@Value("${forum.jwt.expiration}")
		private String expiration;
		
		//ler arquivo properties
		@Value("${forum.jwt.secret}")
		private String secret;

		public String gerarToken(Authentication authentication) {
			Usuario logado = (Usuario) authentication.getPrincipal();
			Date hoje = new Date();
			Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
			
			return Jwts.builder()
					.setIssuer("API do Fórum da Alura")
					.setSubject(logado.getId().toString())
					.setIssuedAt(hoje)
					.setExpiration(dataExpiracao)
					.signWith(SignatureAlgorithm.HS256, secret)
					.compact();
		}

		public boolean isTokenValido(String token) {
			try {
				Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}

		public Long geIdUsuario(String token) {
			Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			return Long.parseLong(claims.getSubject());
		}

	}