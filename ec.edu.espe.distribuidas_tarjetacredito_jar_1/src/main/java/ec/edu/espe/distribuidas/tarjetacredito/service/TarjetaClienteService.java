/*
 * Copyright (c) 2021 mafer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    mafer - initial API and implementation and/or initial documentation
 */
package ec.edu.espe.distribuidas.tarjetacredito.service;

import ec.edu.espe.distribuidas.tarjetacredito.dao.TarjetaClienteRepository;
import ec.edu.espe.distribuidas.tarjetacredito.dao.TarjetaCreditoRepository;
import ec.edu.espe.distribuidas.tarjetacredito.dao.TransaccionRepository;
import ec.edu.espe.distribuidas.tarjetacredito.exception.CreateException;
import ec.edu.espe.distribuidas.tarjetacredito.exception.FoundException;
import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCliente;
import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCredito;
import ec.edu.espe.distribuidas.tarjetacredito.model.Transaccion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TarjetaClienteService {

    private final TarjetaClienteRepository tarjetaClienteRepo;
    private final TarjetaCreditoRepository tarjetaCreditoRepo;
    private final TransaccionRepository transaccionRepo;

    public TarjetaClienteService(TarjetaClienteRepository tarjetaClienteRepo, TarjetaCreditoRepository tarjetaCreditoRepo, TransaccionRepository transaccionRepo) {
        this.tarjetaClienteRepo = tarjetaClienteRepo;
        this.tarjetaCreditoRepo = tarjetaCreditoRepo;
        this.transaccionRepo = transaccionRepo;
    }

    @Transactional
    public String crearTarjetaCliente(TarjetaCliente tarjetaCliente) {
        Optional<TarjetaCredito> creditoOpt = this.tarjetaCreditoRepo.findById(tarjetaCliente.getCodigoTarjetaCredito());
        LocalDateTime fechaExpedicion = LocalDateTime.now(ZoneId.of("America/Chicago")).withNano(0);
        LocalDate fechaExpiracion = fechaExpedicion.toLocalDate();
        if (creditoOpt.isPresent()) {
            tarjetaCliente.setCodigo(this.codigoTarjetaCliente());
            tarjetaCliente.setTarjetaCredito(creditoOpt.get());
            if (obtenerPorCodigo(tarjetaCliente.getCodigo()).isEmpty()) {
                tarjetaCliente.setNumeroTarjeta(this.numeroTarjeta(creditoOpt.get().getEmisor()));
                if (obtenerPorNumeroTarjeta(tarjetaCliente.getNumeroTarjeta()) == null) {
                    if (obtenerPorClienteYTarjeta(tarjetaCliente.getCodigoCliente(), tarjetaCliente.getCodigoTarjetaCredito()) == null) {
                        tarjetaCliente.setEstado("ACT");
                        tarjetaCliente.setCupoDisponible(creditoOpt.get().getCupoInicial());
                        tarjetaCliente.setPin(this.generarPin());
                        tarjetaCliente.setCodigoVerificador(this.codigoVerificador(""));
                        tarjetaCliente.setFechaExpedicion(fechaExpedicion);
                        tarjetaCliente.setFechaExpiracion(fechaExpiracion.plusYears(5));
                        tarjetaCliente.setCupoTotal(creditoOpt.get().getCupoInicial());
                        return this.tarjetaClienteRepo.save(tarjetaCliente).getCodigo();
                    
                    } else {
                        throw new CreateException("El cliente con codigo: "
                                + tarjetaCliente.getCodigoCliente()
                                + " ya posee una tarjeta de credito con codigo: "
                                + tarjetaCliente.getCodigoTarjetaCredito());
                    }
                } else {
                    log.info("Ya existe una tarjeta cliente con el numero: {} ", tarjetaCliente.getNumeroTarjeta());
                    tarjetaCliente.setNumeroTarjeta(this.numeroTarjeta(creditoOpt.get().getEmisor()));
                    tarjetaCliente.setEstado("ACT");
                    tarjetaCliente.setCupoDisponible(creditoOpt.get().getCupoInicial());
                    tarjetaCliente.setPin(this.generarPin());
                    tarjetaCliente.setCodigoVerificador(this.codigoVerificador(""));
                    tarjetaCliente.setFechaExpedicion(fechaExpedicion);
                    tarjetaCliente.setFechaExpiracion(fechaExpiracion.plusYears(5));
                    tarjetaCliente.setCupoTotal(creditoOpt.get().getCupoInicial());
                    return this.tarjetaClienteRepo.save(tarjetaCliente).getCodigo();
                }

            } else {
                log.info("Ya existe una tarjeta cliente con codigo: {} ", tarjetaCliente.getCodigo());
                tarjetaCliente.setCodigo(this.codigoTarjetaCliente());
                tarjetaCliente.setNumeroTarjeta(this.numeroTarjeta(creditoOpt.get().getEmisor()));
                tarjetaCliente.setEstado("ACT");
                tarjetaCliente.setCupoDisponible(creditoOpt.get().getCupoInicial());
                tarjetaCliente.setPin(this.generarPin());
                tarjetaCliente.setCodigoVerificador(this.codigoVerificador(""));
                tarjetaCliente.setFechaExpedicion(fechaExpedicion);
                tarjetaCliente.setFechaExpiracion(fechaExpiracion.plusYears(5));
                tarjetaCliente.setCupoTotal(creditoOpt.get().getCupoInicial());
                return this.tarjetaClienteRepo.save(tarjetaCliente).getCodigo();
            }
        } else {
            throw new CreateException("La tarjeta de credito con codigo: "
                    + tarjetaCliente.getCodigoTarjetaCredito()
                    + " no se ha encontrado en la base de datos");
        }
    }

    public Optional<TarjetaCliente> obtenerPorCodigo(String codigo) {
        return this.tarjetaClienteRepo.findById(codigo);
    }

    public List<TarjetaCliente> obtenerTarjetaCliente() {
        return this.tarjetaClienteRepo.findAll();
    }

    public List<Transaccion> obtenerMovimientosActuales(String codTarjetaCliente) {
        Calendar fechaFin = Calendar.getInstance();
        Calendar fechaInicio = Calendar.getInstance();
        Optional<TarjetaCliente> tarjetaClienteOpt = this.tarjetaClienteRepo.findById(codTarjetaCliente);
        if (tarjetaClienteOpt.isPresent()) {
            fechaFin.set(Calendar.DAY_OF_MONTH, tarjetaClienteOpt.get().getTarjetaCredito().getFechaCorte());
            fechaInicio.add(Calendar.MONTH, -1);
            fechaFin.add(Calendar.DAY_OF_WEEK, -1);
            fechaInicio.set(Calendar.DAY_OF_MONTH, tarjetaClienteOpt.get().getTarjetaCredito().getFechaCorte());
        } else {
            throw new RuntimeException("No se encontro ninguna tarjeta con el codigo: " + codTarjetaCliente);
        }
        LocalDateTime ldInicio = fechaInicio.toInstant().atZone(ZoneId.of("America/Chicago")).toLocalDate().atStartOfDay();
        LocalDateTime ldFin = fechaFin.toInstant().atZone(ZoneId.of("America/Chicago")).toLocalDate().atTime(23, 59, 59);
        log.info("Va a buscar transacciones desde: {} hasta: {}, de la cuenta {}", ldInicio, ldFin, codTarjetaCliente);
        return this.transaccionRepo.findByCodigoTarjetaClienteAndFechaBetween(
                codTarjetaCliente, ldInicio, ldFin);
    }

    public TarjetaCliente obtenerPorClienteYTarjeta(String cliente, String tarjeta) {
        return this.tarjetaClienteRepo.findByCodigoClienteAndCodigoTarjetaCredito(cliente, tarjeta);
    }

    public List<TarjetaCliente> obtenerPorCodigoCliente(String codigo) {
        if (codigo != null) {
            List<TarjetaCliente> tarjetaCliente = this.tarjetaClienteRepo.findByCodigoClienteOrderByCodigoTarjetaCreditoAsc(codigo);
            tarjetaCliente.stream().filter(c -> ("INA".equals(c.getEstado()))).forEachOrdered(c -> {
                tarjetaCliente.remove(c);
            });
            return tarjetaCliente;

        } else {
            throw new FoundException("No se ha encontrado ningún movimiento con el código de tarjeta: " + codigo);
        }
    }

    public TarjetaCliente obtenerPorNumeroTarjeta(String nroTarjeta) {
        return this.tarjetaClienteRepo.findByNumeroTarjeta(nroTarjeta);
    }

    public String numeroTarjeta(String emisor) {
        Integer digId = 0;
        Integer len = 0;
        if ("VIS".equals(emisor)) {
            digId = 4;
            len = 16;
        } else if ("AME".equals(emisor)) {
            digId = (int) (Math.random() * (38 - 34)) + 34;
            len = 15;
        } else if ("MAS".equals(emisor)) {
            digId = (int) (Math.random() * (56 - 51)) + 51;
            len = 16;
        } else {
            digId = (int) (Math.random() * (306 - 300)) + 300;
            len = 14;
        }
        Integer randomNumberLength = len - (digId.toString().length());

        StringBuilder sb = new StringBuilder(digId.toString());

        for (int i = 0; i < randomNumberLength - 1; i++) {
            Integer digit = (int) (Math.random() * 10);
            sb.append(digit);
        }
        Integer verificar = this.digitoVerificador(sb.toString());
        sb.append(verificar);
        return sb.toString();
    }

    public int digitoVerificador(String numeroTarjeta) {
        StringBuilder n = new StringBuilder(numeroTarjeta).reverse();
        Integer suma = 0;
        for (int i = 0; i < n.length(); i++) {
            Integer digito = Integer.parseInt(n.substring(i, (i + 1)));
            if ((i % 2) != 0) {
                digito *= 2;
                if (digito > 9) {
                    digito = digito - 9;
                }
            }
            suma += digito;
        }
        return suma % 10;
    }

    public String codigoVerificador(String codigo) {
        Integer longitud = 7;
        StringBuilder sb = new StringBuilder(codigo);
        for (int i = 0; i < longitud; i++) {
            Integer codigoV = (int) (Math.random() * 10);
            sb.append(codigoV);
        }
        return sb.toString();
    }

    public String codigoTarjetaCliente() {
        String cadena = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * cadena.length());
            salt.append(cadena.charAt(index));
        }
        return salt.toString();
    }

    public String generarPin() {
        StringBuilder pin = new StringBuilder();
        while (pin.length() < 4) {
            Integer digito = (int) (Math.random() * 10);
            pin.append(digito);
        }
        return pin.toString();
    }
}
