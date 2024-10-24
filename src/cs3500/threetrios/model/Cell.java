package cs3500.threetrios.model;

public class Cell {
  boolean isHole;
  Card card;

  Cell() {
    this.isHole = false;
    this.card = null;
  }

  Cell(boolean isHole) {
    this.isHole = isHole;
    this.card = null;
  }

  boolean isEmpty() {
    return !isHole && card == null;
  }


}
