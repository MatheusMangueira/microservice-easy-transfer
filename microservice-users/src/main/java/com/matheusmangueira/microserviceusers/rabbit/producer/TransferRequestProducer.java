package com.matheusmangueira.microserviceusers.rabbit.producer;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

@Component
public class TransferRequestProducer {
  private static final String NAME_EXCHANGE = "amq.direct";

  private AmqpAdmin amqpAdmin;

  public TransferRequestProducer(AmqpAdmin amqpAdmin) {
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
    Queue row = row("transfer-row");
    Queue rowNotification = row("notification-row");

    DirectExchange exchange = exchange();

    Binding relation = bind(row, exchange);
    Binding relationNotification = bind(rowNotification, exchange);

    //criando fila no RabbitMQ
    this.amqpAdmin.declareQueue(row);
    this.amqpAdmin.declareQueue(rowNotification);
    //criando exchange no RabbitMQ
    this.amqpAdmin.declareExchange(exchange);
    //criando relação fila-exchange no RabbitMQ
    this.amqpAdmin.declareBinding(relation);
    this.amqpAdmin.declareBinding(relationNotification);
  }


}
