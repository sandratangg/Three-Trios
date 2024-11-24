package cs3500.threetrios.view;


import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.Posn;
import cs3500.threetrios.controller.ThreeTriosController;


/**
 * Represents a panel for the game grid in the Three Trios game, containing all cells on the board.
 */
public class GameGridPanel extends JPanel implements IGameGridPanel {


  private final int rows;
  private final int cols;
  private final CellPanel[][] cells;
  private Posn selectedCoord = null;
  private ThreeTriosController controller;


  /**
   * Constructs a GameGridPanel based on the given game model, initializing the grid's cells.
   *
   * @param model the read-only model of the game to retrieve cell contents and properties
   */
  public GameGridPanel(ReadOnlyThreeTriosModel model) {
    this.rows = model.getGridHeight();
    this.cols = model.getGridWidth();
    setLayout(new GridLayout(rows, cols));
    cells = new CellPanel[rows][cols];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col] = new CellPanel(model.getCellContents(row, col), model, row, col);
        int finalRow = row;
        int finalCol = col;

        cells[row][col].addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            selectedCoord = new Posn(finalRow, finalCol);
            if (controller != null) {
              controller.onGridCellClicked(finalRow, finalCol);
            }
          }
        });

        add(cells[row][col]);
      }
    }

    revalidate();
    repaint();
  }



  public void setController(ThreeTriosController controller) {
    this.controller = controller;
    System.out.println("Controller set for GameGridPanel.");
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int finalRow = row;
        int finalCol = col;
        cells[row][col].addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            selectedCoord = new Posn(finalRow, finalCol);
            if (controller != null) {
              controller.onGridCellClicked(finalRow, finalCol);
            }
          }
        });
      }
    }
  }






  @Override
  public void highlightCell(int row, int col) {
    cells[row][col].setHighlighted(true);
  }


  @Override
  public void clearHighlight() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col].setHighlighted(false);
      }
    }
  }


  @Override
  public CellPanel getSelectedCardPanel() {
    return null;
  }


  @Override
  public void addCellClickListener(MouseAdapter mouseAdapter) {
    // method not used in this implementation
    // it should add a mouse listener to each cell in the grid
  }


  public Posn getSelectedCoord() {
    return this.selectedCoord;
  }
}
