package cs3500.threetrios.view;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents the game grid panel in the Three Trios game.
 * Displays the game board using a grid of {@link CellPanel} components,
 * allowing for cell highlighting and interaction.
 */
public class GameGridPanel extends JPanel implements IGameGridPanel {

  private final int rows;
  private final int cols;
  private final CellPanel[][] cells;

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
        int finalRow = row;
        int finalCol = col;

        // Add a mouse listener to handle cell clicks
        cells[row][col].addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            System.out.println("Cell clicked at: " + finalRow + "," + finalCol);
            // Handle cell click in the controller (if needed)
          }
        });
        add(cells[row][col]);
      }
    }
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
}
