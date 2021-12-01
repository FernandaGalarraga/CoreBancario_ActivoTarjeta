/*
 * Copyright (c) 2021 mafer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.distribuidas.tarjetacredito.transform;

import ec.edu.espe.distribuidas.tarjetacredito.dto.TarjetaClienteRS;
import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCliente;

public class TarjetaClienteRSTransform {
    public static TarjetaClienteRS buildTarjetaTransformRS(TarjetaCliente tarjetaCliente){
        return TarjetaClienteRS.builder()
                .codTarjetaCliente(tarjetaCliente.getCodigo())
                .codTarjetaCredito(tarjetaCliente.getCodigoTarjetaCredito())
                .codCliente(tarjetaCliente.getCodigoCliente())
                .nombreTitular(tarjetaCliente.getNombreTitular())
                .estado(tarjetaCliente.getEstado())
                .cupoDisponible(tarjetaCliente.getCupoDisponible())
                .cupoTotal(tarjetaCliente.getCupoTotal())
                .fechaExpedicion(tarjetaCliente.getFechaExpedicion())
                .fechaExpiracion(tarjetaCliente.getFechaExpiracion())
                .build();
    }
}
