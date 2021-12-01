/*
 * Copyright (c) 2021 mafer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.distribuidas.tarjetacredito.transform;

import ec.edu.espe.distribuidas.tarjetacredito.dto.TransaccionRS;
import ec.edu.espe.distribuidas.tarjetacredito.model.Transaccion;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TransaccionRSTransform {
    
    public static TransaccionRS buildTransaccionTransformRS(Transaccion transaccion) {
        LocalDateTime fecha = transaccion.getFecha().atZone(ZoneId.of("America/Chicago")).toLocalDate().atStartOfDay();

        return TransaccionRS.builder()
                .codigo(transaccion.getCodigo())
                .codigoTarjetaCliente(transaccion.getCodigoTarjetaCliente())
                .descripcion(transaccion.getDescripcion())
                .tipo(transaccion.getTipo())
                .mes(transaccion.getMeses())
                .monto(transaccion.getMonto())
                .interes(transaccion.getInteres())
                .fecha(fecha)
                .build();
    }
}