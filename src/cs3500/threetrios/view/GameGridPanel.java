package cs3500.threetrios.view;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCell;

/**
 * Represents the game grid panel in the Three Trios game.
 * Displays the game board using a grid of {@link CellPanel} components,
 * allowing for cell highlighting and interaction.
 */
public class GameGridPanel extends JPanel implements IGameGridPanel {

  private final int rows;
  private final int cols;
  private final CellPanel[][] cells;
  private CellPanel selectedCellPanel = null; // Tracks the currently selected cell

  /**
   * Constructs a GameGridPanel with the given read-only game model.
   * Initializes the grid based on the model's dimensions and populates
   * it with cells that represent the contents of the game board.
   *
   * @param model the read-only model of the game to display
   */
  public GameGridPanel(ReadOnlyThreeTriosModel model) {
    this.rows = model.getGridHeight();
    this.cols = model.getGridWidth();
    setLayout(new GridLayout(rows, cols));
    cells = new CellPanel[rows][cols];

    // Initialize the cells and set up mouse listeners for each cell
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col] = new CellPanel(model.getCellContents(row, col), model, row, col);
        CellPanel cell = cells[row][col];

        // Add a mouse listener to handle cell clicks
        int finalRow = row;
        int finalCol = col;
        cell.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            handleCellClick(cell, finalRow, finalCol);
          }
        });
        add(cell);
      }
    }
  }

  /**
   * Handles the selection of a grid cell. Highlights the clicked cell
   * and deselects any previously selected cell.
   *
   * @param cell the cell that was clicked
   * @param row  the row index of the clicked cell
   * @param col  the column index of the clicked cell
   */
  private void handleCellClick(CellPanel cell, int row, int col) {
    // Deselect previous cell
    if (selectedCellPanel != null) {
      selectedCellPanel.setHighlighted(false);
    }

    // Update the selected cell
    selectedCellPanel = cell;
    selectedCellPanel.setHighlighted(true);

    // Internal handling of the selection
    System.out.println("Cell selected: " + row + ", " + col);
  }


  /**
   * Highlights a specific cell on the game grid.
   *
   * @param row the row index of the cell to highlight
   * @param col the column index of the cell to highlight
   */
  @Override
  public void highlightCell(int row, int col) {
    cells[row][col].setHighlighted(true);
  }

  /**
   * Clears any highlights on all cells of the game grid.
   */
  @Override
  public void clearHighlight() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col].setHighlighted(false);
      }
    }
  }

  /**
   * Returns the currently selected cell.
   *
   * @return the currently selected cell, or null if no cell is selected
   */
  @Override
  public CellPanel getSelectedCardPanel() {
    if (selectedCellPanel != null) {
      return selectedCellPanel;
    }
    return null;
  }

  /**
   * Resets the current selection, deselecting the selected cell if any.
   */
  public void resetSelection() {
    if (selectedCellPanel != null) {
      selectedCellPanel.setHighlighted(false);
      selectedCellPanel = null;
    }
  }

  /**
   * Adds a MouseAdapter to all cells in the grid, allowing external listeners
   * to handle cell click events.
   *
   * @param mouseAdapter the MouseAdapter to add to each cell
   */
  public void addCellClickListener(MouseAdapter mouseAdapter) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col].addMouseListener(mouseAdapter);
      }
    }
  }

}
