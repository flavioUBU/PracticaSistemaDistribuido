package com.sistemasdistr.basico.controller;

import com.sistemasdistr.basico.service.ApiTestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Maincontroller {

    private final ApiTestService apiTestService;

    public Maincontroller(ApiTestService apiTestService) {
        this.apiTestService = apiTestService;
    }

    @GetMapping("/")
    public String vistaHome(ModelMap interfazConPantalla) {
        return "index";
    }

    @GetMapping("/api-test")
    public String vistaApiTest(ModelMap interfazConPantalla) {
        interfazConPantalla.addAttribute("mensaje", "Aquí aparecerá el resultado de la llamada a la API Python.");
        return "api-test";
    }

    @GetMapping("/api-test/probar")
    public String probarLlamadaApi(ModelMap interfazConPantalla) {
        String resultado = apiTestService.probarLlamadaSimple();
        interfazConPantalla.addAttribute("mensaje", resultado);
        return "api-test";
    }

    @GetMapping("/api-test/archivo-error")
    public String probarErrorArchivo(ModelMap interfazConPantalla) {
        String resultado = apiTestService.probarErrorArchivo();
        interfazConPantalla.addAttribute("mensaje", resultado);
        return "api-test";
    }

    @GetMapping("/api-test/db-error")
    public String probarErrorBaseDatos(ModelMap interfazConPantalla) {
        String resultado = apiTestService.probarErrorBaseDatos();
        interfazConPantalla.addAttribute("mensaje", resultado);
        return "api-test";
    }

    @GetMapping("/api-test/pokemon")
    public String probarPokemon(ModelMap interfazConPantalla) {
        String resultado = apiTestService.probarPokemon();
        interfazConPantalla.addAttribute("mensaje", resultado);
        return "api-test";
    }

    @GetMapping("/api-test/pokemon-error")
    public String probarPokemonError(ModelMap interfazConPantalla) {
        String resultado = apiTestService.probarPokemonError();
        interfazConPantalla.addAttribute("mensaje", resultado);
        return "api-test";
    }
}