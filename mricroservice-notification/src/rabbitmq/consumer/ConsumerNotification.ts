import { RabbitmqConfig } from "../config/RabbitmqConfig";



export default class ConsumerNotification {
   private rabbitmqConfig: RabbitmqConfig;


   constructor() {
      this.rabbitmqConfig = new RabbitmqConfig(
         process.env.rabbitmq_host!,
         parseInt(process.env.rabbitmq_port!),
         process.env.rabbitmq_username!,
         process.env.rabbitmq_password!,
         'notification-row');
   }

   public async consume() {
      try {
         await this.rabbitmqConfig.connect();
      } catch (err) {
         console.error('Error starting consumer:', err);
      }
   }

}