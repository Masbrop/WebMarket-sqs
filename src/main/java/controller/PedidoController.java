package controller;

import lombok.AllArgsConstructor;
import model.Pedido;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.PedidoService;

@AllArgsConstructor
@RestController
@RequestMapping("/ubicacionessqs")
public class PedidoController {

    PedidoService pedidoService;

    @PostMapping("/aws")
    public Mono<String> postMessageQueue(@RequestBody Pedido ubicacion) {
        return Mono.just(pedidoService.publishStandardQueueMessage(10, ubicacion));
    }
}
