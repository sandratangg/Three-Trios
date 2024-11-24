package cs3500.threetrios.controller;

import cs3500.threetrios.model.*;
import cs3500.threetrios.view.*;

public class ThreeTriosController {
  private final ThreeTriosGameModel model;
  private final Player player;
  private final ThreeTriosView view;
  private final PlayerColor pColor;
  private ThreeTriosCard selectedCard;
  private Posn selectedCoord;

  public ThreeTriosController(ThreeTriosGameModel model, Player player, ThreeTriosView view, PlayerColor pColor) {
    this.model = model;
    this.player = player;
    this.view = view;
    this.pColor = pColor;
    this.selectedCard = null;
    this.selectedCoord = null;
  }

  public void activate() {
    while (!model.isGameOver()) {
      if (model.currentPlayerColor().equals(pColor)) {
        if (!player.isHuman()) {
          player.makeMove(model);
        }
      }
    }
    view.showMessage("Game over! " + model.determineWinner());
  }

  public void onCardSelected(ThreeTriosCard card) {
    this.selectedCard = card;
    System.out.println("Card selected: " + card);
  }

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
