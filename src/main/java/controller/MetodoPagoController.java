package controller;

import lombok.AllArgsConstructor;
import model.MetodoPago;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.MetodoPagoService;

@AllArgsConstructor
@RestController
@RequestMapping("/evento-sqs")
public class MetodoPagoController {

    MetodoPagoService metodoPagoService;

    @PostMapping("/aws")
    public Mono<String> postMessageQueue(@RequestBody MetodoPago evento) {
        return Mono.just(metodoPagoService.publishStandardQueueMessage(10, evento));
    }
}
