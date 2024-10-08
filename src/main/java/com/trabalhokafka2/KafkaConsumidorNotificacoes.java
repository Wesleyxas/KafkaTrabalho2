package com.trabalhokafka2;

public class KafkaConsumidorNotificacoes {

    import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;

    @Component
    public class KafkaConsumidorNotificacoes {

        @KafkaListener(topics = "topico-notificacoes", groupId = "grupo-notificacoes")
        public void listen(ConsumerRecord<String, String> record) {
            String[] parts = record.value().split(":");
            String destinatario = parts[0];
            String mensagem = parts[1];
            System.out.println("Notificação recebida para " + destinatario + ": " + mensagem);
        }
    }

}
