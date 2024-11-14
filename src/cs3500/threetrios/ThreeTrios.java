package cs3500.threetrios;

import java.io.FileNotFoundException;
import java.util.List;
import cs3500.threetrios.controller.FileReader;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.view.ThreeTriosView;

/**
 * The entry point for the Three Trios game application.
 * Initializes the game model, loads data from files, and starts the game view.
 */
public final class ThreeTrios {

  /**
   * The main method to launch the Three Trios game.
   * It reads the board configuration and cards from specified files, initializes the game model,
   * and displays the game view.
   *
   * @param args command-line arguments (not used)
   * @throws FileNotFoundException if the specified board or card file is not found
   */
  public static void main(String[] args) throws FileNotFoundException {
    // Load the grid and deck using the FileReader class
    ThreeTriosGrid grid = FileReader.gridFromFile("docs/ExampleBoards/board2.txt");
    List<ThreeTriosCard> deck =
            FileReader.readCardsFromFile("docs/ExampleCards/card_file2.txt");

    // Initialize the game model with the loaded grid and deck
    ThreeTriosGameModel model = new ThreeTriosGameModel(grid, deck);

    // Initialize the game view and display it
    ThreeTriosView view = new ThreeTriosView(model);
    view.setVisible(true);
  }
}
