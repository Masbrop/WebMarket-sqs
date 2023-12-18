package service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.AllArgsConstructor;
import model.Pedido;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class PedidoService {

    private final AmazonSQS sqsClient;

    private String getQueueUrl() {
        return sqsClient.getQueueUrl("ubicacionessqs").getQueueUrl();
    }

    public String publishStandardQueueMessage(Integer delaySeconds, Pedido ubicacion) {
        Map<String, MessageAttributeValue> atributosMensaje = new HashMap<>();
        atributosMensaje.put("id",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(ubicacion.id()))
                        .withDataType("String")
        );
        atributosMensaje.put("nombre",
                new MessageAttributeValue()
                        .withStringValue(ubicacion.nombre())
                        .withDataType("String")
        );
        atributosMensaje.put("cantidad",
                new MessageAttributeValue()
                        .withStringValue(ubicacion.direccion())
                        .withDataType("String")
        );

        atributosMensaje.put("direccion",
                new MessageAttributeValue()
                        .withStringValue(ubicacion.ciudad())
                        .withDataType("String")
        );

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(ubicacion.nombre())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(atributosMensaje);

        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
