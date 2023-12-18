package service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.AllArgsConstructor;
import model.MetodoPago;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MetodoPagoService {

    private final AmazonSQS sqsClient;

    private String getQueueUrl() {
        return sqsClient.getQueueUrl("eventos-sqs").getQueueUrl();
    }

    public String publishStandardQueueMessage(Integer delaySeconds, MetodoPago evento) {
        Map<String, MessageAttributeValue> atributosMensaje = new HashMap<>();
        atributosMensaje.put("id",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(evento.id()))
                        .withDataType("String")
        );
        atributosMensaje.put("tipo",
                new MessageAttributeValue()
                        .withStringValue(evento.tipo())
                        .withDataType("String")
        );
        atributosMensaje.put("banco",
                new MessageAttributeValue()
                        .withStringValue(evento.banco())
                        .withDataType("String")
        );
        atributosMensaje.put("fecha",
                new MessageAttributeValue()
                        .withStringValue(evento.fecha())
                        .withDataType("String")
        );
        atributosMensaje.put("capacidad",
                new MessageAttributeValue()
                        .withStringValue(Optional.ofNullable(evento.capacidad()).orElse(0).toString())
                        .withDataType("String")
        );

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(evento.tipo())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(atributosMensaje);

        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
