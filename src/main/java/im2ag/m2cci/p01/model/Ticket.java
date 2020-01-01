/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package im2ag.m2cci.p01.model;

/**
*
* @author GP01
*/
public class Ticket {

  private final int numero;
  private final Reduction reduction;
  private int numPla;
  private int numRang;
  private String zone;


  public Ticket (int numero, Reduction reduction){
    this.numero = numero;
    this.reduction = reduction;
    this.numPla = 0;
    this.numRang = 0;
    this.zone = "";
  }
  public Ticket (int numero, Reduction reduction, int numPlace, int numRang, String zone){
    this.numero = numero;
    this.reduction = reduction;
    this.numPla = numPlace;
    this.numRang = numRang;
    this.zone = zone;
  }

  public int getNumero() {
    return this.numero;
  }

  public String getZonePlace() {
    return this.zone;
  }
  public int getNumeroPlace() {
    return this.numPla;
  }
  public void setNumeroPlace(int num) {
    this.numPla=num;
  }

  public int getNumeroRang() {
    return this.numRang;
  }
  public void setNumeroRang(int num) {
    this.numRang=num;
  }
  public Reduction getReduction() {
    return this.reduction;
  }

  @Override
  public String toString() {
    return "Ticket{" + "numero=" + this.numero + ", reduction=" + this.reduction + '}';
  }


}
