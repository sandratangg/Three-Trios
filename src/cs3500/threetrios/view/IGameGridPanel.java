package cs3500.threetrios.view;

import java.awt.event.MouseAdapter;


/**
 * Represents the interface for the game grid panel in the Three Trios game.
 * Provides methods to highlight specific cells on the grid and clear any highlights.
 */
public interface IGameGridPanel {

  /**
   * Highlights a specific cell on the game grid at the given row and column.
   * The highlighted cell will be visually distinguished (e.g., with a colored border).
   *
   * @param row the row index of the cell to highlight
   * @param col the column index of the cell to highlight
   */
  void highlightCell(int row, int col);

  /**
   * Clears any highlights on the game grid, removing any visual indications.
   */
  void clearHighlight();

  /**
   * Gets the panel representing the currently selected card on the game grid.
   *
   * @return the panel representing the selected card
   */
  CellPanel getSelectedCardPanel();

  /**
   * Adds a mouse listener to the game grid panel to handle cell clicks.
   *
   * @param mouseAdapter the mouse adapter to handle cell clicks
   */
  void addCellClickListener(MouseAdapter mouseAdapter);
}
