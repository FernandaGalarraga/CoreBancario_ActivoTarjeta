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
package ec.edu.espe.distribuidas.tarjetacredito.transform;

import ec.edu.espe.distribuidas.tarjetacredito.dto.TarjetaActivoRS;
import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCliente;

public class TarjetaActivoRSTransform {
    public static TarjetaActivoRS buildTransformRS(TarjetaCliente tarjetaCliente){
        return TarjetaActivoRS.builder()
                .codTarjetaCliente(tarjetaCliente.getCodigo())
                .codTarjetaCredito(tarjetaCliente.getCodigoTarjetaCredito())
                .codCliente(tarjetaCliente.getCodigoCliente())
                .emisor(tarjetaCliente.getTarjetaCredito().getEmisor())
                .estado(tarjetaCliente.getEstado())
                .numeroTarjeta(tarjetaCliente.getNumeroTarjeta())
                .cupoDisponible(tarjetaCliente.getCupoDisponible())
                .cupoTotal(tarjetaCliente.getCupoTotal())
                .fechaExpedicion(tarjetaCliente.getFechaExpedicion())
                .fechaExpiracion(tarjetaCliente.getFechaExpiracion())
                .build();
    }
}
