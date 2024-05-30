package com.example.Literarura;

import com.example.Literarura.modelos.*;
import com.example.Literarura.servicio.AutorRepositorio;
import com.example.Literarura.servicio.ConsumoAPI;
import com.example.Literarura.servicio.ConversorDatos;
import com.example.Literarura.servicio.LibroRepositorio;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


@Service
public class Principal {



    private LibroRepositorio repositorio;
    private AutorRepositorio autorRepositorio;


    public Principal(LibroRepositorio repositorio, AutorRepositorio autorRepositorio) {

        this.repositorio = repositorio;
        this.autorRepositorio = autorRepositorio;
    }

    private final String URL_BASE = "http://gutendex.com/books/";

    private DatosLibro libroElegido;

    private Autor autorAgregar;

    private ConsumoAPI api = new ConsumoAPI();

    private ConversorDatos conversor = new ConversorDatos();

    private int opcionElegida;


    private Scanner teclado = new Scanner(System.in);

    private String mensajeUsuario = """
            1 - Buscar Libro por título
            2 - Ver libros registrados
            3 - listar autores registrados
            4 - listar autores vivos en un determinado año
            5 - Listar libros por idiomas
            """;


    public void mostrarMenu () throws JsonProcessingException {
        while (opcionElegida < 5 && opcionElegida >= 0){

            System.out.println(mensajeUsuario);
            opcionElegida = teclado.nextInt();
            teclado.nextLine();
            switch (opcionElegida){
                case 1:
                    // buscar libros por titulo
                    buscarPorTitulo();
                    break;
                case 2:
                    // buscar libros
                    buscarTodosLosLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:

                    listarLibrosPorIdioma();
                    //
                    break;

                default:
                    System.out.println("La opción ingresada no es válida!");
            }
        }
    }

    private void buscarPorTitulo() throws JsonProcessingException {

        System.out.println("Escriba el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        String url = URL_BASE + "?search="+nombreLibro.replace(" ", "%20");
        String json = api.obtenerDatos(url);
        ResultadosDTO librosEncontrados = conversor.convertirDatos(json, ResultadosDTO.class);
        var resultados = librosEncontrados.resultados();

        if(resultados.size() > 1){
            libroElegido = mostrarListaLibros(librosEncontrados.resultados());
        } else {
            //unica coincidencia encontrada
            libroElegido = resultados.get(0);
            System.out.println("Su búsqueda arrojó el siguiente resultado");
            System.out.println("*****************************************");
            System.out.printf("""
                    *********************
                    Titulo: %s
                    Autor: %s
                    ******************%n""", libroElegido.getTitulo(), libroElegido.getAutor());
        }

        //aquí se procede a agregar el libro que resultó de la búqueda, así como el autor
        AutorDTO a = libroElegido.getAutor().get(0);
        Libro libro = new Libro(libroElegido.getTitulo(), libroElegido.getAutor(), libroElegido.getImagen(), libroElegido.getIdioma(), libroElegido.getId());
        //verificar si existe el autor
        Optional<Autor> autorExiste = revisarAutor(a);
        if(autorExiste.isPresent()){

            //buscar libros ya existentes

            var librosExistentes = repositorio.findAll().stream()
                    .filter(l-> l.getAutor().toLowerCase().equals(autorExiste.get().getNombre()))
                    .collect(Collectors.toList());


            // agregar libros al autor

            autorAgregar = autorExiste.get();
            libro.setEscritor(autorAgregar);
            librosExistentes.add(libro);
            autorAgregar.setLibros(librosExistentes);

        } else {

            //crear autor
            autorAgregar = new Autor(a.nombre(), a.nacimiento(), a.fallecimiento());

            libro.setEscritor(autorAgregar);

            // agregar libro

            autorAgregar.agregarLibros(libro);

        }



        //agregar Autor a la base de datos (Actualización || agregado por primera vez)

        autorRepositorio.save(autorAgregar);

    }

    public DatosLibro mostrarListaLibros (List<DatosLibro> resultados) {

        System.out.println("Encontramos más de una coincidencia para su búsqueda");
        while(true){
            System.out.println("Elija el libro que desea ingresar a la base de datos");
            int index = 1;

                for (DatosLibro resultado : resultados) {

                    System.out.printf("""
                            **********
                            %d)Titulo: %s
                            Autor:  %s
                            Código: %d
                            ***********%n""", index, resultado.getTitulo(), resultado.getAutor(), resultado.getId());

                    index++;

                }

            int opcionElegida = teclado.nextInt();
            teclado.nextLine();
            if(opcionElegida > 0 && opcionElegida <= resultados.size()){
                //retorna DatosLibro
                return resultados.get(opcionElegida-1);
            } else {
                System.out.println("La opción ingresada no es válida, inténtelo nuevamente");
            }
        }

    }


    public void buscarTodosLosLibros () {

        List<Libro> libros = repositorio.findAll();

        int index = 1;

        for (Libro libro : libros) {

            System.out.println("""
                    **********
                    %d)Titulo: %s
                    Autor:  %s
                    Código: %d
                    ***********""".formatted(index, libro.getTitulo(), libro.getAutor(), libro.getId()));

            index++;

        }


    }

  public void  listarAutores () {

        List<Autor> autores = autorRepositorio.findAll();

        Integer index = 1;

      for (Autor autor : autores) {

          System.out.printf("""
                  **********
                  %d) Nombre: %s
                  Fecha de Nacimiento:  %s
                  Fecha de fallecimiento: %d
                  Libros: %s
                  ***********%n""", index, autor.getNombre(), autor.getFechaNacimiento(), autor.getFechaFallecimiento(), autor.getLibros());
          index++;

      }

    }


    public Optional<Autor> revisarAutor (AutorDTO autor) {

        var autorEncontrado = autorRepositorio.findByNombreContainingIgnoreCase(autor.nombre());

        return autorEncontrado;


    }

    public void listarAutoresVivos () {

        System.out.println("Ingrese una fecha");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepositorio.buscarAutoresVivos(fecha);

        autores.forEach(a-> System.out.printf("""
                Autor: %s
                Fecha de Nacimiento: %d
                Libros: %s%n""", a.getNombre(), a.getFechaNacimiento(), a.getLibros()));


    }


    public void listarLibrosPorIdioma () {
        System.out.println("Indique el idioma de la siguiente lista");
        System.out.println("""
                1 - En (Inglés)
                2 - ES (Español)
                3 - FR (francés)
                4 - PT (Portugués)
                5 - DE (Alemán)""");

        Integer opcion = teclado.nextInt();
        teclado.nextLine();
        if(opcion > 5 || opcion <= 0) {
            System.out.println("Opción incorrecta. Elija de nuevo");
        } else {
            String idioma = "";
            switch (opcion){
                case 1:
                    idioma = "en";
                    break;
                case 2:
                    idioma = "es";
                    break;
                case 3:
                    idioma = "fr";
                    break;
                case 4:
                    idioma = "pt";
                    break;
                case 5:
                    idioma = "de";
                    break;
            }

            String finalIdioma = idioma;
            List<Libro> libros = repositorio.findAll().stream()
                    .filter(l-> l.getIdioma().equals(finalIdioma))
                    .collect(Collectors.toList());


            libros.forEach(System.out::println);


        }
    }



}
