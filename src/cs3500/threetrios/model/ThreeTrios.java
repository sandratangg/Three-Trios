package cs3500.threetrios.model;

/**
 * Represents the core Three Trios game, encompassing the main game logic
 * and actions available to players. This interface defines essential
 * interactions with the game, such as placing cards on the game grid.
 * Implementations of this interface are responsible for enforcing game rules
 * and managing game state.
 */
public interface ThreeTrios {

  /**
   * Attempts to place a card at a specified location on the game grid.
   * Placement may be subject to grid availability and game rules,
   * such as preventing placement in occupied cells or out-of-bounds locations.
   *
   * @param row the row index at which to place the card.
   * @param col the column index at which to place the card.
   * @param card the {@link ThreeTriosCard} to be placed on the grid.
   * @return true if the card was successfully placed at the specified location;
   *         false if the placement failed due to game rules or invalid coordinates.
   */
  boolean placeCard(int row, int col, ICard card);
}
