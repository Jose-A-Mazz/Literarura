package com.example.Literarura.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDTO(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer nacimiento,
        @JsonAlias("death_year") Integer fallecimiento
) {
}
