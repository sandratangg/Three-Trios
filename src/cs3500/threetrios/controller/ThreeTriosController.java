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
  //private final ThreeTriosView otherView; // Add this to the class
  public ThreeTriosController otherController;


  /**
   * Constructs a controller for the Three Trios game.
   *
   * @param model the game model
   * @param player the player
   * @param view the game view
   * @param playerColor the player's color
   */
  public ThreeTriosController(ThreeTriosGameModel model, Player player,
                              ThreeTriosView view, PlayerColor playerColor,
                              ThreeTriosController otherController) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.playerColor = playerColor;
    this.otherController = otherController; // Set the other controller
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
    System.out.println("Checking if " + playerColor + " can select a card...");
    if (!model.currentPlayerColor().equals(playerColor)) {
      System.out.println("It's not " + playerColor + "'s turn!");
      view.showMessage("Wait for your turn!");
      return;
    }

    this.selectedCard = card;
    System.out.println(playerColor + " selected card: " + card);
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

    // Check if it is this player's turn
    if (!model.currentPlayerColor().equals(playerColor)) {
      System.out.println("It's not " + playerColor + "'s turn!");
      view.showMessage("Wait for your turn!");
      return;
    }

    if (selectedCard != null) {
      try {
        System.out.println("Attempting to place card: " + selectedCard + " at (" + row + ", " + col + ")");
        model.placeCard(row, col, selectedCard);
        System.out.println("Placed card: " + selectedCard + " at (" + row + ", " + col + ")");

        // Clear selection
        selectedCard = null;

        // Refresh both views
        PlayerHandPanel redHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED);
        PlayerHandPanel blueHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE);

        // Link controllers to the new hand panels
        redHandPanel.setController(otherController);
        blueHandPanel.setController(this);

        view.setPlayerHand(redHandPanel, blueHandPanel);
        view.setGrid(new GameGridPanel(model));

        view.revalidate();
        view.repaint();

        view.showMessage("Card placed! Current player: " + model.currentPlayerColor());

        // Notify the other controller
        if (otherController != null) {
          otherController.notifyTurn();
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid move: " + e.getMessage());
      }
    } else {
      System.out.println("No card selected!");
    }
  }


  public void notifyTurn() {
    if (model.currentPlayerColor().equals(playerColor)) {
      System.out.println(playerColor + "'s turn! Updating hand and grid.");

      // Clear previous state
      this.selectedCard = null;
      this.selectedCoord = null;

      // Rebuild both hand panels
      PlayerHandPanel redHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED);
      PlayerHandPanel blueHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE);

      // Link the correct controllers to their respective panels
      redHandPanel.setController(otherController); // Link RED panel to RED controller
      blueHandPanel.setController(this);          // Link BLUE panel to BLUE controller

      // Update the view
      view.setPlayerHand(redHandPanel, blueHandPanel);

      // Rebuild the grid
      view.setGrid(new GameGridPanel(model));

      // Revalidate and repaint the view
      view.revalidate();
      view.repaint();

      view.showMessage("Your turn!");
    } else {
      System.out.println(playerColor + " is not active. Skipping turn notification.");
    }
  }


}















