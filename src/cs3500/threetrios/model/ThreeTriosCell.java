package cs3500.threetrios.model;

public class ThreeTriosCell {
  boolean isHole;
  ThreeTriosCard card;

  ThreeTriosCell() {
    this.isHole = false;
    this.card = null;
  }

  ThreeTriosCell(boolean isHole) {
    this.isHole = isHole;
    this.card = null;
  }

  boolean isEmpty() {
    return !isHole && card == null;
  }


}
