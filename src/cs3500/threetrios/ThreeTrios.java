package cs3500.threetrios;

import cs3500.threetrios.controller.*;
import cs3500.threetrios.model.*;
import cs3500.threetrios.strategies.*;
import cs3500.threetrios.view.*;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The entry point for the Three Trios game application.
 */
public final class ThreeTrios {
  public static void main(String[] args) throws FileNotFoundException {
    // Load the grid and deck using the FileReader class
    ThreeTriosGrid grid = FileReader.gridFromFile("docs/ExampleBoards/board2.txt");
    List<ThreeTriosCard> deck = FileReader.readCardsFromFile("docs/ExampleCards/card_file2.txt");
    ThreeTriosGameModel model = new ThreeTriosGameModel(grid, deck);

    // Hardcode players
    // Both players are set as HumanPlayers for now
    Player player1 = new HumanPlayer();
    Player player2 = new HumanPlayer();

    // Uncomment the following lines to use AI players instead
    // Player player1 = new AIPlayer(new CornerPreferenceStrategy(), PlayerColor.RED);
    // Player player2 = new AIPlayer(new MaximizeFlipsStrategy(), PlayerColor.BLUE);

    // Get the actual game players
    // TO-DO: Need to modify logic to work w/ playerColors instead
    ThreeTriosPlayer redPlayer = model.getRedPlayer();
    ThreeTriosPlayer bluePlayer = model.getBluePlayer();

    // Create views for both players
    ThreeTriosView view1 = new ThreeTriosView(model);
    ThreeTriosView view2 = new ThreeTriosView(model);

    // Create controllers for each player
    new ThreeTriosController(model, view1, player1, redPlayer);
    new ThreeTriosController(model, view2, player2, bluePlayer);

    // Display both views
    view1.setVisible(true);
    view2.setVisible(true);

    // Start the game

    //TO-DO: I'm pretty sure the way I implemented the game you don't need to start the game
    // but can't hurt to double-check.
    model.startGame();
  }
}
