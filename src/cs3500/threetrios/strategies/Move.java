package cs3500.threetrios.strategies;

import cs3500.threetrios.model.ThreeTriosCard;
import java.util.Objects;

public class Move {
  private final int row;
  private final int col;
  private final ThreeTriosCard card;

  public Move(int row, int col, ThreeTriosCard card) {
    this.row = row;
    this.col = col;
    this.card = card;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public ThreeTriosCard getCard() {
    return card;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Move move = (Move) o;
    return row == move.row &&
            col == move.col &&
            Objects.equals(card, move.card);
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col, card);
  }

  @Override
  public String toString() {
    return "Move{" +
            "row=" + row +
            ", col=" + col +
            ", card=" + card +
            '}';
  }
}
