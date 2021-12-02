/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqsoftware.cliente.tarjetaactivo.controller;

import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.dto.Serializador;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.exception.FoundException;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.service.TarjetaClienteService;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.ObtenerTodasRequest;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TarjetaActivoRS;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TarjetaClienteRQ;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TarjetaClienteRS;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @ApiOperation(value = "Crear una tarjeta de credito de un cliente",
            notes = "Inserción de una tarjeta de credito")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. La tarjeta de credito se guardo correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. No se pudo guardar la tarjeta de credito"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @PostMapping
    public ResponseEntity crearTarjetaCliente(@RequestBody TarjetaClienteRQ request) {
        try {
            log.info("Se va a crear una tarjeta de credito con la siguiente informacion: {}", request);
            TarjetaActivoRS tarjetas =  this.service.crearTarjetaCliente(request);
            return ResponseEntity.ok(tarjetas);
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la tarjeta. {} - retorna badrequest", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    
    @ApiOperation(value = "Listar datos de la tarjeta por el numero",
            notes = "Datos de la tarjeta cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Se encontro la tarjeta del cliente"),
        @ApiResponse(code = 400, message = "Bad Request. No se encontro ninguna tarjeta"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
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

    @ApiOperation(value = "Obtiene un tarjeta de credito cliente",
            notes = "Obtiene tarjetas de credito de un cliente de acuerdo a su codigo ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK, Cuando obtiene una tarjeta de credito cliente de acuerdo a su codigo"),
        @ApiResponse(code = 404, message = "No existe una tarjeta de credito del codigo del cliente enviado")
    })
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

    @ApiOperation(value = "Listar movimientos de todos los cliente",
            notes = "Movimientos de las tarjetas de los clientes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Se encontraron movimientos"),
        @ApiResponse(code = 400, message = "Bad Request. No se encontro ningun movimiento"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
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
    
    @ApiOperation(value = "Listar todas las tarjetas",
            notes = "Datos de la tarjeta del cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Se encontro la tarjeta del cliente"),
        @ApiResponse(code = 400, message = "Bad Request. No se encontro ninguna tarjeta"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "/todas")
    public ResponseEntity obtenerTodas() {
        log.info("Obteniendo todas las tarjetas de credito esperando por resultados");
         
        List<TarjetaActivoRS> tarjetas = this.service.ObtenerTodas(new ObtenerTodasRequest()).getTarjetaActivoRS();
        try {
            if(tarjetas.size()<=0){
                throw new FoundException("No se ha encontrado ningúna tarjeta.");
            }

            return ResponseEntity.ok(tarjetas);
        } catch (Exception e) {
            return Serializador.Error(e, "Error, no se encontró ningun producto");
        }
    }
}
