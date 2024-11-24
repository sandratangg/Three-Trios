package cs3500.threetrios.controller;


import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.Posn;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.view.GameGridPanel;
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

  /**
   * Constructs a controller for the Three Trios game.
   *
   * @param model the game model
   * @param player the player
   * @param view the game view
   * @param playerColor the player's color
   */
  public ThreeTriosController(ThreeTriosGameModel model, Player player,
                              ThreeTriosView view, PlayerColor playerColor) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.playerColor = playerColor;
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
   * Prints the selected card.
   *
   * @param card the card selected
   */
  public void onCardSelected(ThreeTriosCard card) {
    this.selectedCard = card;
    System.out.println("Card selected: " + card);
  }

  /**
   * Handles the event when a grid cell is clicked.
   * Updates the selected coordinates and attempts to place the selected card
   * at the specified grid cell. If a card is successfully placed, the grid is
   * refreshed and a message is displayed. If no card is selected or the move
   * is invalid, appropriate messages are displayed.
   *
   * @param row the row index of the clicked cell
   * @param col the column index of the clicked cell
   */
  public void onGridCellClicked(int row, int col) {
    this.selectedCoord = new Posn(row, col);
    System.out.println("Cell clicked at: " + row + ", " + col);

    if (selectedCard != null) {
      try {
        model.placeCard(row, col, selectedCard);
        System.out.println("Placed card: " + selectedCard + " at (" + row + ", " + col + ")");
        selectedCard = null; // Clear selection after placing
        view.setGrid(new GameGridPanel(model)); // Refresh grid
        view.showMessage("Card placed! Current player: " + model.currentPlayerColor());
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid move: " + e.getMessage());
      }
    } else {
      System.out.println("No card selected!");
    }
  }

}
