package cs3500.threetrios.view;

import java.awt.*;
import java.awt.event.MouseAdapter;

import javax.swing.*;

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
    if (playerColor == PlayerColor.RED) {
      leftHandPanel.highlightCard(cardIndex);
    } else {
      rightHandPanel.highlightCard(cardIndex);
    }
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

  /**
   * Adds a mouse listener to the card selection panel.
   *
   * @param mouseAdapter the mouse adapter to add
   */
  public void addCardSelectionListener(MouseAdapter mouseAdapter) {
    leftHandPanel.addMouseListener(mouseAdapter);
    rightHandPanel.addMouseListener(mouseAdapter);
  }

  /**
   * TODO : Implement this method
   * Gets the selected card from the view.
   *
   * @return the selected card.
   */
  public ThreeTriosCard getSelectedCard() {
    if (leftHandPanel.getSelectedCardPanel() != null) {
      return leftHandPanel.getSelectedCardPanel().getCard();
    } else if (rightHandPanel.getSelectedCardPanel() != null) {
      return rightHandPanel.getSelectedCardPanel().getCard();
    } else if (gridPanel.getSelectedCardPanel() != null) {
      return gridPanel.getSelectedCardPanel().getCard();
    }
    return null;
  }


  /**
   * TODO : Implement this method
   * Gets the selected row from the view.
   *
   * @return the selected row.
   */
  public int getSelectedRow() {
    if (gridPanel.getSelectedCardPanel() != null) {
      return gridPanel.getSelectedCardPanel().getRow();
    }
    return -1;
  }

  /**
   * TODO : Implement this method
   * Gets the selected column from the view.
   *
   * @return the selected column.
   */
  public int getSelectedCol() {
    if (gridPanel.getSelectedCardPanel() != null) {
      return gridPanel.getSelectedCardPanel().getCol();
    }
    return -1;
  }

  /**
   * TODO : Implement this method
   * Show error message in the view.
   *
   * @param message the error message to display.
   */
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * TODO : Implement this method
   * Updates the turn indicator to show the current player's turn.
   *
   * @param currentPlayerColor the color of the current player.
   */
  public void updateTurnIndicator(PlayerColor currentPlayerColor) {
    // Update the label to show the current player's turn
    String playerText = "Current Player: " + currentPlayerColor.toString();
    messageLabel.setText(playerText);

    // Change the label's color to visually represent the player
    if (currentPlayerColor == PlayerColor.RED) {
      messageLabel.setForeground(Color.RED);
    } else if (currentPlayerColor == PlayerColor.BLUE) {
      messageLabel.setForeground(Color.BLUE);
    } else {
      messageLabel.setForeground(Color.BLACK); // Default color if needed
    }
  }

  @Override
  public void addGridClickListener(MouseAdapter mouseAdapter) {
    gridPanel.addCellClickListener(mouseAdapter);
  }


}
