package cs3500.threetrios;

import java.io.FileNotFoundException;
import java.util.List;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.view.ThreeTriosView;

/**
 * A mock intermediate view that sets up an intermediate game state
 */
public final class MockIntermediateView {
  public static void main(String[] args) throws FileNotFoundException {
    ThreeTriosGrid grid = ThreeTriosGrid.fromFile("docs/ExampleBoards/board2.txt");
    List<ThreeTriosCard> deck = ThreeTriosGameModel.readCardsFromFile("docs/ExampleCards/card_file2.txt");

    ThreeTriosGameModel model = new ThreeTriosGameModel(grid, deck);

    // Set up an intermediate game state by playing several moves
    createIntermediateState(model);

    // Display the game using the view
    ThreeTriosView view = new ThreeTriosView(model);
    view.setVisible(true);
  }

  /**
   * Creates an intermediate game state by placing cards for each player
   * at specific grid locations. This setup simulates that cards have been played
   * and the hands of both players have decreased.
   */
  private static void createIntermediateState(ThreeTriosGameModel model) {
    List<ThreeTriosCard> redHand = model.getPlayerHand(PlayerColor.RED);
    List<ThreeTriosCard> blueHand = model.getPlayerHand(PlayerColor.BLUE);

    // Are there are enough cards to place?
    if (redHand.size() < 2 || blueHand.size() < 2) {
      System.out.println("Error: Not enough cards in each player's hand.");
      return;
    }

    // Place cards in valid positions only
    model.placeCard(0, 0, redHand.get(0)); // Red player
    model.placeCard(1, 4, blueHand.get(0)); // Blue player
    model.placeCard(4, 0, redHand.get(1)); // Red player
    model.placeCard(3, 4, blueHand.get(1)); // Blue player
  }


}
