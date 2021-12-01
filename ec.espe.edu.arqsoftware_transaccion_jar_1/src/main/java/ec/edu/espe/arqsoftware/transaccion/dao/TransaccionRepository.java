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
package ec.edu.espe.arqsoftware.transaccion.dao;

import ec.edu.espe.arqsoftware.transaccion.model.Transaccion;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {
    List<Transaccion> findByCodigoTarjetaClienteAndFechaBetween(String codigo, LocalDateTime inicio, LocalDateTime fin);
   
    List<Transaccion> findByFechaBetween(Date inicio, Date fin);
}
