package cs3500.threetrios.model;

/**
 * Represents a cell in the game.
 */
public class ThreeTriosCell implements ICell {
  boolean isHole;
  ThreeTriosCard card;

  /**
   * Constructor for a Card Cell (A space that is empty or contains a card).
   */
  ThreeTriosCell() {
    this.isHole = false;
    this.card = null;
  }

  /**
   * Constructor for a Hole (A dark space where no card can be placed.).
   * @param isHole whether the cell is a hole or not
   */
  ThreeTriosCell(boolean isHole) {
    this.isHole = isHole;
    this.card = null;
  }

  /**
   * Method that checks if it is a Card Cell and if it is empty.
   */
  public boolean isEmpty() {
    return !isHole && card == null;
  }

}
