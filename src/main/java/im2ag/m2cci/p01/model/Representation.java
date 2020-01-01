package im2ag.m2cci.p01.model;

import java.util.Date;
import im2ag.m2cci.p01.dao.DateString;

/**
*
* @author GP01
*/
public class Representation implements Comparable<Representation> {
  private final int id;
  private final DateString date;
  private final int heure;
  private final int nbPlacesDispo;
  private final int promotion;

  // S1
  /**
  * Representation (id, date, heure, nbPlacesDispo)
  * creation d'un objet de type Rpresentation dont les parametres sont:
  * @param id  : identifiant de representation
  * @param date : date à laquelle cette Representation aura lieu
  * @param heure : heure à laquelle cette Representation aura lieu
  * @param nbPlacesDispo : nombre de places disponibles proposees par cette Representation
  */

  public Representation (int id, String date, int heure, int nbPlacesDispo) {
    this.id = id;
    this.date = new DateString(date);
    this.heure = heure;
    this.nbPlacesDispo = nbPlacesDispo;
    this.promotion = 0;
  }


  // S2
  public Representation (int id, String date, int heure, int nbPlacesDispo, int promotion) {
    this.id = id;
    this.date = new DateString(date);
    this.heure = heure;
    this.nbPlacesDispo = nbPlacesDispo;
    this.promotion = promotion;
  }

  /**
  * getId() est une methode getter permettant de recuperrer
  * le parametre id de la représentation
  * @return this.id
  */
  public int getId () {
    return this.id;
  }
  /**
  * getDateString () est une methode getter permettant de recuperrer
  * la date (sous forme String) à laquelle un objet de type Representation aura lieu
  * @return this.date.toString()
  */
  public String getDateString () {
    return this.date.toString();
  }
  /**
  * getDate () est une methode getter permettant de recuperrer
  * la date à laquelle un objet de type Representation aura lieu
  * @return this.date.getDate();
  */
  public Date getDate () {
    return this.date.getDate();
  }
  /**
  * retourne le format de date pour passer en argument (YYYY-MM-DD)
  */
  public String getDateStringArg () {
    return this.date.getDateString();
  }
  /**
  * getHeure () est une methode getter permettant de recuperrer
  * l' heure (sous forme d'integer) à laquelle un objet de type Representation aura lieu
  * @return this.heure
  */
  public int getHeure () {
    return this.heure;
  }
  /**
  * getNbPlacesDispo () est une methode getter permettant de recuperrer
  * le nombre de Places disponibles proposées par un objet de type Representation
  * @return this.nbPlacesDispo
  */
  public int getNbPlacesDispo () {
    return this.nbPlacesDispo;
  }
  /**
  * getPromotion () est ue methode getter  permettant de recuperer la pourcentage de promotion
  * (promotion) d'un objet de type Representation
  * @return this.promotion
  */
  public int getPromotion () {
    return this.promotion;
  }
  /**
  * toString () est une methode permettant de retourner sous forme d'un String
  *          "id d'objet de type Representation à date heure h  (retour à la ligne)
  *           Il reste nbPlacesDispo places."
  * @return this.id + " à " + this.date.getDateString() + " " + this.heure + "h\n\tIl reste " + this.nbPlacesDispo + " places.";
  */
  @Override
  public String toString () {
    return this.id + " à " + this.date.getDateString() + " " + this.heure + "h\n\tIl reste " + this.nbPlacesDispo + " places.";
  }

  @Override
  public int compareTo (Representation anotherRep) {
    int diff = this.date.getDate().compareTo(anotherRep.getDate());
    if (diff == 0) {
      diff = this.id - anotherRep.getId();
    }
    if (diff == 0) {
      diff = this.heure - anotherRep.getHeure();
    }
    return diff;
  }
  /**
  * equals(object) est une methode permettant de retourner un boolean (isEqual)
  * suite à une comparaison deux objets
  * Dans notre cas, cette methode sera utile pour verifier si deux objets de
  * type Representation font reference à la meme Representation ou pas.
  * @return isEqual;
  */
  @Override
  public boolean equals (Object object) {
    boolean isEqual = false;
    if (object != null && this.getClass() == object.getClass()) {
      final Representation rep = (Representation) object;
      isEqual = this.heure == rep.getHeure() && this.id == rep.getId() && this.nbPlacesDispo == rep.getNbPlacesDispo();
      isEqual = isEqual && this.getDate().compareTo(rep.getDate()) == 0;
    }
    return isEqual;
  }

  @Override
  public int hashCode() {
    int hash = 41;
    return hash * 3 + this.id * this.heure * this.nbPlacesDispo;
  }
}
