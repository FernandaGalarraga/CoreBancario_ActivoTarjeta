/*
 * Copyright (c) 2021 mafer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.arqsoftware.activotarjeta.transform;

import ec.edu.espe.arqsoftware.activotarjeta.ws.TransaccionRS;
import ec.edu.espe.arqsoftware.activotarjeta.model.Transaccion;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class TransaccionRSTransform {
    
    public static TransaccionRS buildTransaccionTransformRS(Transaccion transaccion) throws DatatypeConfigurationException {
        LocalDateTime fechaLocal = transaccion.getFecha().atZone(ZoneId.of("America/New_York")).toLocalDate().atStartOfDay();
        XMLGregorianCalendar fecha = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaLocal.toString());
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
