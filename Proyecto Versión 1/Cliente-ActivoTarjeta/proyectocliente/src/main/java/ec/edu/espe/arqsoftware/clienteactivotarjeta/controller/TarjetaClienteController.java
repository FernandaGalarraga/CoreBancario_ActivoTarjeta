/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqsoftware.clienteactivotarjeta.controller;

import ec.edu.espe.arqsoftware.clienteactivotarjeta.service.TarjetaClienteService;
import ec.espe.edu.arqsoftware.clienteactivotarjeta.wsdl.TarjetaClienteRQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tarjetaCliente")
@Slf4j
public class TarjetaClienteController {
    
    private final TarjetaClienteService service;

    public TarjetaClienteController(TarjetaClienteService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity crearTarjetaCliente(@RequestBody TarjetaClienteRQ request) {
        try {
            log.info("Se va a crear una tarjeta de credito con la siguiente informacion: {}", request);
            this.service.crearTarjetaCliente(request);
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la tarjeta. {} - retorna badrequest", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
