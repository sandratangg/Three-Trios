package cs3500.threetrios.controller;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.Posn;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
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
   *
   * @param model        the game model
   * @param player       the player
   * @param view         the game view
   * @param playerColor  the player's color
   * @param otherController the opponent's controller
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
  }

  /**
   * Activates the game controller.
   */
  public void activate() {
    while (!model.isGameOver()) {
      if (model.currentPlayerColor().equals(playerColor)) {
        if (!player.isHuman()) {
          player.makeMove(model);
        }
      }
    }
    view.showMessage("Game over! " + model.determineWinner());
  }

  /**
   * Handles card selection.
   *
   * @param card the selected card
   */
  public void onCardSelected(ThreeTriosCard card) {
    if (!model.currentPlayerColor().equals(playerColor)) {
      view.showMessage("Wait for your turn!");
      return;
    }

    if (!model.getPlayerHand(playerColor).contains(card)) {
      view.showMessage("Invalid move: You don't own this card!");
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
      view.showMessage("Wait for your turn!");
      return;
    }

    if (selectedCard != null) {
      try {
        model.placeCard(row, col, selectedCard);
        selectedCard = null;

        // Update the grid for the current view
        GameGridPanel gridPanel = view.getGrid();
        gridPanel.updateGrid(model);

        // Repaint and refresh the current view
        view.revalidate();
        view.repaint();

        // Notify the other controller/view
        notifyOpponentOfPlacement();

        // Switch to the next player's turn
        //model.switchTurn();  // Ensure the model updates the current player
        notifyTurn();        // Notify the next player

      } catch (IllegalArgumentException e) {
        view.showMessage("Invalid move: " + e.getMessage());
      }
    } else {
      view.showMessage("No card selected!");
    }
  }

  /**
   * Notifies the opponent's controller to update their view.
   */
  public void notifyOpponentOfPlacement() {
    if (otherController != null) {
      System.out.println("Notifying opponent's view of placement.");
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
    System.out.println("Updated view for player: " + playerColor);
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
}
