package cs3500.threetrios.provider.view;

import java.awt.geom.Path2D;

/**
 * A path that represents a rectangular border that is drawn around cells or cards.
 */
public class RectBorder extends Path2D.Double {
  /**
   * Draws a border of a given size at the specified coordinates.
   *
   * @param x      the x-coordinate of the top left corner of the border. This assumes that the
   *               origin is at the top left of the screen.
   * @param y      the y-coordinate of the top left corner of the border. This assumes that the
   *               origin is at the top left of the screen.
   * @param width  the width of the border relative to the scaling of the coordinates of the screen
   * @param height the height of the border relative to the scaling of the coordinates of the
   *               screen
   */
  public RectBorder(int x, int y, int width, int height) {
    moveTo(x, y);
    lineTo(x + width, y);
    lineTo(x + width, y + height);
    lineTo(x, y + height);
    lineTo(x, y);
  }
}
