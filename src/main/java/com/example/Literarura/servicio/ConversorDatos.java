package com.example.Literarura.servicio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorDatos {

    private ObjectMapper objetMapper = new ObjectMapper();

    public <T> T convertirDatos (String json, Class<T> clase) throws JsonProcessingException {

        return objetMapper.readValue(json, clase);


    }

}
