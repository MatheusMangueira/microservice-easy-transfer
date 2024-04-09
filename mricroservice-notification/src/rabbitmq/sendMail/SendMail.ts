import nodemailer from "nodemailer";

export class SendMail {

   private transporter: nodemailer.Transporter;

   constructor() {
      this.transporter = nodemailer.createTransport({
         host: process.env.mail_host,
         port: parseInt(process.env.mail_port!),
         secure: false,
         auth: {
            user: process.env.mail_user,
            pass: process.env.mail_password
         }
      })
   }

   public async messageTransfer(
      senderMail: string,
      recipientMail: string,
      value: string,
      message: string
   ) {

      try {
         await this.transporter.sendMail({
            from: senderMail,
            to: recipientMail,
            subject: 'Email enviado ',
            text: message + " no valor de: " + value
         });

         console.log('Email enviado com sucesso!');
      } catch (err) {
         console.error('Error sending mail:', err);
         throw err;
      }


   }

}