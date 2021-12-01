/*
 * Copyright (c) 2021 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package ec.edu.espe.distribuidas.tarjetacredito.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransaccionRQ {
    
    private String codTarjetaCliente;
    
    private String descripcion;
    
    private String tipo;
    
    private Integer meses;
    
    private BigDecimal monto;
    
    private BigDecimal interes;

}
