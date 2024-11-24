package cs3500.threetrios;

import cs3500.threetrios.controller.*;
import cs3500.threetrios.model.*;
import cs3500.threetrios.strategies.CornerPreferenceStrategy;
import cs3500.threetrios.strategies.MaximizeFlipsStrategy;
import cs3500.threetrios.view.*;

import java.io.FileNotFoundException;
import java.util.List;


public final class ThreeTrios {
  public static void main(String[] args) throws FileNotFoundException {
    ThreeTriosGrid grid = FileReader.gridFromFile("docs/ExampleBoards/board2.txt");
    List<ThreeTriosCard> deck = FileReader.readCardsFromFile("docs/ExampleCards/card_file2.txt");
    ThreeTriosGameModel model = new ThreeTriosGameModel(grid, deck);

    Player player1 = new HumanPlayer();
    Player player2 = new HumanPlayer();
    //Player player1 = new AIPlayer(new MaximizeFlipsStrategy(), PlayerColor.RED);
    //Player player2 = new AIPlayer(new CornerPreferenceStrategy(), PlayerColor.BLUE);

    ThreeTriosView view1 = new ThreeTriosView(model);
    ThreeTriosView view2 = new ThreeTriosView(model);

    ThreeTriosController c1 = new ThreeTriosController(model, player1, view1, PlayerColor.RED);
    ThreeTriosController c2 = new ThreeTriosController(model, player2, view2, PlayerColor.BLUE);

    view1.getGrid().setController(c1);
    view1.getLeftHandPanel().setController(c1);
    view2.getGrid().setController(c2);
    view2.getRightHandPanel().setController(c2);

    c1.activate();
    c2.activate();
  }
}
