package com.example.Literarura.servicio;

import com.example.Literarura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {


    List<Libro> findByAutor (String nombreEscritor);



}
