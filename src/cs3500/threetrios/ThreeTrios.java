package cs3500.threetrios;

import java.io.FileNotFoundException;
import java.util.List;

import cs3500.threetrios.controller.AIPlayer;
import cs3500.threetrios.controller.FileReader;
import cs3500.threetrios.controller.HumanPlayer;
import cs3500.threetrios.controller.Player;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.strategies.CornerPreferenceStrategy;
import cs3500.threetrios.strategies.MaximizeFlipsStrategy;
import cs3500.threetrios.view.ThreeTriosView;

public final class ThreeTrios {
  public static void main(String[] args) throws FileNotFoundException {
    if (args.length != 2) {
      System.out.println("Usage: java ThreeTrios <player1> <player2>");
      System.out.println("player types: 'human', 'strategy1', 'strategy2', etc.");
      return;
    }

    // Load the grid and deck
    ThreeTriosGrid grid = FileReader.gridFromFile("docs/ExampleBoards/board2.txt");
    List<ThreeTriosCard> deck = FileReader.readCardsFromFile("docs/ExampleCards/card_file2.txt");
    ThreeTriosGameModel model = new ThreeTriosGameModel(grid, deck);

    // Create players
    Player player1 = createPlayer(args[0], PlayerColor.RED);
    Player player2 = createPlayer(args[1], PlayerColor.BLUE);

    // Create views
    ThreeTriosView view1 = new ThreeTriosView(model);
    ThreeTriosView view2 = new ThreeTriosView(model);

    // Create controllers
    ThreeTriosController c1 = new ThreeTriosController(model, player1, view1, PlayerColor.RED, null);
    ThreeTriosController c2 = new ThreeTriosController(model, player2, view2, PlayerColor.BLUE, c1);
    c1.otherController = c2; // Link controllers

    // Set controllers for views
    view1.getGrid().setController(c1);
    view1.getLeftHandPanel().setController(c1);
    view2.getGrid().setController(c2);
    view2.getRightHandPanel().setController(c2);

  }

  /**
   * Creates a player based on the input type.
   *
   * @param type  the player type (e.g., "human", "strategy1")
   * @param color the player's color
   * @return the created player
   */
  private static Player createPlayer(String type, PlayerColor color) {
    switch (type.toLowerCase()) {
      case "human":
        return new HumanPlayer();
      case "strategy1":
        return new AIPlayer(new MaximizeFlipsStrategy(), color);
      case "strategy2":
        return new AIPlayer(new CornerPreferenceStrategy(), color);
      default:
        throw new IllegalArgumentException("Unknown player type: " + type);
    }
  }
}
