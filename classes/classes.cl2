--=========================================================================
--                          Class Model
---------------------------------------------------------------------------
-- This glossary model is expressed in GlossaryScript.
-- See https://modelscript.readthedocs.io/en/latest/scripts/classes1/index.html
-- ClassScript1 is an annotated version of USE OCL.
-- Annotations are in comments and start with
--    "@" for statement annotations
--    "|" for documentation annotations
--=========================================================================
--
--@ class model MyTheatre
--@ import glossary model from "../glossaries/glossaries.gls"

model MyTheatre

------------------------------------------------------------------
-- Enumerations
------------------------------------------------------------------

enum CategorieTarifaire {
    orchestre,
    balcon,
    poulailler
}


enum Promotion {
    p10,
    p20,
    p30,
    p40,
    p50,
    none
}

enum AgeClassification {
    toutPublic,
    moins12,
    moins16,
    moins18
}

enum Genre {
    humouristique,
    musical,
    tragedie,
    comedie,
    improvisation,
    intrigue
}

------------------------------------------------------------------
-- Classes
------------------------------------------------------------------

-- laisser 70 places en vente une heure avant le spectacle

class Spectacle
    attributes
        _numero_ : Integer
        nom : String
        img : Integer --@ Blob
        genre : Genre
        trancheAge : AgeClassification
end

class Representation
    attributes
        _date_ : String --@ {Date}
        _heure_ : String --@ {Time}
        id : Integer --@ AUTOINCREMENT
        nbPlacesDispo : Integer
        promotion : Promotion
end

class Zone
    attributes
        _numero_ : Integer
        nom : String --@ CategorieTarifaire.toString
        prix : CategorieTarifaire -- unique
end

class Place
    attributes
        _id_ : Integer --@ AUTOINCREMENT
        _rang_ : Integer
        _numero_ : Integer
end

class DossierDAchat
    attributes
        _numero_ : Integer
        prix : Integer -- derived
        dateDAchat : String --@ {Date + Time} jusqu'à la seconde (derived)
        estPaye : Boolean
end

class Utilisateur
    attributes
        _login_ : String -- {id}
        email : String -- unique
        motDePasse : String
        nom : String
        prenom : String
end


associationclass Ticket
    between
        Representation[0..*] role representations
        Place[0..*] role places
    attributes
        _numero_ : Integer

end

class Auteur
    attributes
        _nom_ : String
        _prenom_ : String
end

class Reduction
    attributes
        _nomReduc_ : String
        tauxReduc : Integer
end

------------------------------------------------------------------
-- Contraintes
------------------------------------------------------------------
--@ constraint NomDeReduction
--@     scope
--@         Reduction
--@         Reduction._nomReduc_
--@     | le nom de Reduction doit etre l'un de noms definis dans cette ensemble: {'adherent', 'etudiant', 'scolaire', 'senior',
--@     | 'familleNombreuse'}.
--context Reduction

--@ constraint TauxDeReduction
--@     scope
--@         Reduction
--@         Reduction.tauxReduc
--@     | le taux de Reduction est exprimé en pourcentage donc on ne peut jamais avoir des valeurs de taux de reduction supérieur à 100.
--context Reduction

constraints

--@ constraint NumeroDePlace
--@     scope
--@         Place
--@         Place._numero_
--@     | le numéro de Place est toujours supérieur à zéro.
--context Place
--    inv numero: self._numero_ > 0

--@ constraint NumeroDeZone
--@     scope
--@         Zone
--@         Zone._numero_
--@     | le numéro de Zone est toujours supérieur à zéro.
--context Zone
--    inv numero: self._numero_ > 0

--@ constraint NumeroDeDossierDAchat
--@     scope
--@         DossierDAchat
--@         DossierDAchat._numero_
--@     | le numéro de DossierDAchat est toujours supérieur à zéro.
--context DossierDAchat
--    inv numero: self._numero_ > 0

--@ constraint NumeroDeSpectacle
--@     scope
--@         Spectacle
--@         Spectacle._numero_
--@     | le numéro de Spectacle est toujours supérieur à zéro.
--context Spectacle
--    inv numero: self._numero_ > 0

--@ constraint NombreDePlacesDisponibles
--@     scope
--@         Place
--@         Zone
--@         Representation
--@         Appartient
--@         Ticket
--@         places
--@         placesPhysiques
--@         zone
--@         Representation.nbPlacesDispo
--@     | le nbPlacesDispo d'une Representation est inférieur ou égal à la somme de placesPhysiques dans toutes les différentes zone
--@     | le nbPlacesDispo d'une Representation est égal à la différence entre la somme de placesPhysiques dans toutes les différentes
--@     | zone et le nombre total de Ticket (i.e. places reservées).
--@     | zone et le nombre total de Ticket (i.e. places réservées).

--context Representation
--    inv nbPlacesDispo: self.places->size() >= self.ticket->size()

--@ constraint ValeurDePromotion
--@     scope
--@         Representation
--@         Representation.promotion
--@     | Si une Representation ne propose aucune promotion, la valeur de l'attribut "promotion" dans ce cas est "none" et donc
--@     | l' Utlisateur payera un tarif plein pour acheter le Ticket associé à cette Representation.
--@     | Sinon si la Representation propose une promotion, dans ce cas il y a plusieurs possibiltés :
--@     | une promotion "p10" signifie un tarif reduite de 10%,
--@     | "p20" correspond à un tarif reduite de 20%,
--@     | "p30" ramene à un tarif reduite de 30%,
--@     | "p40" signifie un tarif reduite de 40%,
--@     | et enfin "p50" correspond à un tarif reduite de 50%.

--@ constraint DateDAchatSurDossierDAchat
--@     scope
--@         DossierDAchat
--@         Representation
--@         DossierDAchat.dateDAchat
--@         Representation._date_
--@         Representation._heure_
--@     | La date marquée sur le DossierDAchat (exprimée à la granularité de la seconde) doit etre avant la date et heure de Representation
--@     | correspondante aux Tickets appartenant à ce DossierDAchat.
--context DossierDAchat
--    inv dateDAchat: self.dateDAchat < self.placesAchetees.representations._date_

--@ constraint PrixDossierDAchat
--@     scope
--@         DossierDAchat
--@         DossierDAchat.prix
--@     | Le prix global marqué sur le DossierDAchat est toujours supérieur à zéro.
--context DossierDAchat
--    inv prix: self.prix > 0

--@ constraint PrixZone
--@     scope
--@         Zone
--@         Zone.prix
--@     | Le prix de Zone est toujours supérieur à zéro, et il est fixe et égal à la tarifDeBase de Categorie à laquelle apprtient cette Zone.
--@     | Si la zone appartient à la CategorieTarifaire  "poulailler" le prix d'un placement dans cette zone est fixé à 15€.
--@     | Sinon, si la zone appartient à la CategorieTarifaire  "balcon" le prix d'un placement dans cette zone est fixé à 20€.
--@     | Sinon, si la zone appartient à la CategorieTarifaire  "orchestre" le prix d'un placement dans cette zone est fixé à 25€.

--@ constraint PrixDossierDAchatVsPrixZone
--@     scope
--@         DossierDAchat
--@         Zone
--@         DossierDAchat.prix
--@         Zone.prix
--@     | Sachant que le prix global marqué sur le DossierDAchat est la somme de prix de Ticket contenus dans ce DossierDAchat,
--@     | le prix global de DossierDAchat doit etre toujours supérieur ou égal au prix (tarifDeBase) de Zone à laquelle
--@     | appartiennent ces Tickets.
--context DossierDAchat
--    inv prix: self.prix >= self.placesAchetees -> collect(places.zone.prix) -> sum()

--@ constraint ConfigurationSalle
--@     scope
--@         Spectacle
--@         Zone
--@     | Un `Spectacle` peut avoir une à plusieurs `Zones` occupées pour les `Representations`.
--@     | Les `Zones` occupées par un `Spectacle` sont toutes différentes.
--@     | Les `Zones` occupées par le `Spectacle` ne peuvent pas être mises en vente.
--context Spectacle
--    inv zoneUnique: self.zoneReservee->includes(self.representations.places.zone)

------------------------------------------------------------------
-- Associations
------------------------------------------------------------------
association BeneficieDe
    between
        Ticket[0..*] role ticketsreduits
        Reduction[0..*] role reductionsDeTickets --Question (Issue 137) -- Updated after answers given at Issue 137
end

association CreePar
    between
        DossierDAchat[0..*] role dossierDAchats
        Utilisateur[0..1] role acheteur
end

association Propose
    between
        Spectacle[1] role spectacleRepresente
        Representation[1..*] role representationsProposees
end

composition Appartient
    between
        Zone[1] role zone
        Place[1..*] role placesPhysiques
end

association EstDossierDAchat
    between
        DossierDAchat[1] role vente
        Ticket[1..*] role placesAchetees
end

association Occupe
    between
        Spectacle[*] role spectacles
        Zone[1..*] role zonesOccupees
end

association EstAuteurDe
    between
        Auteur[1] role auteur
        Spectacle[1..*] role spectaclesEcrits
end
