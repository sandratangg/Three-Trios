package cs3500.threetrios.provider.view;

import java.awt.Color;

/**
 * Represents the different colors used in the ThreeTriosView.
 */
enum ViewColor {
  RED_CARD(new Color(255, 171, 173)),
  BLUE_CARD(new Color(72, 172, 255)),
  BORDER(new Color(130, 134, 134)),
  CARD_CELL(new Color(212,199,0)),
  HOLE(new Color(186,191,191));

  private final Color color;

  ViewColor(Color color) {
    this.color = color;
  }

  /**
   * Returns the color stored by the enum.
   *
   * @return the color stored by the enum
   */
  public Color color() {
    return color;
  }
}