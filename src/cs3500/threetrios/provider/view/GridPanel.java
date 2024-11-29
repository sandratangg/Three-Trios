package cs3500.threetrios.provider.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.function.BiConsumer;

import cs3500.threetrios.provider.model.ICell;
import cs3500.threetrios.provider.model.CellType;
import cs3500.threetrios.provider.model.IGrid;
import cs3500.threetrios.provider.model.IThreeTriosCard;
import cs3500.threetrios.provider.model.PlayerColor;


/**
 * Represents the panel in the view that displays information about the current state of the grid:
 * both the cards placed on it and the types of cells.
 */
public class GridPanel extends AbstractGridPanel {
  private IGrid grid;

  // Converts from physical coordinates to zero-based grid row and column model coordinates.
  private AffineTransform getPhysicalToModel() {
    AffineTransform transform = new AffineTransform();
    transform.scale(1.0 / getCellWidth(), 1.0 / getCellHeight());
    return transform;
  }

  // Returns the corresponding grid position based on a Point2D object. The first value in the pair
  // represents the grid row index, and the second represents the grid column index
  private Pair<Integer, Integer> getGridPosFromPoint2D(Point2D point) {
    AffineTransform physicalToModel = getPhysicalToModel();
    Point2D modelPt = physicalToModel.transform(point, null);

    // This int cast effectively floors the coordinates by truncating the decimal
    return new Pair<Integer, Integer>((int) modelPt.getY(), (int) modelPt.getX());
  }

  private double getCellWidth() {
    return getWidth() / grid.getWidth();
  }

  private double getCellHeight() {
    return getHeight() / grid.getHeight();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();

    int gridHeight = grid.getHeight();
    int gridWidth = grid.getWidth();
    int cellWidth = (int) getCellWidth();
    int cellHeight = (int) getCellHeight();

    int fontSize = (cellWidth + cellHeight) / 10;
    int attackValHorizontalOffset = cellWidth / 5;
    int attackValVerticalOffset = cellHeight / 5;

    for (int rowIdx = 0; rowIdx < gridHeight; rowIdx++) {
      // Y position of the cell's top left corner
      int cellPosY = rowIdx * cellHeight;
      for (int colIdx = 0; colIdx < gridWidth; colIdx++) {
        // X position of cell's top left corner
        int cellPosX = colIdx * cellWidth;
        //  Draw border
        g2d.setColor(ViewColor.BORDER.color());
        RectBorder cellBorder = new RectBorder(cellPosX, cellPosY, cellWidth, cellHeight);
        g2d.draw(cellBorder);
        // Draw cards/cells
        ICell cell = grid.getCell(rowIdx, colIdx);
        if (cell.getType() == CellType.CARD_CELL) {
          if (cell.isOccupiedCardCell()) { // Draw the card if the card cell has a card
            IThreeTriosCard card = cell.getCard();
            PlayerColor cardOwner = cell.getCardOwner();
            DrawCardHelpers.drawCard(
                g2d, card, cellPosX, cellPosY, cellWidth, cellHeight, fontSize,
                attackValHorizontalOffset, attackValVerticalOffset, cardOwner
            );
          } else { // Fill it with the card cell color if it doesn't have a card
            g2d.setColor(ViewColor.CARD_CELL.color());
            g2d.fillRect(cellPosX, cellPosY, cellWidth, cellHeight);
          }
        } else { // Fill it with the hole color if it is a card hole
          g2d.setColor(ViewColor.HOLE.color());
          g2d.fillRect(cellPosX, cellPosY, cellWidth, cellHeight);
        }
      }
    }
  }

  @Override
  public void setCellOnClick(BiConsumer<Integer, Integer> onClick) {
    Objects.requireNonNull(onClick, "onClick cannot be null");

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Point2D evtPt = e.getPoint();
        Pair<Integer, Integer> gridPos = getGridPosFromPoint2D(evtPt);
        int gridRow = gridPos.firstVal();
        int gridCol = gridPos.secondVal();
        onClick.accept(gridRow, gridCol);
      }
    });
  }

  @Override
  void setGrid(IGrid grid) {
    this.grid = Objects.requireNonNull(grid, "Grid cannot be null");
  }
}
