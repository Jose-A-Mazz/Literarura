package com.example.Literarura.modelos;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @Column(unique = true)
    private String nombre;

    private Integer fechaNacimiento;

    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "escritor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor (String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {

        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;

    }

    public Autor () {}


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void agregarLibros (Libro libro) {
        this.libros.add(libro);
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
