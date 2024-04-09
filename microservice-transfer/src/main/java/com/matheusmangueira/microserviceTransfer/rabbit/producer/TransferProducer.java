package com.matheusmangueira.microserviceTransfer.rabbit.producer;

import com.matheusmangueira.microserviceTransfer.services.TransferService;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;


@Component
public class TransferProducer {

  private static final String NAME_EXCHANGE = "amq.direct";

  private AmqpAdmin amqpAdmin;

  public TransferProducer(AmqpAdmin amqpAdmin) {
    this.amqpAdmin = amqpAdmin;
  }

  private Queue row(String rowName) {
    return new Queue(rowName, true, false, false);
  }

  private DirectExchange exchange() {
    return new DirectExchange(NAME_EXCHANGE);
  }

  private Binding bind(Queue row, DirectExchange exchange) {
    return new Binding(
        row.getName(),
        Binding.DestinationType.QUEUE,
        exchange.getName(),
        row.getName(),
        null);
  }

  @PostConstruct
  public void send() {
    Queue row = row("notification-row");

    DirectExchange exchange = exchange();

    Binding relation = bind(row, exchange);

    //criando fila no RabbitMQ
    this.amqpAdmin.declareQueue(row);
    //criando exchange no RabbitMQ
    this.amqpAdmin.declareExchange(exchange);
    //criando relação fila-exchange no RabbitMQ
    this.amqpAdmin.declareBinding(relation);


  }
}
