
import amqp from "amqplib";
import { SendMail } from "../sendMail/SendMail";

export class RabbitmqConfig {
   private host: string;
   private port: number;
   private username: string;
   private password: string;
   private queue: string;

   private sendMail: SendMail;

   constructor(host: string, port: number, username: string, password: string, queue: string) {
      this.host = host;
      this.port = port;
      this.username = username;
      this.password = password;
      this.queue = queue;

      this.sendMail = new SendMail();
   }


   public async connect() {
      try {
         const connection = await amqp.connect({
            hostname: this.host,
            port: this.port,
            username: this.username,
            password: this.password
         });

         const channel = await connection.createChannel();
         await channel.assertQueue(this.queue, { durable: true });
         channel.consume(this.queue, async (message) => {
            if (message && message.content) {
               try {
                  const messageContent = message.content.toString();
                  const parsedMessage = JSON.parse(messageContent);

                  const { message: emailMessage, recipientMail, value, senderMail } = parsedMessage;

                  await this.sendMail.messageTransfer(senderMail, recipientMail, value.toString(), emailMessage);

                  console.log(messageContent + ' =======enviado para======= ');

               } catch (err) {
                  console.error('Erro ao analisar a mensagem JSON:', err);

               }

            }
         }, { noAck: true });


      } catch (err) {
         console.error('Error connecting to RabbitMQ:', err);
         throw err;
      }
   }

}

