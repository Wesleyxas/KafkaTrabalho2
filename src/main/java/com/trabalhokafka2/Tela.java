import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaNotificacoes extends JFrame {

    private JTextArea areaNotificacoes;

    public TelaNotificacoes() {
        setTitle("Sistema de Notificações");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        areaNotificacoes = new JTextArea();
        areaNotificacoes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaNotificacoes);
        add(scrollPane, BorderLayout.CENTER);

        JButton botaoEnviar = new JButton("Enviar Notificação");
        botaoEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Corrigido: Usando setText() para definir o texto da área de texto
                areaNotificacoes.setText("Notificação enviada!\n");
            }
        });
        add(botaoEnviar, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaNotificacoes();
            }
        });
    }
}
