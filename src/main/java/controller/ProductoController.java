package controller;

import lombok.AllArgsConstructor;
import model.Producto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import service.ProductoService;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario-sqs")
public class ProductoController {

    ProductoService productoService;

    @PostMapping("/aws")
    public Mono<String> postMessageQueue(@RequestBody Producto usuario) {
        return Mono.just(productoService.publishStandardQueueMessage(10, usuario));
    }
}