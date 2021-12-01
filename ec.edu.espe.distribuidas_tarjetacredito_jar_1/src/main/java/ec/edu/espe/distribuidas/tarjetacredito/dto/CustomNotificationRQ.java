/*
 * Copyright (c) 2021 yazbe.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    yazbe - initial API and implementation and/or initial documentation
 */
package ec.edu.espe.distribuidas.tarjetacredito.dto;

import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCliente;
import ec.edu.espe.distribuidas.tarjetacredito.model.Transaccion;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomNotificationRQ {

    private String email;
    private String message;
    private String subject;

    public static CustomNotificationRQ buildTransaccion(ClienteDto clienteDto, Transaccion transaccion, TarjetaCliente tarjeta) {
        CustomNotificationRQ customNotification = new CustomNotificationRQ();
        customNotification.setEmail(clienteDto.getEmail());
        customNotification.setSubject("Se ha realizado una transacción con su tarjeta");
        
        String tipo="";
        switch(transaccion.getTipo()){
            case "CON":
                tipo="consumo";
                break;
            case "DIF":
                tipo="consumo";
                break;
            case "PAG":
                tipo="pago";
                break;
        }
        String mensaje="<html>Estimado(a) "+clienteDto.getNombre1() + " " + clienteDto.getNombre2() + " "
                + clienteDto.getApellidoMaterno() + " " + clienteDto.getApellidoPaterno() 
                +" ha realizado un "+tipo+" con su tarjeta " + tarjeta.getNumeroTarjeta().substring(0, 10)
                +"XXXXX"
                + ", con un valor de " + transaccion.getMonto()
                +", el día " + transaccion.getFecha().toLocalDate()
                +" a las " + transaccion.getFecha().getHour() + ":" + transaccion.getFecha().getMinute()
                +"</html>";
                
        customNotification.setMessage(mensaje);
        return customNotification;
    }
}
