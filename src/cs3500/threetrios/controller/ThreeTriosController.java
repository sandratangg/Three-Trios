package cs3500.threetrios.controller;

import java.util.Optional;

import javax.swing.JOptionPane;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.Posn;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.strategies.Move;
import cs3500.threetrios.view.GameGridPanel;
import cs3500.threetrios.view.PlayerHandPanel;
import cs3500.threetrios.view.ThreeTriosView;

/**
 * Controller for the Three Trios game.
 */
public class ThreeTriosController {
  private final ThreeTriosGameModel model;
  private final Player player;
  private final ThreeTriosView view;
  private final PlayerColor playerColor;
  private ThreeTriosCard selectedCard;
  private Posn selectedCoord;
  public ThreeTriosController otherController;

  /**
   * Constructs a controller for the Three Trios game.
   */
  public ThreeTriosController(ThreeTriosGameModel model, Player player,
                              ThreeTriosView view, PlayerColor playerColor,
                              ThreeTriosController otherController) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.playerColor = playerColor;
    this.otherController = otherController;
    this.selectedCard = null;
    this.selectedCoord = null;

    // Set the view to display which player it represents
    view.setPlayerRepresentation(playerColor);
  }

  /**
   * Activates the game controller.
   */
  public void activate() {
    while (!model.isGameOver()) {
      if (model.currentPlayerColor().equals(playerColor)) {
        if (!player.isHuman()) {
          Optional<Move> move = player.makeMove(model);
          move.ifPresent(m -> {
            try {
              model.placeCard(m.getRow(), m.getCol(), m.getCard());
              System.out.println(playerColor + " placed card: " + m.getCard());
              notifyOpponentOfPlacement();

              // Check if the game is over after the move
              if (model.isGameOver()) {
                notifyGameOver();
                return;
              }

              notifyTurn();

            } catch (IllegalArgumentException e) {
              System.out.println("Invalid AI move: " + e.getMessage());
            }
          });
        }
      }
    }
    notifyGameOver();
  }


  /**
   * Handles card selection.
   *
   * @param card the selected card
   */
  public void onCardSelected(ThreeTriosCard card) {
    if (!model.currentPlayerColor().equals(playerColor)) {
      showErrorDialog("Wait for your turn!");
      return;
    }

    if (!model.getPlayerHand(playerColor).contains(card)) {
      showErrorDialog("Invalid move: You don't own this card!");
      return;
    }

    this.selectedCard = card;
    view.showMessage(playerColor + " selected card: " + card);
  }

  /**
   * Handles a grid cell click.
   *
   * @param row the row index
   * @param col the column index
   */
  public void onGridCellClicked(int row, int col) {
    this.selectedCoord = new Posn(row, col);

    if (!model.currentPlayerColor().equals(playerColor)) {
      showErrorDialog("Wait for your turn!");
      return;
    }

    if (selectedCard != null) {
      try {
        model.placeCard(row, col, selectedCard);
        selectedCard = null;

        // Update the grid for the current view immediately
        GameGridPanel gridPanel = view.getGrid();
        gridPanel.updateGrid(model);

        // Repaint and refresh the current view
        view.revalidate();
        view.repaint();

        // Check if the game is over
        if (model.isGameOver()) {
          notifyOpponentOfPlacement(); // Notify the opponent
          notifyGameOver();            // Show the winner on both views
          return;
        }

        // Notify the other controller/view
        notifyOpponentOfPlacement();
        notifyTurn();        // Notify the next player

      } catch (IllegalArgumentException e) {
        showErrorDialog("Invalid move: " + e.getMessage());
      }
    } else {
      showErrorDialog("No card selected!");
    }
  }

  /**
   * Displays a dialog for invalid/illegal moves.
   *
   * @param message the error message
   */
  private void showErrorDialog(String message) {
    JOptionPane.showMessageDialog(view, message, "Invalid Move", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Notifies both players of the game over condition and shows the winner's color.
   */
  private void notifyGameOver() {
    // Determine the winner
    String winner = model.determineWinner();
    String message;

    if (winner.equals("TIE")) {
      message = "It's a tie!";
    } else {
      message = winner + " wins!";
    }

    // Show game over dialog for both views
    JOptionPane.showMessageDialog(view, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

    if (otherController != null) {
      otherController.showGameOverMessage(message);
    }
  }

  /**
   * Displays the game-over message for this controller's view.
   *
   * @param message the game-over message
   */
  public void showGameOverMessage(String message) {
    JOptionPane.showMessageDialog(view, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Notifies the opponent's controller to update their view.
   */
  public void notifyOpponentOfPlacement() {
    if (otherController != null) {
      otherController.updateView();
    }
  }

  /**
   * Updates the current controller's view.
   */
  public void updateView() {
    // Rebuild the grid for the current view
    GameGridPanel gridPanel = view.getGrid();
    gridPanel.updateGrid(model);

    // Rebuild the hand panels for the current view
    PlayerHandPanel redHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED);
    PlayerHandPanel blueHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE);

    redHandPanel.setController(playerColor == PlayerColor.RED ? this : otherController);
    blueHandPanel.setController(playerColor == PlayerColor.BLUE ? this : otherController);

    view.setPlayerHand(redHandPanel, blueHandPanel);

    // Revalidate and repaint the view
    view.revalidate();
    view.repaint();
  }

  /**
   * Notifies the player when it's their turn.
   */
  public void notifyTurn() {
    if (model.currentPlayerColor().equals(playerColor)) {
      this.selectedCard = null;
      this.selectedCoord = null;

      PlayerHandPanel redHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED);
      PlayerHandPanel blueHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE);

      redHandPanel.setController(playerColor == PlayerColor.RED ? this : otherController);
      blueHandPanel.setController(playerColor == PlayerColor.BLUE ? this : otherController);

      view.setPlayerHand(redHandPanel, blueHandPanel);
      view.setGrid(new GameGridPanel(model));
      view.revalidate();
      view.repaint();

      view.showMessage("Your turn!");
    }
  }

  public PlayerColor getPlayerColor() {
    return model.currentPlayerColor();
  }

  public ReadOnlyThreeTriosModel getModel() {
    return model;
  }
}
