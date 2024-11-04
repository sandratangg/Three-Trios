package cs3500.threetrios.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class GameGridPanel extends JPanel implements IGameGridPanel {
  private int rows, cols;
  private CellPanel[][] cells;

  public GameGridPanel(ReadOnlyThreeTriosModel model) {
    this.rows = model.getGridHeight();
    this.cols = model.getGridWidth();
    setLayout(new GridLayout(rows, cols));
    cells = new CellPanel[rows][cols];

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col] = new CellPanel(model.getCellContents(row, col));
        int finalRow = row;
        int finalCol = col;
        cells[row][col].addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            System.out.println("Cell clicked at: " + finalRow + "," + finalCol);
            // Handle cell click in the controller
          }
        });
        add(cells[row][col]);
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
}
