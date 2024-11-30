package cs3500.threetrios.provider.model;

import cs3500.threetrios.model.PlayerColor;

/**
 * Describes behaviours of the board/grid in a game of Three Trios. The grid consists of "card
 * cells" where cards can be placed, and "holes" where cards cannot be placed. Playing a card to a
 * position can have other side effects. A grid cannot have an odd number of card cells.
 */
public interface ProviderIGrid {
  /**
   * Plays a given card to a specified position on the grid.
   *
   * @param row        the zero-based index of row on the grid to be played to. Rows are numbered
   *                   from top to bottom, starting with 0 at the top.
   * @param col        the zero-based index of column on the grid to be played to. Columns are
   *                   numbered from left to right, starting with 0 at the left.
   * @param playedCard the card to be played to the specified position
   * @param color      the color corresponding to the player who played the card
   * @throws NullPointerException     if card or color is null
   * @throws IllegalArgumentException if the given row or column is out of bounds
   * @throws IllegalArgumentException if the given position is a card hole
   * @throws IllegalStateException    if the given position is already occupied
   */
  void playCardToPosition(int row, int col, IThreeTriosCard playedCard, PlayerColor color);


  /**
   * Getter method for the number of card cells on the grid.
   *
   * @return the number of card cells on the grid
   */
  int getCardCellCount();

  /**
   * Returns the width of the grid, or the number of columns.
   *
   * @return the width of the grid as an integer
   */
  int getWidth();

  /**
   * Returns the height of the grid, or the number of Rows.
   *
   * @return the height of the grid as an integer
   */
  int getHeight();

  /**
   * Returns the cell at the given position. Mutating the cell returned should not affect the state
   * of the game.
   *
   * @param row the zero-based index of row on the grid to be played to. Rows are numbered from top
   *            to bottom, starting with 0 at the top.
   * @param col the zero-based index of column on the grid to be played to. Columns are numbered
   *            from left to right, starting with 0 at the left.
   * @return the cell at the given position
   * @throws IllegalArgumentException if the given row/column is out of bounds
   */
  ProviderICell getCell(int row, int col);


  /**
   * Checks if the given grid position is out of bounds.
   *
   * @param row the zero-based index of row on the grid to be played to. Rows are numbered from top
   *            to bottom, starting with 0 at the top.
   * @param col the zero-based index of column on the grid to be played to. Columns are numbered
   *            from left to right, starting with 0 at the left.
   * @return a boolean that represents if the given grid position is out of bounds
   */
  boolean isOutOfBounds(int row, int col);


  /**
   * Returns whether every card cell on the grid has been occupied by a card.
   *
   * @return if every card cell on the grid has been occupied by a card.
   */
  boolean allCellsAreFilled();

  /**
   * Flips the card in the cell at the given position.
   *
   * @param row the zero-based index of row on the grid to be played to. Rows are numbered from top
   *            to bottom, starting with 0 at the top.
   * @param col the zero-based index of column on the grid to be played to. Columns are numbered
   *            from left to right, starting with 0 at the left.
   * @throws IllegalArgumentException if the given row or column is out of bounds, or if the given
   *                                  position is a card hole
   * @throws IllegalStateException    if the cell in the given position doesn't have a card have in
   *                                  it
   */
  void flipCardInCell(int row, int col);

  /**
   * Returns the number of cards owned by a player, given the player's color.
   *
   * @param color the color of the player in question
   * @return the number of cards owned by the player
   * @throws NullPointerException if the given color is null
   */
  int getPlayerOwnedCardsCount(PlayerColor color);
}
