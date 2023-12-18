package service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.AllArgsConstructor;
import model.Clientes;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class ClientesService {

    private final AmazonSQS sqsClient;

    private String getQueueUrl() {
        return sqsClient.getQueueUrl("comentario-sqs").getQueueUrl();
    }

    public String publishStandardQueueMessage(Integer delaySeconds, Clientes comentario) {
        Map<String, MessageAttributeValue> atributosMensaje = new HashMap<>();
        atributosMensaje.put("id",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(comentario.id()))
                        .withDataType("String")
        );
        atributosMensaje.put("nombre",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(comentario.nombre()))
                        .withDataType("String")
        );
        atributosMensaje.put("telefono",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(comentario.evento()))
                        .withDataType("String")
        );
        atributosMensaje.put("direccion",
                new MessageAttributeValue()
                        .withStringValue(comentario.direccion())
                        .withDataType("String")
        );
        atributosMensaje.put("fecha",
                new MessageAttributeValue()
                        .withStringValue(comentario.fecha())
                        .withDataType("String")
        );

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(comentario.direccion())
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(atributosMensaje);

        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
