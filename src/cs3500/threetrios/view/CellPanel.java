package cs3500.threetrios.view;

import javax.swing.*;
import java.awt.*;

import cs3500.threetrios.model.ICell;

public class CellPanel extends JPanel {
  private boolean isHole;
  private boolean isHighlighted = false;

  public CellPanel(ICell cell) {
    this.isHole = cell.isHole();

    // Set the initial background color based on the cell type
    setBackground(isHole ? Color.GRAY : Color.YELLOW);
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
  }

  public void setHighlighted(boolean highlighted) {
    isHighlighted = highlighted;
    setBorder(highlighted ? BorderFactory.createLineBorder(Color.RED, 2)
            : BorderFactory.createLineBorder(Color.BLACK));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (isHole) {
      setBackground(Color.GRAY);
    } else {
      setBackground(Color.YELLOW);
    }
  }
}
