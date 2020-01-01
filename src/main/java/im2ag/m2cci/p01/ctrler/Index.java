package im2ag.m2cci.p01.ctrler;

import java.util.TreeMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import im2ag.m2cci.p01.dao.DateString;
import im2ag.m2cci.p01.model.Spectacle;
import im2ag.m2cci.p01.model.Representation;

/* créé un index des représentations triées par dates avec lien avec le spectacle */
public class Index implements Iterable<Representation> {

  private Map<Representation, Spectacle> map = new TreeMap(); /* sorted insert */

  /* constructeur pour l'index */
  public Index (List<Spectacle> spectacles) {
    Iterator<Spectacle> specIt = spectacles.iterator();
    while (specIt.hasNext()) {
      Spectacle spec = specIt.next();
      Iterator<Representation> repIt = spec.iterator();
      while (repIt.hasNext()) {
        this.map.put(repIt.next(), spec);
      }
    }
  }

  /**
  * @return le nombre de représentations
  */
  public int getNbRepresentations () {
    return this.map.size();
  }

  public Set<Representation> getAllRepresentations () {
    return this.map.keySet();
  }

  /**
  * renvoie un iterateur permettant de parcourir l'ensemble des représentations
  *
  * @return iterateur
  */
  @Override
  public Iterator<Representation> iterator () {
    return this.getAllRepresentations().iterator();
  }

  /**
  * @return le Spectacle de la représentation
  */
  public Spectacle getSpectacle (Representation rep) {
    return this.map.get(rep);
  }
}
