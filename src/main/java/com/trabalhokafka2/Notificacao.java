public class Notificacao {
    private String destinatario;
    private String mensagem;
    private Prioridade prioridade;

    public Notificacao(String destinatario, String mensagem, Prioridade prioridade) {
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.prioridade = prioridade;
    }


    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "destinatario='" + destinatario + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", prioridade=" + prioridade +
                '}';
    }
}
