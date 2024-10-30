package cs3500.threetrios.model;

/**
 * Represents a cell in the game.
 */
public class ThreeTriosCell implements ICell {
  boolean isHole;
  public ICard card;

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
   * Constructor for a Card Cell with a given card.
   *
   * @param isHole whether the cell is a hole or not
   * @param card The card to place in the cell
   */
  public ThreeTriosCell(boolean isHole, ICard card) {
    this.isHole = isHole;
    this.card = card;
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
  public ICard getCard() {
    return new ThreeTriosCard(card.getName(),
            card.attack(Direction.NORTH),
            card.attack(Direction.EAST),
            card.attack(Direction.WEST),
            card.attack(Direction.SOUTH));
  }

  /**
   * Method that sets the Card in the cell.
   */
  public void setCard(ICard card) {
    if (this.isHole) {
      throw new IllegalStateException("Cannot place card in a hole.");
    }
    this.card = card;
  }

  /**
   * Method that checks if the card in cell is same as the sample card.
   */
  public boolean sameCard(ThreeTriosCard sampleCard) {
    return this.card.equals(sampleCard);
  }
}
