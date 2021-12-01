/*
 * Copyright (c) 2021 mafer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.arqsoftware.activotarjeta.transform;

import ec.edu.espe.arqsoftware.activotarjeta.ws.TarjetaClienteRS;
import ec.edu.espe.arqsoftware.activotarjeta.model.TarjetaCliente;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class TarjetaClienteRSTransform {

    public static TarjetaClienteRS buildTarjetaTransformRS(TarjetaCliente tarjetaCliente) throws DatatypeConfigurationException {
        LocalDateTime fechaExp = tarjetaCliente.getFechaExpedicion();
        LocalDate fechaExpi = tarjetaCliente.getFechaExpiracion();
        XMLGregorianCalendar fechaExpedicion = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaExp.toString());
        XMLGregorianCalendar fechaExpiracion = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaExpi.toString());
        return TarjetaClienteRS.builder()
                .codTarjetaCliente(tarjetaCliente.getCodigo())
                .codTarjetaCredito(tarjetaCliente.getCodigoTarjetaCredito())
                .codCliente(tarjetaCliente.getCodigoCliente())
                .nombreTitular(tarjetaCliente.getNombreTitular())
                .estado(tarjetaCliente.getEstado())
                .cupoDisponible(tarjetaCliente.getCupoDisponible())
                .cupoTotal(tarjetaCliente.getCupoTotal())
                .fechaExpedicion(fechaExpedicion)
                .fechaExpiracion(fechaExpiracion)
                .build();
    }
}
