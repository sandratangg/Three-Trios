package cs3500.threetrios.model;

/**
 * Represents a cell within the Three Trios game grid. A cell can either be a
 * playable space, capable of holding a card, or a "hole" space where no card
 * can be placed. This class manages the cell state, including its occupancy
 * and type (regular or hole), and provides methods for placing and retrieving
 * cards within the cell.
 */
public class ThreeTriosCell implements ICell {

  boolean isHole;
  public ICard card;

  /**
   * Constructs a playable cell that can hold a card. This constructor
   * initializes a cell that is not a hole and starts as empty (no card).
   */
  public ThreeTriosCell() {
    this.isHole = false;
    this.card = null;
  }

  /**
   * Constructs a cell that may either be a playable cell or a hole.
   * Holes are designated spaces on the grid where no card can be placed.
   *
   * @param isHole true if the cell is a hole where no card can be placed;
   *               false if it is a regular playable cell.
   */
  public ThreeTriosCell(boolean isHole) {
    this.isHole = isHole;
    this.card = null;
  }

  public ThreeTriosCell(boolean isHole, ICard card) {
    this.isHole = isHole;
    this.card = card;
  }

  public boolean isEmpty() {
    return !isHole && card == null;
  }

  /**
   * Retrieves the card currently placed in the cell. If the cell is empty,
   * this method returns null.
   *
   * @return the {@link ICard} in the cell, or null if the cell is empty.
   */
  public ICard getCard() {
    return new ThreeTriosCard(card.getName(),
            card.attack(Direction.NORTH),
            card.attack(Direction.EAST),
            card.attack(Direction.WEST),
            card.attack(Direction.SOUTH));
  }

  /**
   * Places a card in the cell. This method sets the card in the cell,
   * overriding any previous card assignment. It does not perform any
   * checks on whether the cell is a hole or already occupied; such checks
   * should be managed by the game logic.
   *
   * @param card the {@link ICard} to place in the cell.
   */
  public void setCard(ICard card) {
    this.card = card;
  }
}
