package cs3500.threetrios.provider.view;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.provider.model.AttackDirection;
import cs3500.threetrios.provider.model.IThreeTriosCard;

/**
 * Helper methods used to draw cards.
 */
public class DrawCardHelpers {
  private static final AttackDirection[] ATTACK_DIRECTIONS =
          new AttackDirection[]{
                  AttackDirection.NORTH, AttackDirection.EAST, AttackDirection.SOUTH, AttackDirection.WEST
          };

  /**
   * Helper function that draws a card.
   *
   * @param g2d                       the Graphics2D object used to draw
   * @param card                      the card object to be drawn
   * @param xPos                      the x position of the top left corner of the card
   * @param yPos                      the y position of the top left corner of the card
   * @param cardWidth                 the width of the card
   * @param cardHeight                the height of the card
   * @param fontSize                  the font size of the card's attack values
   * @param attackValHorizontalOffset the horizontal offset of the attack values from the card's
   *                                  center
   * @param attackValVerticalOffset   the vertical offset of the attack values from the card's
   *                                  center
   * @param owner                     the owner of the card
   */
  public static void drawCard(
          Graphics2D g2d,
          IThreeTriosCard card,
          int xPos,
          int yPos,
          int cardWidth,
          int cardHeight,
          int fontSize,
          int attackValHorizontalOffset,
          int attackValVerticalOffset,
          PlayerColor owner
  ) {
    Color cardColor =
            owner == PlayerColor.RED ? ViewColor.RED_CARD.color() : ViewColor.BLUE_CARD.color();

    // Set font
    Font font = new Font("Arial", Font.BOLD, fontSize);
    g2d.setFont(font);

    // Draw card background
    g2d.setColor(cardColor);
    g2d.fillRect(xPos, yPos, cardWidth, cardHeight);

    // Draw attack values
    g2d.setColor(Color.BLACK);
    for (AttackDirection direction : ATTACK_DIRECTIONS) {
      FontMetrics fm = g2d.getFontMetrics(font);
      int attackValue = card.getAttackValue(direction);
      String valueString = getAttackValString(attackValue);
      Rectangle2D r = fm.getStringBounds(valueString, g2d);

      int cardCenterX = xPos + (cardWidth - (int) r.getWidth()) / 2;
      int cardCenterY = yPos + (cardHeight + (int) r.getHeight()) / 2;

      switch (direction) {
        case NORTH:
          g2d.drawString(valueString, cardCenterX, cardCenterY - attackValVerticalOffset);
          break;
        case EAST:
          g2d.drawString(valueString, cardCenterX + attackValHorizontalOffset, cardCenterY);
          break;
        case SOUTH:
          g2d.drawString(valueString, cardCenterX, cardCenterY + attackValVerticalOffset);
          break;
        default: // West
          g2d.drawString(valueString, cardCenterX - attackValHorizontalOffset, cardCenterY);
          break;
      }
    }
  }

  private static String getAttackValString(int attackVal) {
    if (attackVal == 10) {
      return "A";
    }

    return Integer.toString(attackVal);
  }
}
