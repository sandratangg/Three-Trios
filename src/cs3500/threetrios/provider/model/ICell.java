package cs3500.threetrios.provider.model;

import cs3500.threetrios.provider.model.PlayerColor;

/**
 * Represents a singular cell on a grid for a game of Three Trios. A cell can either be a card hole,
 * or a card cell. A card hole cannot hold cards, while a card cell can.
 */
public interface ICell {
  /**
   * Plays a card to this cell. Modifying the card passed into this function outside of it should
   * not affect the Cell's internal state.
   *
   * @param card  the card to be placed
   * @param owner the color corresponding to the player who owns this card x
   *
   * @throws UnsupportedOperationException if the current cell is a card hole, since card holes
   *                                       cannot hold cards
   * @throws NullPointerException          if card or owner is null
   */
  void placeCard(IThreeTriosCard card, PlayerColor owner);

  /**
   * Returns a copy of the card that this cell is holding, or an empty object if the cell is not
   * holding a card. Modifying the return of this function should not affect the cell's state.
   *
   * @return the card that the cell is holding
   * @throws UnsupportedOperationException if the cell is a hole
   * @throws IllegalStateException         if the cell does not contain a card
   */
  IThreeTriosCard getCard();

  /**
   * Returns the color corresponding to the player who owns the card in the current cell, or an
   * empty object if the cell does not hold a card.
   *
   * @return the color of the player who owns the card in this cell
   * @throws UnsupportedOperationException if the cell is a hole
   * @throws IllegalStateException         if the cell does not contain a card
   */
  PlayerColor getCardOwner();

  /**
   * Returns whether the cell is a card cell and contains a card.
   *
   * @return a boolean that represents whether the cell contains a card
   */
  boolean isOccupiedCardCell();

  /**
   * Returns whether the cell is an unoccupied card cell.
   *
   * @return a boolean that represents whether the cell is a card cell and is unoccupied
   */
  boolean isUnoccupiedCardCell();

  /**
   * Flips the car∂ being held by the current cell.
   *
   * @throws UnsupportedOperationException if the current cell is a cell hole
   * @throws IllegalStateException         if the cell is a card cell, but doesn't have a card in
   *                                       it, or if the card has no owner
   */
  void flipCardInCell();

  /**
   * Returns the cell's type (a card hole, or a card cell).
   *
   * @return the cell's type
   */
  CellType getType();
}
