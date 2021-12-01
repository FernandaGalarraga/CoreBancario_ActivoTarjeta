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
package ec.edu.espe.distribuidas.tarjetacredito.dto;

import ec.edu.espe.distribuidas.tarjetacredito.model.TarjetaCliente;
import ec.edu.espe.distribuidas.tarjetacredito.model.Transaccion;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ClienteTransaccionDto {
    
    private String cuentaId;

    private String descripcion;

    private String tipo;

    private BigDecimal monto;
    
    public static ClienteTransaccionDto buildTransaccion(Transaccion transaccion, TarjetaCliente tarjeta){
        ClienteTransaccionDto clienteTransaccion = new ClienteTransaccionDto();
            clienteTransaccion.setCuentaId("0000000000");
            clienteTransaccion.setDescripcion("Se ha pagado "+transaccion.getMonto() +" a la tarjeta "+
                    tarjeta.getTarjetaCredito().getNombre());
            clienteTransaccion.setTipo("DEP");
            clienteTransaccion.setMonto(transaccion.getMonto());
            return clienteTransaccion;
    }
}
