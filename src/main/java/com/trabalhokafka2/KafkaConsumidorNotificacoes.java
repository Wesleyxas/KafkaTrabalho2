import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumidorNotificacoes {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("group.id", "grupo-notificacoes");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);


        consumer.subscribe(Arrays.asList("topico-notificacoes"));


        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String[] parts = record.value().split(":");
                String destinatario = parts[0];
                String mensagem = parts[1];
                System.out.println("Notificação recebida para " + destinatario + ": " + mensagem);

            }
        }
    }
}
