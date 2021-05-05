package com.vmourac;

import java.util.Objects;

public class Bid {
  private int id;
  private double ammount;

  public Bid(int id, double ammount) {
    this.id = id;
    this.ammount = ammount;
  }

  public int getId() {
    return id;
  }

  public double getAmmount() {
    return ammount;
  }

  public void setAmmount(double ammount) {
    this.ammount = ammount;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ID: " + this.getId() + " - Bid: " + String.format("%.2f", this.getAmmount());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Bid bid = (Bid) o;
    return Double.compare(bid.ammount, ammount) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ammount);
  }
}
