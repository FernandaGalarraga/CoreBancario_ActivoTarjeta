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
package ec.edu.espe.distribuidas.tarjetacredito.service;

import ec.edu.espe.distribuidas.tarjetacredito.dto.ClienteDto;
import ec.edu.espe.distribuidas.tarjetacredito.dto.ClienteTransaccionDto;
import ec.edu.espe.distribuidas.tarjetacredito.dto.CustomNotificationRQ;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Data
@Slf4j
public class WebClienteService {
    
    @NonNull
    private final String notificationServiceURL = "http://52.151.203.48:8200/api";
    private final String clientExternalServiceURL = "http://52.234.160.244:8002/api";
    private final String accountServiceURL = "http://52.146.55.208:8004/api";
    
    
    public CustomNotificationRQ sendNotification(CustomNotificationRQ transaccion) {
        WebClient client = WebClient.create();
        return  client.post()
                .uri(notificationServiceURL + "/v1/notifications/custom/notify/")
                .body(Mono.just(transaccion), CustomNotificationRQ.class)
                .retrieve()
                .bodyToMono(CustomNotificationRQ.class)
                .block();
    }
    
    public ClienteTransaccionDto depositar(ClienteTransaccionDto transaccion) {
        WebClient client = WebClient.create();
        return  client.post()
                .uri(accountServiceURL + "/transaccion")
                .body(Mono.just(transaccion), ClienteTransaccionDto.class)
                .retrieve()
                .bodyToMono(ClienteTransaccionDto.class)
                .block();
    }
    
    
    public ClienteDto readClient(String id){
        WebClient client = WebClient.create();

        ClienteDto response = client.get()
                .uri(clientExternalServiceURL + "/cliente/" + id)
                .retrieve()
                .bodyToMono(ClienteDto.class)
                .block();

        return response;
    }
}
