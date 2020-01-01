
package im2ag.m2cci.p01.model;

/**
 *
 * @author GP01
 */
public class Auteur {

    private String nom;
    private String prenom;

    public Auteur(String nomAut, String prenomAut) {
        this.nom = nomAut;
        this.prenom = prenomAut;
    }

    public String getNomAut() {
        return this.nom;
    }

    public String getPrenomAut() {
        return this.prenom;
    }

    public String getAuteur() {
        return this.getPrenomAut() + " " + this.getNomAut();
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;
        if (object != null && this.getClass() == object.getClass()) {
            final Auteur auteur = (Auteur) object;
            isEqual = this.nom.equals(auteur.getNomAut()) && this.prenom.equals(auteur.getPrenomAut());
        }
        return isEqual;
    }

    public void setNom(String nomAut) {
        this.nom = nomAut;
    }

    public void setPrenom(String prenomAut) {
        this.prenom = prenomAut;
    }

    @Override
    public String toString() {
        return this.prenom + " " + this.nom;
    }

}
