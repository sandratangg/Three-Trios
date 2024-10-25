package cs3500.threetrios.model;

/**
 * Represents a cell in the game.
 */
public class ThreeTriosCell implements ICell {
  boolean isHole;
  public ThreeTriosCard card;

  /**
   * Constructor for a Card Cell (A space that is empty or contains a card).
   */
  public ThreeTriosCell() {
    this.isHole = false;
    this.card = null;
  }

  /**
   * Constructor for a Hole (A dark space where no card can be placed.).
   * @param isHole whether the cell is a hole or not
   */
  public ThreeTriosCell(boolean isHole) {
    this.isHole = isHole;
    this.card = null;
  }

  /**
   * Method that checks if it is a Card Cell and if it is empty.
   */
  public boolean isEmpty() {
    return !isHole && card == null;
  }

  /**
   * Method that gets the Card in the cell.
   */
  public ThreeTriosCard getCard() {
    return this.card;
  }

  /**
   * Method that sets the Card in the cell.
   */
  public void setCard(ThreeTriosCard card) {
    this.card = card;
  }
}
