package cs3500.threetrios.provider.view;

import java.util.function.BiConsumer;

import javax.swing.JPanel;

import cs3500.threetrios.provider.model.IGrid;

/**
 * Represents a panel that displays the cells and cards that have currently been placed on the
 * grid.
 */
public abstract class AbstractGridPanel extends JPanel {
  /**
   * Sets a callback that is called when a cell on the grid is selected by the user.
   *
   * @param onClick the function that is called when a cell is selected. Note that the first value
   *                of the consumer is the zero-based index of the row clicked on, and the second is
   *                the zero-based column of the row clicked on. These indices are exactly as they
   *                are in the model, where the 0th row is the top-most row, and the 0th row is the
   *                bottom-most row.
   *
   * @throws NullPointerException if onClick is null
   */
  abstract void setCellOnClick(BiConsumer<Integer, Integer> onClick);

  /**
   * Supplies the grid with an updated version of the model's grid.
   *
   * @param grid the grid object
   *
   * @throws NullPointerException if grid is null
   */
  abstract void setGrid(IGrid grid);
}
