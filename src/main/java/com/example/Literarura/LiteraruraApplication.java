package com.example.Literarura;

import com.example.Literarura.modelos.Autor;
import com.example.Literarura.servicio.AutorRepositorio;
import com.example.Literarura.servicio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraruraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepositorio repositorio;

	@Autowired
	private AutorRepositorio autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraruraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(repositorio, autorRepositorio);

		principal.mostrarMenu();

	}
}
