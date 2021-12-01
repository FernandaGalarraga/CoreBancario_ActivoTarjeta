/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.cliente.tarjetacliente.controller;

import ec.edu.espe.arquitectura.cliente.tarjetacliente.service.TarjetaClienteService;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.TarjetaActivoRS;
import ec.espe.edu.arquitectura.cliente.tarjetacliente.wsdl.TarjetaClienteRS;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Windows Boo
 */
@RestController
@RequestMapping("/api/tarjetaCliente")
@Slf4j
public class TarjetaClienteController {

    private final TarjetaClienteService service;

    public TarjetaClienteController(TarjetaClienteService service) {
        this.service = service;
    }

    @GetMapping(value = "/nroTarjeta/{nroTarjeta}")
    public ResponseEntity obtenerPorNroTarjeta(@PathVariable("nroTarjeta") String nroTarjeta) throws DatatypeConfigurationException {
        log.info("Obteniendo tarjeta de credito por numero: {} esperando por resultados", nroTarjeta);
        TarjetaClienteRS tarjeta = this.service.obtenerPorNumeroTarjeta(nroTarjeta);
        try {
            if (tarjeta != null) {
                return ResponseEntity.ok(tarjeta);
            } else {
                System.out.println("El número de tarjeta: " + nroTarjeta + ", no existe");
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la tarjeta. {} - retorna badrequest", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/codcliente/{codCliente}")
    public ResponseEntity obtenerActivoTarjetaCliente(@PathVariable("codCliente") String codCliente) {
        try {
            log.info("Se va a obtener la información del cliente {}", codCliente);
            List<TarjetaActivoRS> tarjetaCliente = service.obtenerPorCodigoCliente(codCliente);

            return ResponseEntity.ok(tarjetaCliente);

        } catch (Exception e) {
            log.error("Ocurrio un error. {} - retorna badrequest", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/ultimosMovimientos")
    public ResponseEntity listarMovimientosActuales() {
        try {
            List<TarjetaClienteRS> tarjetasClienteRS = this.service.obtenerMovimientosActuales();
            if (tarjetasClienteRS.size() <= 0) {
                log.info("No se encontro ningun movimiento");
                return ResponseEntity.badRequest().build();
            } else {
                log.info("Movimientos obtenidas {}",
                        tarjetasClienteRS.size());
                return ResponseEntity.ok(tarjetasClienteRS);
            }
        } catch (Exception e) {
            log.error("Ocurrio un error. {} - retorna badrequest", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
