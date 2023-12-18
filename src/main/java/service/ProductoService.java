package service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.AllArgsConstructor;
import model.Producto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductoService {

    private final AmazonSQS sqsClient;

    private String getQueueUrl() {
        return sqsClient.getQueueUrl("usuarios-sqs").getQueueUrl();
    }

    public String publishStandardQueueMessage(Integer delaySeconds, Producto usuario) {
        Map<String, MessageAttributeValue> atributosMensaje = new HashMap<>();
        atributosMensaje.put("id",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(usuario.id()))
                        .withDataType("String")
        );
        atributosMensaje.put("nombre",
                new MessageAttributeValue()
                        .withStringValue(usuario.nombre())
                        .withDataType("String")
        );
        atributosMensaje.put("categoria",
                new MessageAttributeValue()
                        .withStringValue(usuario.categoria())
                        .withDataType("String")
        );

        atributosMensaje.put("valor",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(usuario.valor()))
                        .withDataType("String")
        );

        atributosMensaje.put("ciudad",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(usuario.ciudad()).orElse(Boolean.FALSE).toString())
                        .withDataType("String")
        );

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(usuario.nombre())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(atributosMensaje);

        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
