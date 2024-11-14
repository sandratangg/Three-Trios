package cs3500.threetrios.strategies;

import cs3500.threetrios.model.ThreeTriosCard;
import java.util.Objects;

/**
 * Represents a move in the Three Trios game, consisting of a row, column, and a card to place.
 */
public class Move {

  private final int row;
  private final int col;
  private final ThreeTriosCard card;

  /**
   * Constructs a Move object with the specified row, column, and card.
   *
   * @param row  the row index where the card will be placed
   * @param col  the column index where the card will be placed
   * @param card the card to be placed at the specified position
   */
  public Move(int row, int col, ThreeTriosCard card) {
    this.row = row;
    this.col = col;
    this.card = card;
  }

  /**
   * Gets the row index of this move.
   *
   * @return the row index
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets the column index of this move.
   *
   * @return the column index
   */
  public int getCol() {
    return col;
  }

  /**
   * Gets the card associated with this move.
   *
   * @return the card to be placed
   */
  public ThreeTriosCard getCard() {
    return card;
  }

  /**
   * Checks if this move is equal to another object.
   *
   * @param o the object to compare with
   * @return true if the given object is equal to this move, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Move move = (Move) o;
    return row == move.row && col == move.col && Objects.equals(card, move.card);
  }

  /**
   * Computes the hash code for this move.
   *
   * @return the hash code of this move
   */
  @Override
  public int hashCode() {
    return Objects.hash(row, col, card);
  }

  /**
   * Returns a string representation of the move.
   *
   * @return a string describing the move
   */
  @Override
  public String toString() {
    return "Move{" +
            "row=" + row +
            ", col=" + col +
            ", card=" + card +
            '}';
  }
}
