package cs3500.threetrios.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents the main view for the Three Trios game, which extends JFrame and
 * implements the IThreeTriosView interface. It displays the game grid, player
 * hands, and messages about the current state of the game.
 */
public class ThreeTriosView extends JFrame implements IThreeTriosView {

  private IGameGridPanel gridPanel;
  private PlayerHandPanel leftHandPanel;
  private PlayerHandPanel rightHandPanel;
  private JLabel messageLabel;

  /**
   * Constructs the ThreeTriosView with the given game model. It initializes the
   * grid panel, player hands, and a message label to display the current player's turn.
   *
   * @param model the read-only model of the game to display
   */
  public ThreeTriosView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");
    setLayout(new BorderLayout());

    // Set up the game grid panel
    gridPanel = new GameGridPanel(model);
    add((Component) gridPanel, BorderLayout.CENTER);

    // Set up player hand panels
    leftHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED);
    rightHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE);
    add((Component) leftHandPanel, BorderLayout.WEST);
    add((Component) rightHandPanel, BorderLayout.EAST);

    // Initialize message label to display the current player's turn
    messageLabel = new JLabel("Current player: RED");
    add(messageLabel, BorderLayout.NORTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  /**
   * Sets a new game grid panel.
   *
   * @param gridPanel the new game grid panel to set
   */
  @Override
  public void setGrid(IGameGridPanel gridPanel) {
    this.gridPanel = gridPanel;
  }

  /**
   * Sets the player hand panels for both players.
   *
   * @param leftHandPanel  the hand panel for the RED player
   * @param rightHandPanel the hand panel for the BLUE player
   */
  public void setPlayerHand(PlayerHandPanel leftHandPanel, PlayerHandPanel rightHandPanel) {
    this.leftHandPanel = leftHandPanel;
    this.rightHandPanel = rightHandPanel;
  }

  /**
   * Displays a message in the message label.
   *
   * @param message the message to display
   */
  @Override
  public void showMessage(String message) {
    messageLabel.setText(message);
  }

  /**
   * Highlights a card in the specified player's hand.
   *
   * @param cardIndex   the index of the card to highlight
   * @param playerColor the color of the player (RED or BLUE)
   */
  @Override
  public void highlightCard(int cardIndex, PlayerColor playerColor) {
    /* Uncomment to enable highlighting
    if (playerColor == PlayerColor.RED) {
      leftHandPanel.highlightCard(cardIndex);
    } else {
      rightHandPanel.highlightCard(cardIndex);
    }
    */
  }

  /**
   * Deselects any currently highlighted card in both players' hands.
   */
  @Override
  public void deselectCard() {
    /* Uncomment to enable deselecting
    leftHandPanel.deselectCard();
    rightHandPanel.deselectCard();
    */
  }
}
