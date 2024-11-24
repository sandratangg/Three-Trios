package cs3500.threetrios.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;

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
  private final ReadOnlyThreeTriosModel model;

  /**
   * Constructs the ThreeTriosView with the given game model. It initializes the
   * grid panel, player hands, and a message label to display the current player's turn.
   *
   * @param model the read-only model of the game to display
   */
  public ThreeTriosView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");
    this.model = model;
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
   * Adds a listener to the player's hand panels for card selection.
   *
   * @param listener the MouseAdapter to handle card selection
   */
  public void addCardSelectionListener(MouseAdapter listener) {
    leftHandPanel.addMouseListener(listener);
    rightHandPanel.addMouseListener(listener);
  }

  /**
   * Adds a listener to the game grid panel for cell clicks.
   *
   * @param listener the MouseAdapter to handle cell clicks
   */
  public void addGridClickListener(MouseAdapter listener) {
    ((GameGridPanel) gridPanel).addMouseListener(listener);
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

  @Override
  public void highlightCard(int cardIndex, PlayerColor playerColor) {

  }

  @Override
  public void deselectCard() {

  }

  /**
   * Returns the card currently selected by the player, or null if no card is selected.
   *
   * @return the selected card, or null if none is selected
   */
  public ThreeTriosCard getSelectedCard() {
    if (model.getCurrentPlayerColor() == PlayerColor.RED) {
      return leftHandPanel.getSelectedCard();
    } else {
      return rightHandPanel.getSelectedCard();
    }
  }

  /**
   * Returns the current game grid panel.
   *
   * @return the game grid panel
   */
  public IGameGridPanel getGrid() {
    return gridPanel;
  }
}