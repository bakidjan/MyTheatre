package im2ag.m2cci.p01.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Classe utilitaire pour envoyer des courriels en utilisant l'API java mail.
 * 
 * @author Philippe.Genoud@imag.fr
 */
public class MailSender {

    /**
     * Envoie un courriel  avec un fichier attaché
     * @param session  la session mail utilisée pour envoyer les courriels
     * @param from l'adresse de courrier électronique utilisée comme paramètre de: (from)  du courriel
     * @param to l'adresse de courrier électronique à laquelle est envoyé le courriel
     * @param subject sujet du courriel
     * @param messageBody corps du message
     * @param fileName nom du fichier attaché
     * @param fileData byte array contenant les données du fichier
     * @param fileMimeType type Mime du fichier
     * 
     * @throws MessagingException si un problème intervient lors de l'envoie du mail
     */
    public static void sendMailWithAttachedFile(Session session, String from,
            String to, String subject, String messageBody,
            String fileName, byte[] fileData, String fileMimeType)
            throws MessagingException {

        // créee un message avec l'expéditeur et le destinataire du message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(subject);

        // création d'un nouvel objet MimeBodyPart qui contient le texte du message       
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setText(messageBody);

        // création d'un nouvel objet MimeBodyPart qui contient le fichier attaché     
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource(fileData, fileMimeType);
        messageBodyPart2.setDataHandler(new DataHandler(source));
        messageBodyPart2.setFileName(fileName);

        // création d'un nouvel objet Multipart object et ajout des objets MimeBodyPart
        // à celui-ci       
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart1);
        multipart.addBodyPart(messageBodyPart2);

        // fixe le contenue de l'objet message avec l'objet Multipart   
        message.setContent(multipart);

        // envoyer le message    
        Transport.send(message);
    }
}
