import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NotificacaoKafkaEArquivo {

    private static final int BATCH_SIZE = 10;
    private static final long TIMEOUT_MS = 5000;
    private static final String PASTA_ARQUIVOS = "notificacoes";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        BlockingQueue<String> notificacoesBuffer = new LinkedBlockingQueue<>();

        // Adiciona notificações ao buffer
        enviarNotificacao(notificacoesBuffer, "aluno1", "Lembrete: Trabalho de Java devido amanhã!", Prioridade.ALTA);
        enviarNotificacao(notificacoesBuffer, "professor2", "Mudança de horário da aula de Java para as 14h", Prioridade.BAIXA);
        enviarNotificacao(notificacoesBuffer, "aluno3", "Palestra sobre Inteligência Artificial amanhã às 10h", Prioridade.ALTA);

        // Envia as notificações em lote
        enviarLote(producer, notificacoesBuffer);

        producer.close();
    }

    private static void enviarNotificacao(BlockingQueue<String> buffer, String destinatario, String mensagem, Prioridade prioridade) {
        String notificacao = destinatario + ":" + mensagem + " (Prioridade: " + prioridade + ")\n";

        try {
            buffer.put(notificacao);
            gravarNoArquivo(notificacao);
        } catch (InterruptedException e) {
            System.err.println("Erro ao adicionar notificação ao buffer: " + e.getMessage());
        }
    }

    private static void gravarNoArquivo(String notificacao) {
        try {
            Path pasta = Path.of(PASTA_ARQUIVOS);
            Files.createDirectories(pasta);
            FileWriter writer = new FileWriter(pasta.resolve("notificacoes.txt"), true);
            writer.write(notificacao);
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao gravar notificação no arquivo: " + e.getMessage());
        }
    }

    private static void enviarLote(KafkaProducer<String, String> producer, BlockingQueue<String> buffer) {
        List<String> notificacoes = new ArrayList<>();
        try {
            buffer.drainTo(notificacoes, BATCH_SIZE, TIMEOUT_MS, TimeUnit.MILLISECONDS);

            for (String notificacao : notificacoes) {
                ProducerRecord<String, String> record = new ProducerRecord<>("topico-notificacoes", notificacao);
                producer.send(record);
            }
        } catch (InterruptedException e) {
            System.err.println("Erro ao enviar lote para o Kafka: " + e.getMessage());
        }
    }
}

enum Prioridade {
    ALTA, BAIXA
}
