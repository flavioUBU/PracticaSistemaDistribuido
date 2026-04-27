package com.sistemasdistr.basico.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ApiTestService {

    private final RestTemplate restTemplate;

    public ApiTestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String probarLlamadaSimple() {
        try {
            return llamarApiExterna();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "La API externa devolvió un error 404: recurso no encontrado.";
            }
            return "La API externa devolvió un error del cliente.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API externa. Detalle: " + e.getMessage();

        }
    }

    private String llamarApiExterna() {
        String url = "http://api-flask:5000/saludo";
        return restTemplate.getForObject(url, String.class);
    }

    public String probarErrorArchivo() {
        try {
            String url = "http://api-flask:5000/archivo-error";
            return restTemplate.getForObject(url, String.class);
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error al abrir un archivo.";
        } catch (HttpClientErrorException e) {
            return "La API Flask devolvió un error del cliente.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }

    public String probarErrorBaseDatos() {
        try {
            String url = "http://api-flask:5000/db-error";
            return restTemplate.getForObject(url, String.class);
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error de acceso a la base de datos.";
        } catch (HttpClientErrorException e) {
            return "La API Flask devolvió un error del cliente.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }

    public String probarPokemon() {
        try {
            String url = "http://api-flask:5000/pokemon/pikachu";
            String respuesta = restTemplate.getForObject(url, String.class);

            String nombre = extraerValor(respuesta, "nombre");
            String id = extraerValor(respuesta, "id");
            String altura = extraerValor(respuesta, "altura");
            String peso = extraerValor(respuesta, "peso");

            return "Pokémon consultado correctamente -> Nombre: " + nombre
                    + ", ID: " + id
                    + ", Altura: " + altura
                    + ", Peso: " + peso + ".";
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "La API Flask informó de que el Pokémon no existe.";
            }
            return "La API Flask devolvió un error del cliente.";
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error al consultar Pokémon.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }

    private String extraerValor(String json, String clave) {
        String patronTexto = "\"" + clave + "\": \"";
        int inicioTexto = json.indexOf(patronTexto);
        if (inicioTexto != -1) {
            inicioTexto += patronTexto.length();
            int finTexto = json.indexOf("\"", inicioTexto);
            return json.substring(inicioTexto, finTexto);
        }

        String patronNumero = "\"" + clave + "\": ";
        int inicioNumero = json.indexOf(patronNumero);
        if (inicioNumero != -1) {
            inicioNumero += patronNumero.length();
            int finNumero = json.indexOf(",", inicioNumero);
            if (finNumero == -1) {
                finNumero = json.indexOf("}", inicioNumero);
            }
            return json.substring(inicioNumero, finNumero).trim();
        }

        return "desconocido";
    }

    public String probarPokemonError() {
        try {
            String url = "http://api-flask:5000/pokemon-error";
            String respuesta = restTemplate.getForObject(url, String.class);
            return "Respuesta inesperada: " + respuesta;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "La API Flask informó de que el Pokémon solicitado no existe.";
            }
            return "La API Flask devolvió un error del cliente.";
        } catch (HttpServerErrorException e) {
            return "La API Flask informó de un error al consultar Pokémon.";
        } catch (RestClientException e) {
            return "No se pudo conectar con la API Flask.";
        }
    }
}