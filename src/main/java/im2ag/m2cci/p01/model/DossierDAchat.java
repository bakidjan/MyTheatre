package im2ag.m2cci.p01.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
*
* @author GP01
*/
public class DossierDAchat implements Iterable<Ticket> {

  private final int numero;
  private String dateDAchat;
  private boolean estPaye;
  private int prixTot;

  private final List<Ticket> lesTickets;

  public DossierDAchat(int numero) {
    this.numero = numero;
    this.lesTickets = new ArrayList<>();   // liste de tickets inclus dans ce dossier d'achat
    this.dateDAchat = "";    // date du jour de creation du dossier d'achat
    this.estPaye = false;
    this.prixTot = 0;
  }

  public DossierDAchat(int numero, String date, boolean estPaye, int prixDos) {
    this.numero = numero;
    this.lesTickets = new ArrayList<>();   // liste de tickets inclus dans ce dossier d'achat
    this.dateDAchat = date;    // date du jour de creation du dossier d'achat
    this.estPaye = estPaye;
    this.prixTot = prixDos;
  }

  public void addTicket(int numeroTicket, Reduction reduction) {
    this.lesTickets.add(new Ticket(numeroTicket, reduction));
  }
  public void addTicket(Ticket ticket) {
    this.lesTickets.add(ticket);
  }

  public int getPrixTotal(){
    return this.prixTot;
  }
  public void setPrixTotal (int prixTot) {
    this.prixTot = prixTot;
  }

  public boolean getEstPaye (){
    return this.estPaye;
  }
  public void payerDossier (String date) {
    this.estPaye = true;
    this.dateDAchat = date;
  }

  public int getNbTicket() {
    return this.lesTickets.size();
  }

  public List<Ticket> getAllTickets () {
    return this.lesTickets;
  }

  public String getDateDAchat() {
    return this.dateDAchat;
  }

  @Override
  public Iterator<Ticket> iterator() {
    return this.lesTickets.iterator();
  }

  public int getNumero() {
    return this.numero;
  }

  @Override
  public String toString() {
    String s = "DossierDAchat{ " + "numero=" + this.numero + ", dateDAchat=" + this.dateDAchat + " }\n";
    s = s + "Le dossier est " + (this.estPaye ? "" : "non ") + "payé.";
    return s;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final DossierDAchat other = (DossierDAchat) obj;
    if (this.numero != other.numero) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + this.numero;
    return hash;
  }

}
