package cs3500.threetrios;

import java.io.FileNotFoundException;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.view.ThreeTriosView;

public final class ThreeTrios {
  public static void main(String[] args) throws FileNotFoundException {
    ThreeTriosGameModel model = new ThreeTriosGameModel(ThreeTriosGrid.fromFile("docs/ExampleBoards/board2.txt"),
            ThreeTriosGameModel.readCardsFromFile("docs/ExampleCards/card_file2.txt"));

    // Test
    System.out.println("Current grid height: " + model.getGridHeight());
    System.out.println("Red player hand size: " + model.getPlayerHand(PlayerColor.RED).size());
    ThreeTriosView view = new ThreeTriosView(model);
    view.setVisible(true);
  }
}