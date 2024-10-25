package cs3500.threetrios.model;

/**
 * Represents the ThreeTrios game.
 */
public interface ThreeTrios {
  //All methods that are needed for the ThreeTrios game in every model

  /**
   * Method to place a card on the grid.
   * @param row the row to place the card
   * @param col the column to place the card
   * @return true or false whether the card was placed
   */
  boolean placeCard(int row, int col, ThreeTriosCard card);
}
