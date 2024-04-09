import ConsumerNotification from "./rabbitmq/consumer/ConsumerNotification";
import 'dotenv/config';

const consumer = new ConsumerNotification();
consumer.consume();
