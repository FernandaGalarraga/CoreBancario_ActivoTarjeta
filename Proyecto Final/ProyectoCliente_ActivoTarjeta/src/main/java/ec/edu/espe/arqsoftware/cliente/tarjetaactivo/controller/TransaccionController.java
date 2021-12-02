/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arqsoftware.cliente.tarjetaactivo.controller;

import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.dto.Serializador;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.exception.FoundException;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.service.TransaccionService;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TransaccionRQ;
import ec.edu.espe.arqsoftware.cliente.tarjetaactivo.wsdl.TransaccionRS;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaccion")
@Slf4j
public class TransaccionController {

    private final TransaccionService service;

    public TransaccionController(TransaccionService service) {
        this.service = service;
    }

    @ApiOperation(value = "Crear una transaccion de una tarjeta de un cliente",
            notes = "Inserción de una transaccion de una tarjeta de un cliente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. La transacción de credito se guardo correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. No se pudo guardar la transacción"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @PostMapping
    public ResponseEntity crearTransaccion(@RequestBody TransaccionRQ request) {
        try {
            log.info("Se va a crear una transaccion con la siguiente informacion: {}", request);
            this.service.crearTransaccion(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la transaccion. {} - retorna badrequest {}", e.getMessage(), e.getStackTrace());
            return Serializador.Error(e, "Error al crear una transacción, inténtelo de nuevo");
        }
    }

    @ApiOperation(value = "Listar transacciones por fecha",
            notes = "Listar transacciones entre dos fechas con formato yyyy-MM-dd")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Las transacciones se obtuvieron correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. El formato de fecha es: yyyy-MM-dd"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "{codTarjetaCliente}/{fechaInicio}/{fechaFin}")
    public ResponseEntity listarTransaccionesFechas(
            @PathVariable("codTarjetaCliente") String codTarjetaCliente,
            @PathVariable("fechaInicio") String fechaInicio,
            @PathVariable("fechaFin") String fechaFin) {
        try {
            List<TransaccionRS> transacciones = this.service.listarPorFecha(codTarjetaCliente, fechaInicio, fechaFin);
            if (transacciones.size() <= 0) {
                log.info("No se encontro ninguna fecha");
                return ResponseEntity.badRequest().build();
            } else {
                log.info("Transacciones obtenidas {} con los parametros: {} - {}",
                        transacciones.size(), fechaInicio, fechaFin);
                return ResponseEntity.ok(transacciones);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("{}", e.getStackTrace());
            return ResponseEntity.badRequest().build();
        }

    }

    @ApiOperation(value = "Listar últimos movimientos",
            notes = "Listar últimos movimientos antes de la fecha de corte")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK. Las transacciones se obtiene correctamente"),
        @ApiResponse(code = 400, message = "Bad Request. El formato de fecha es: yyyy-MM-dd"),
        @ApiResponse(code = 500, message = "Error inesperado del sistema")})
    @GetMapping(value = "/movimientosActuales/{codTarjetaCliente}")
    public ResponseEntity listarUltimasTransacciones(@PathVariable("codTarjetaCliente") String codTarjetaCliente) {

        try {
            List<TransaccionRS> transaccionesRS = this.service.listarUltimasTransacciones(codTarjetaCliente).getTransaccionRS();
            if (transaccionesRS.size() <= 0) {
                throw new FoundException("No se ha encontrado ningún movimiento");
            } else {
                log.info("Transacciones obtenidas {} con la tarjeta de credito: {}",
                        transaccionesRS.size(), codTarjetaCliente);
                return ResponseEntity.ok(transaccionesRS);
            }
        } catch (Exception e) {
            return Serializador.Error(e, "Error al obtener los últimos movimientos");
        }
    }

}
