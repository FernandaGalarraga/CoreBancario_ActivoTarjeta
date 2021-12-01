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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;

@Builder
@Data
public class TarjetaActivoRS {
    private String codTarjetaCliente;
    private String codTarjetaCredito;
    private String codCliente;
    private String emisor;
    private String estado;
    private String numeroTarjeta;
    private BigDecimal cupoDisponible;
    private BigDecimal cupoTotal;
    private LocalDateTime fechaExpedicion;
    private LocalDate fechaExpiracion;
}
