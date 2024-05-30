package com.example.Literarura.modelos;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(unique = true)
    private Integer codigo;

    private String titulo;


    private String autor;



    @ManyToOne
    private Autor escritor;

    private String caratula;

    private String idioma;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro () {

    }

    public Libro(String titulo, List<AutorDTO> autor, String caratula, String idioma, int codigo) {

        this.titulo = titulo;
        this.autor = autor.get(0).nombre();
        this.caratula = caratula;
        this.idioma = idioma;
        this.codigo = codigo;
    }


    public Autor getEscritor() {
        return escritor;
    }

    public void setEscritor(Autor escritor) {
        this.escritor = escritor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", codigo=" + codigo +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", caratula='" + caratula + '\'' +
                ", idioma='" + idioma + '\'' +
                '}';
    }
}
