package cs3500.threetrios.view;

import java.awt.event.MouseAdapter;

import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosCell;

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

  CellPanel getSelectedCardPanel();

  void addCellClickListener(MouseAdapter mouseAdapter);
}
