package controller;

import lombok.AllArgsConstructor;
import model.Clientes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.ClientesService;

@AllArgsConstructor
@RestController
@RequestMapping("/comentario-sqs")
public class ClientesController {

    ClientesService clientesService;

    @PostMapping("/aws")
    public Mono<String> postMessageQueue(@RequestBody Clientes comentario) {
        return Mono.just(clientesService.publishStandardQueueMessage(10, comentario));
    }
}
