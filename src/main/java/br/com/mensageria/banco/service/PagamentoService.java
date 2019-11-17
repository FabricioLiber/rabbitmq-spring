package br.com.mensageria.banco.service;

import br.com.mensageria.banco.web.rest.dto.PagamentoRequest;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.json.JSONWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Connection;

@Service
public class PagamentoService {

    @Value("${queue.master}")
    private String FILA_MASTER;

    @Value("${queue.visa}")
    private String FILA_VISA;

    @Value("${queue.response.approve}")
    private String FILA_APROVADOS;

    @Value("${queue.response.decline}")
    private String FILA_REPROVADOS;

    public Boolean enviarPagamento(PagamentoRequest pagamentoRequest) {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        //localizacao do gestor da fila (Queue Manager)
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        try {
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();

            Gson gson = new Gson();
            String mensagem = gson.toJson(pagamentoRequest);

            String fila = null;
            if (pagamentoRequest.getBandeira().equalsIgnoreCase("master"))
                fila = FILA_MASTER;
            else
                fila = FILA_VISA;
            channel.queueDeclare(fila, true, false, false, null);
            channel.basicPublish("", fila, null, mensagem.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
