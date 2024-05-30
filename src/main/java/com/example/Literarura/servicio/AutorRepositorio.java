package com.example.Literarura.servicio;

import com.example.Literarura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreContainingIgnoreCase(String nombre);



    @Query("SELECT a FROM Autor a WHERE a.fechaFallecimiento > :fecha AND a.fechaNacimiento <= :fecha ")
    List<Autor> buscarAutoresVivos (int fecha);
}
