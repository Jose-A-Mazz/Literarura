package com.example.Literarura.servicio;

import com.example.Literarura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreContainingIgnoreCase(String nombre);
}
