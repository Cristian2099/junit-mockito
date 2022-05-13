package com.project.testing.calcular.controller;

import com.project.testing.calcular.service.ObtenerPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ObtenerPersonaController {

    @Autowired
    ObtenerPersonaService obtenerPersonaService;

}
