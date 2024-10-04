import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class NotificacaoKafkaEArquivo {

    public static void main(String[] args) {


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);


        String arquivoNotificacoes = "notificacoes.txt";


        enviarNotificacao(producer, arquivoNotificacoes, "aluno1", "Lembrete: Trabalho de Java devido amanhã!", Prioridade.ALTA);
        enviarNotificacao(producer, arquivoNotificacoes, "professor2", "Mudança de horário da aula de Java para as 14h", Prioridade.BAIXA);
        enviarNotificacao(producer, arquivoNotificacoes, "aluno3", "Palestra sobre Inteligência Artificial amanhã às 10h", Prioridade.ALTA);


        producer.close();

        System.out.println("Notificações enviadas para o Kafka e gravadas no arquivo: " + arquivoNotificacoes);
    }

    private static void enviarNotificacao(KafkaProducer<String, String> producer, String arquivo, String destinatario, String mensagem, Prioridade prioridade) {
        String notificacao = destinatario + ":" + mensagem + " (Prioridade: " + prioridade + ")\n";


        ProducerRecord<String, String> record = new ProducerRecord<>("topico-notificacoes", notificacao);
        producer.send(record);
        System.out.println("Notificação enviada para o Kafka: " + notificacao);


        try (FileWriter writer = new FileWriter(arquivo, true)) {
            writer.write(notificacao);
            System.out.println("Notificação gravada no arquivo: " + notificacao);
        } catch (IOException e) {
            System.err.println("Erro ao gravar notificação no arquivo: " + e.getMessage());
        }
    }
}


enum Prioridade {
    ALTA, BAIXA
}
