package cs3500.threetrios.model;

public class ThreeTriosCell implements ICell {
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

  public boolean isEmpty() {
    return !isHole && card == null;
  }

}
