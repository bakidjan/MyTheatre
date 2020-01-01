package im2ag.m2cci.p01.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.IndexOutOfBoundsException;
import java.util.Base64;

/**
 *
 * @author GP01
 */
public class Spectacle implements Iterable<Representation> {

    private final String nom;
    private final int numero;
    private String img = null;
    private String genre = "";
    private Integer trancheAge = null;
    private Auteur auteurSpec;

    private final List<Representation> representations = new ArrayList<>();
    private final List<Integer> zonesOccupees = new ArrayList<>();

    /* for test */
    /**
     * Constructeur Spectacle (numero, nom)
     *
     * @param numero
     * @param nom creation d'un Spectacle dont le nom est (nom) et le numero est
     * (numero) l'image et la liste de Representations associées à ce Spectacle
     * sont initialisés à null.
     */
    public Spectacle(int numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    // for S1
    /**
     * Constructeur Spectacle (numero, nom, img)
     *
     * @param numero
     * @param nom
     * @param img creation d'un Spectacle dont le nom est (nom) et le numero est
     * (numero) l'image d'affiche de ce Spectacle est (img) la liste de
     * Representations associées à ce Spectacle est initialisée à null.
     */
    public Spectacle(int numero, String nom, byte[] img) {
        this.numero = numero;
        this.nom = nom;
        this.setImg(img);
    }

    // for S2
    public Spectacle(int numero, String nom, byte[] img, String genre, int trancheAge) {
        this.numero = numero;
        this.nom = nom;
        this.setImg(img);
        this.genre = genre;
        this.trancheAge = trancheAge;
    }

    public Spectacle(int numero, String nom, String genre, int trancheAge) {
        this.numero = numero;
        this.nom = nom;
        this.genre = genre;
        this.trancheAge = this.trancheAge;
    }

    public Spectacle(int numero, String nom, String genre, int trancheAge, String nomAut, String prenomAut) {
        this.numero = numero;
        this.nom = nom;
        this.genre = genre;
        this.trancheAge = trancheAge;
        this.auteurSpec = new Auteur(nomAut, prenomAut);
    }

    public void setImg(byte[] img) {
        this.img = new String(Base64.getEncoder().encode(img));
    }

    /**
     * getNom() est une methode getter permettant de recuperrer le parametre
     * (nom) d'un objet de type Spectatcle
     *
     * @return this.nom
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * getNumero() est une methode getter permettant de recuperrer le parametre
     * (numero) d'un objet de type Spectatcle
     *
     * @return this.numero
     */
    public int getNumero() {
        return this.numero;
    }

    /**
     * getImg() est une methode getter permettant de recuperrer le parametre
     * (img) d'un objet de type Spectatcle
     *
     * @return this.img
     */
    public String getImg() {
        return this.img;
    }

    public Auteur getAuteur(){
        return this.auteurSpec;
    }

    /**
     * addRepresentation(id, date, heure, nbPlacesDispo) cette methode permet
     * d'ajouter une nouvelle representation à la liste (rep) de Representation
     * d'un objet de type Spectacle, cette nouvelle a comme parametres :
     *
     * @param id est l'identifient de (id) de la representation à ajouter,
     * @param date est la date à laquelle la representation à ajouter aura lieu,
     * @param heure est l'heure à laquelle la representation à ajouter aura
     * lieu,
     * @param nbPlacesDispo est le nombre de places disponibles proposées par la
     * representation à ajouter
     */
    // S1
    public void addRepresentation(int id, String date, int heure, int nbPlacesDispo) {
        this.representations.add(new Representation(id, date, heure, nbPlacesDispo));
    }

    public String getGenre() {
        return this.genre;
    }

    public int getTrancheAge() {
        return this.trancheAge;
    }

    // S2
    public void addRepresentation(int id, String date, int heure, int nbPlacesDispo, int promotion) {
        this.representations.add(new Representation(id, date, heure, nbPlacesDispo, promotion));
    }

    public void addZoneOccupee(int numero) {
        this.zonesOccupees.add(numero);
    }

    public List<Integer> getZonesOccupees() {
        return this.zonesOccupees;
    }

    public int getNbZonesOccupees() {
        return this.zonesOccupees.size();
    }

    /**
     * toString() methode permettant de transformer le nom et numero d'un objet
     * de type Spectacle à un texte sous la forme: "nom de Spectacle (numero de
     * Spectacle)"
     *
     * @return this.nom + " (" + this.numero + ")"
     */
    @Override
    public String toString() {
        return this.nom + " (" + this.numero + ")";
    }

    @Override
    public Iterator<Representation> iterator() {
        return this.representations.iterator();
    }

    /**
     * getNbRepresentation() est une methode getter qui permet de donner le
     * nombre de Representations associées à un objet de type Spectacle en
     * retournant :
     *
     * @return this.rep.size() : la taille de liste (rep) de representations de
     * ce meme objet de type Spectacle.
     */
    public int getNbRepresentation() {
        return this.representations.size();
    }

    /**
     * getRepresentation(id) est une methode getter qui permet de recuperer pour
     * une Represetation de parametre est
     *
     * @param id
     * @return
     */
    public Representation getRepresentation(int id) {
        return this.representations.get(this.findId(id, 0));
    }

    public List<Representation> getAllRepresentations() {
        return this.representations;
    }

    private int findId(int id, int startId) {
        if (startId < this.getNbRepresentation()) {
            if (this.representations.get(startId).getId() != id) {
                return this.findId(id, ++startId);
            } else {
                return startId;
            }
        } else {
            throw new IndexOutOfBoundsException("Identifiant non présent dans la base de donnée");
        }
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;
        if (object != null && this.getClass() == object.getClass()) {
            final Spectacle spectacle = (Spectacle) object;
            isEqual = this.nom.equals(spectacle.getNom()) && this.numero == spectacle.getNumero();
            //isEqual = isEqual && (this.img == null) ? spectacle.getImg() != null : this.img.equals(spectacle.getImg());
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        int hash = 68;
        return hash * 3 + this.numero;
    }

}
