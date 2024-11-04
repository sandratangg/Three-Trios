package cs3500.threetrios.view;

import javax.swing.*;
import java.awt.*;

import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ICell;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCell;

public class CellPanel extends JPanel {
  private boolean isHole;
  private boolean isHighlighted = false;

  public CellPanel(ThreeTriosCell cell, ReadOnlyThreeTriosModel model, int row, int col) {
    this.isHole = cell.isHole();

    if (!cell.isEmpty() & !isHole) {
      setLayout(new BorderLayout());
      setBackground(model.getCardOwner(row, col).equals(PlayerColor.RED) ? Color.PINK : Color.CYAN);
      JLabel northLabel = new JLabel(String.valueOf(cell.getCard().attack(Direction.NORTH)), SwingConstants.CENTER);
      JLabel eastLabel = new JLabel(String.valueOf(cell.getCard().attack(Direction.EAST)), SwingConstants.CENTER);
      JLabel southLabel = new JLabel(String.valueOf(cell.getCard().attack(Direction.SOUTH)), SwingConstants.CENTER);
      JLabel westLabel = new JLabel(String.valueOf(cell.getCard().attack(Direction.WEST)), SwingConstants.CENTER);

      add(northLabel, BorderLayout.NORTH);
      add(eastLabel, BorderLayout.EAST);
      add(southLabel, BorderLayout.SOUTH);
      add(westLabel, BorderLayout.WEST);
    } else {
      setBackground(isHole ? Color.GRAY : Color.YELLOW);
    }

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
