package cs3500.threetrios.model;

/**
 * Represents the game grid for the Three Trios game. The grid manages the
 * placement of cards within cells and keeps track of the overall grid state.
 * This interface provides methods to place cards at specific coordinates and
 * check if the grid is fully occupied. Implementations of this interface
 * are responsible for defining the grid's structure and enforcing placement rules.
 */
public interface IGrid {

  /**
   * Attempts to place a card at the specified row and column within the grid.
   * Placement may be restricted based on game rules or grid state,
   * such as only allowing placement in empty cells. Implementations
   * should validate coordinates and check if the cell is available.
   *
   * @param row the row index at which to place the card.
   * @param col the column index at which to place the card.
   * @param card the {@link ICard} to be placed at the specified cell.
   * @return true if the card was successfully placed; false if the cell is occupied
   *         or if the coordinates are invalid.
   */
  void placeCard(int row, int col, ICard card);

  /**
   * Checks if the grid is fully occupied with no empty cells remaining.
   * A full grid signifies that no further moves can be made, potentially
   * indicating an end state for the game. This method allows for efficient
   * assessment of grid capacity, impacting gameplay decisions and flow.
   *
   * @return true if the grid has no empty cells; false if at least one cell is empty.
   */
  boolean isGridFull();
}

