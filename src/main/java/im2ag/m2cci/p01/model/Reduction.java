package im2ag.m2cci.p01.model;

public class Reduction {

//attributs
    private String name = "";
    private int value;
//Constructeur
    public Reduction (String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValeurReduction () {
        return this.value;
    }

    public String getNomReduction () {
      return this.name;
    }

    public String toString () {
        return "Reduction : " + this.name + " (" + this.value + "%)";
    }
}
