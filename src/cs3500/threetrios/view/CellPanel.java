package cs3500.threetrios.view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosCell;

/**
 * Represents a panel for an individual cell on the game grid in the Three Trios game.
 * The cell may display a card owned by a player, be empty, or represent a hole on the board.
 */
public class CellPanel extends JPanel {

  private ThreeTriosCard card;

  /**
   * Constructs a CellPanel based on the given cell's contents, location, and the game model.
   * Initializes the panel as a player card, empty cell, or hole based on the cell's properties.
   *
   * @param cell  the cell's data used to determine its contents and properties
   * @param model the read-only model of the game to retrieve player ownership information
   * @param row   the row index of the cell on the game grid
   * @param col   the column index of the cell on the game grid
   */
  public CellPanel(ThreeTriosCell cell, ReadOnlyThreeTriosModel model, int row, int col) {
    boolean isHole = cell.isHole();

    if (!cell.isEmpty() && !isHole) {
      System.out.println("Row: " + row + ", Col: " + col);
      createPlayerCardPanel(cell, model, row, col);
    } else {
      setBackground(isHole ? Color.GRAY : Color.YELLOW);
    }

    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    card = null;
  }

  /**
   * Constructs a CellPanel to display a card with its directional attack values and
   * player-specific background color.
   *
   * @param card  the card displayed in this panel
   * @param color the color representing the player who owns the card
   */
  public CellPanel(ThreeTriosCard card, PlayerColor color) {
    setLayout(new BorderLayout());
    setBackground(color.equals(PlayerColor.RED) ? Color.PINK : Color.CYAN);

    JLabel northLabel = new JLabel(
            String.valueOf(card.attack(Direction.NORTH)), JLabel.CENTER);
    JLabel eastLabel = new JLabel(
            String.valueOf(card.attack(Direction.EAST)), JLabel.CENTER);
    JLabel southLabel = new JLabel(
            String.valueOf(card.attack(Direction.SOUTH)), JLabel.CENTER);
    JLabel westLabel = new JLabel(
            String.valueOf(card.attack(Direction.WEST)), JLabel.CENTER);

    add(northLabel, BorderLayout.NORTH);
    add(eastLabel, BorderLayout.EAST);
    add(southLabel, BorderLayout.SOUTH);
    add(westLabel, BorderLayout.WEST);

    this.card = card;
  }

  /**
   * Initializes the panel to display the card of the specified player, including
   * directional attack values and player-specific background color.
   *
   * @param cell  the cell containing the card data
   * @param model the read-only model to retrieve ownership information
   * @param row   the row index of the cell on the grid
   * @param col   the column index of the cell on the grid
   */
  private void createPlayerCardPanel(
          ThreeTriosCell cell, ReadOnlyThreeTriosModel model, int row, int col) {
    setLayout(new BorderLayout());
    setBackground(model.getCardOwner(row, col).equals(PlayerColor.RED) ? Color.PINK : Color.CYAN);

    JLabel northLabel = new JLabel(
            String.valueOf(cell.getCard().attack(Direction.NORTH)), JLabel.CENTER);
    JLabel eastLabel = new JLabel(
            String.valueOf(cell.getCard().attack(Direction.EAST)), JLabel.CENTER);
    JLabel southLabel = new JLabel(
            String.valueOf(cell.getCard().attack(Direction.SOUTH)), JLabel.CENTER);
    JLabel westLabel = new JLabel(
            String.valueOf(cell.getCard().attack(Direction.WEST)), JLabel.CENTER);

    add(northLabel, BorderLayout.NORTH);
    add(eastLabel, BorderLayout.EAST);
    add(southLabel, BorderLayout.SOUTH);
    add(westLabel, BorderLayout.WEST);
  }

  /**
   * Sets the highlighted state of the cell panel. Highlights the cell with a red border if
   * selected, otherwise displays a black border.
   *
   * @param highlighted true to highlight the cell, false to remove highlight
   */
  public void setHighlighted(boolean highlighted) {
    setBorder(highlighted ? BorderFactory.createLineBorder(Color.RED, 2)
            : BorderFactory.createLineBorder(Color.BLACK));
  }

  public void updateCell(ThreeTriosCell cell, ReadOnlyThreeTriosModel model, int row, int col) {
    removeAll(); // Clear previous content
    if (!cell.isEmpty() && !cell.isHole()) {
      createPlayerCardPanel(cell, model, row, col);
    } else {
      setBackground(cell.isHole() ? Color.GRAY : Color.YELLOW);
    }
    revalidate();
    repaint();
  }


  /**
   * Overrides the paintComponent method to preserve any custom background color and
   * ensure the player's color remains visible.
   *
   * @param g the Graphics object to protect the component's rendering
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Background color is set during initialization in the constructor or createPlayerCardPanel
  }

  protected ThreeTriosCard getCard() {
    return this.card;
  }
}
