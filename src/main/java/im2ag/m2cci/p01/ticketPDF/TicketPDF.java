package im2ag.m2cci.p01.ticketPDF;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import im2ag.m2cci.p01.model.Representation;
import im2ag.m2cci.p01.model.Spectacle;
import im2ag.m2cci.p01.model.Ticket;
import im2ag.m2cci.p01.model.DossierDAchat;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.util.Iterator;


/**
* Cette classe permet de générer un document PDF document correspondant à un
* ticket pour une epreuve sportive en utilisant la librairie apache PDFBox
* (https://pdfbox.apache.org/).
*
* @author Philippe Genoud (Université Grenoble Alpes - laboratoire LIG STeamer)
*/
public class TicketPDF {

  public static BufferedImage createQRC(String codeText, int size) throws WriterException {
    String fileType = "png";
    Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix byteMatrix = qrCodeWriter.encode(codeText, BarcodeFormat.QR_CODE, size, size, hintMap);
    int CrunchifyWidth = byteMatrix.getWidth();
    BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
    BufferedImage.TYPE_INT_RGB);
    image.createGraphics();

    Graphics2D graphics = (Graphics2D) image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
    graphics.setColor(Color.BLACK);

    for (int i = 0; i < CrunchifyWidth; i++) {
      for (int j = 0; j < CrunchifyWidth; j++) {
        if (byteMatrix.get(i, j)) {
          graphics.fillRect(i, j, 1, 1);
        }
      }
    }
    return image;

  }

  /**
  * Crée en mémoire un document pdf correspondant à un ticket électronique
  *
  * @param login le nom du titulaire du billet (peut être null)
  * @param idPlace le numéro de la place achetée
  * @param logoFileName le nom (chemin absolu) du fichier contenant l'image
  * logo à afficher en haut du ticket.
  * @return l'objet byte array contenant les données du fichier pdf
  * @throws IOException
  * @throws com.google.zxing.WriterException
  */
  public static byte[] createPDF_AsByteArray(String login, DossierDAchat dossier, String logoFileName, Representation representation, Spectacle spectacle) throws IOException, WriterException {

    // Le tableau d'octets qui contiendra en mémoire le document pdf
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try (PDDocument document = new PDDocument();) {
      // on utilise un try avec ressource pour être sûr que le document pdf est bien fermé

      Iterator<Ticket> dossierIt = dossier.iterator();
      while (dossierIt.hasNext()) {
        Ticket ticket = dossierIt.next();

        PDPage page = new PDPage();
        document.addPage(page);

        try ( // Définition d'un objet contentStream destiné à "contenir" le document qui va être créée.
        // On utilise un try avec ressources  pour s'assurer que l'objet contentStream sera bien fermé
        PDPageContentStream contentStream = new PDPageContentStream(document, page);) {

          //--------------------------------------------------------------
          // lecture du fichier image du logo et écriture de celui-ci
          // dans le contentStream
          //--------------------------------------------------------------
          BufferedImage logo = ImageIO.read(new File(logoFileName));
          PDImageXObject logoImage = LosslessFactory.createFromImage(document, logo);
          contentStream.drawImage(logoImage, 10, 700);

          //--------------------------------------------------------------
          // Définition d'un texte donnant le numéro du ticket, le nom du
          // titulaire, le nom et la date de l'épreuve, le nombre de places.
          //--------------------------------------------------------------
          // création d'un nouvel objet font selectionnant l'une des polices de
          // base du PDF et association de celui-ci au contentStream
          PDFont font = PDType1Font.HELVETICA_BOLD;
          contentStream.setFont(font, 12);

          // affichage du numéro de ticket
          contentStream.beginText();
          contentStream.newLineAtOffset(140, 670); // positionnement du curseur là où le texte doit être affiché
          contentStream.showText("Ticket N°" + String.format("%06d", ticket.getNumero())); // affichage du texte
          contentStream.endText();

          // Reduction
          if (ticket.getReduction() != null) {
            contentStream.beginText();
            contentStream.newLineAtOffset(140, 650);
            contentStream.showText(ticket.getReduction().toString());
            contentStream.endText();
          }

          // affichage du nom du titulaire du billet
          if (login != null) {
            contentStream.beginText();
            contentStream.newLineAtOffset(140, 630);
            contentStream.showText("Utilisateur:" + login);
            contentStream.endText();
          }

          // affichage du nom de l'épreuve
          contentStream.beginText();
          contentStream.newLineAtOffset(140, 610);
          contentStream.showText("Spectacle: " + spectacle.getNom());
          contentStream.endText();

          // affichage de la date de l'épreuve
          contentStream.beginText();
          contentStream.newLineAtOffset(140, 590);
          contentStream.showText("Date: " + representation.getDateString() + " à " + representation.getHeure() + " heures");
          contentStream.endText();

          // affichage du nombre de places achetées
          contentStream.beginText();
          contentStream.newLineAtOffset(140, 570);
          contentStream.showText("Numéro de la place: " + ticket.getNumeroPlace());
          contentStream.endText();

          contentStream.beginText();
          contentStream.newLineAtOffset(140, 550);
          contentStream.showText("Numéro du rang: " + ticket.getNumeroRang());
          contentStream.endText();

          contentStream.beginText();
          contentStream.newLineAtOffset(140, 530);
          contentStream.showText("Nom de la zone: " + ticket.getZonePlace());
          contentStream.endText();

          // affichage du prix total
          contentStream.beginText();
          contentStream.newLineAtOffset(140, 510);
          contentStream.showText("Prix total: " + dossier.getPrixTotal() + " Euros");
          contentStream.endText();

          // Paiement ?
          contentStream.beginText();
          contentStream.newLineAtOffset(140, 490);
          contentStream.showText((dossier.getEstPaye() ? "A PAYE EN LIGNE" : "DOIT PAYER EN CAISSE"));
          contentStream.endText();

          //---------------------------------------------------------------------------
          // ajout de l'image avec le QR Code
          // le texte à encoder avec le QR Code
          String data = "Ticket N°" + String.format("%06d", ticket.getNumero());
          // génération de l'image avec la bibliothèque  zxing core 3.3.0 (https://github.com/zxing/zxing)
          // et  écriture de celle-ci l'image dans le fichier pdf
          PDImageXObject ximage = LosslessFactory.createFromImage(document, createQRC(data, 100));
          contentStream.drawImage(ximage, 20, 580);

          // une autre manière de procéder aurait eté de faire
          // appel du web service qrickit.com pour récupérer en une image d'un code QRC
          // et afficher celle-ci dans le document PDF.
          // Pour plus d'informations sur l'API de ce webservice et les différents
          // paramètres possibles: http://qrickit.com/qrickit_apps/qrickit_api.php
          // Mais sur les machine de l'ufr cela ne marche pas à cause du proxy....
          // C'est pour contourner ce problème que nous avaons utilisé la librairie zxing
          //
          //                // attention ce texte devant être passé en paramètre d'une requête HTTP GET
          //                // il doit être "url encodé" (pour gérer correctement les espaces et caractères non ASCII).
          //                data = URLEncoder.encode(data, "UTF-8");
          //                // appel du service web pour obtenir l'image du QR code
          //                BufferedImage awtImage = ImageIO.read(new URL(
          //                        "http://qrickit.com/api/qr?d=" + data
          //                        + "&qrsize=100&t=p&e=m"));
          //--------------------------------------------------------------
          // Sauvegarde du document pdf dans l'objet ByteArrayOutputStream
          //--------------------------------------------------------------
          // avant toute chose l'objet ContentStream doit être fermé
          contentStream.close();
        }
      }
      document.save(out);
      return out.toByteArray();
    }
  }
}
