package com.example.Literarura.modelos;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)

public class DatosLibro{

    @JsonProperty("id") Integer id;
    @JsonProperty("title") String titulo;

    private String idioma;

    private String imagen;


    @JsonProperty("authors") List<AutorDTO> autor;

    @JsonProperty("formats")
    private void establecerPoster(Map<String, String> formats) {

        this.imagen = formats.get("image/jpeg");
    }

    @JsonProperty("languages")
    private void extraerIdiomas(List<String> languages){
        this.idioma = languages.get(0);
    }


//    @JsonProperty("authors")
//    private void recuperarAutores(List<Map<String, Object>> authors) {
//        this.autor = (String) authors.get(0).get("name");
//    }


    @Override
    public String toString() {
        return "Titulo: "+this.titulo + "\n"+
                "Imagen: "+this.imagen +"\n"+
                "Autor: "+this.autor + "\n" +
                "Id: "+this.id;
    }


    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getImagen() {
        return imagen;
    }

    public List<AutorDTO> getAutor() {
        return autor;
    }
}


//
//{
//        "id": 1342,
//        "title": "Pride and Prejudice",
//        "authors": [
//        {
//        "name": "Austen, Jane",
//        "birth_year": 1775,
//        "death_year": 1817
//        }
//        ],
//        "translators": [],
//        "subjects": [
//        "Courtship -- Fiction",
//        "Domestic fiction",
//        "England -- Fiction",
//        "Love stories",
//        "Sisters -- Fiction",
//        "Social classes -- Fiction",
//        "Young women -- Fiction"
//        ],
//        "bookshelves": [
//        "Best Books Ever Listings",
//        "Harvard Classics"
//        ],
//        "languages": [
//        "en"
//        ],
//        "copyright": false,
//        "media_type": "Text",
//        "formats": {
//        "text/html": "https://www.gutenberg.org/ebooks/1342.html.images",
//        "application/epub+zip": "https://www.gutenberg.org/ebooks/1342.epub3.images",
//        "application/x-mobipocket-ebook": "https://www.gutenberg.org/ebooks/1342.kf8.images",
//        "application/rdf+xml": "https://www.gutenberg.org/ebooks/1342.rdf",
//        "image/jpeg": "https://www.gutenberg.org/cache/epub/1342/pg1342.cover.medium.jpg",
//        "text/plain; charset=us-ascii": "https://www.gutenberg.org/ebooks/1342.txt.utf-8",
//        "application/octet-stream": "https://www.gutenberg.org/cache/epub/1342/pg1342-h.zip"
//        },
//        "download_count": 62862
//        }
