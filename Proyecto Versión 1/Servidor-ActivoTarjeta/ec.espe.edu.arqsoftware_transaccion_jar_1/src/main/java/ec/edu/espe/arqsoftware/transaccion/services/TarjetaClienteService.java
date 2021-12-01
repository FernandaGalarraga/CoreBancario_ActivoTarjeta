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
package ec.edu.espe.arqsoftware.transaccion.services;

import ec.edu.espe.arqsoftware.transaccion.dao.TarjetaClienteRepository;
import ec.edu.espe.arqsoftware.transaccion.dao.TarjetaCreditoRepository;
import ec.edu.espe.arqsoftware.transaccion.dao.TransaccionRepository;
import ec.edu.espe.arqsoftware.transaccion.exception.CreateException;
import ec.edu.espe.arqsoftware.transaccion.exception.FoundException;
import ec.edu.espe.arqsoftware.transaccion.model.TarjetaCliente;
import ec.edu.espe.arqsoftware.transaccion.model.TarjetaCredito;
import ec.edu.espe.arqsoftware.transaccion.model.Transaccion;
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


    public TarjetaClienteService(TarjetaClienteRepository tarjetaClienteRepo) {
        this.tarjetaClienteRepo = tarjetaClienteRepo;
    }

    public List<TarjetaCliente> obtenerTarjetaCliente() {
        return this.tarjetaClienteRepo.findAll();
    }

    
}
