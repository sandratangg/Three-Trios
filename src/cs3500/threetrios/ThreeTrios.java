package cs3500.threetrios;

import java.io.FileNotFoundException;

import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.view.ThreeTriosView;

public final class ThreeTrios {
  public static void main(String[] args) throws FileNotFoundException {
    ThreeTriosGameModel model = new ThreeTriosGameModel(2, 2, ThreeTriosGameModel.readCardsFromFile("docs/ExampleCards/card_file2.txt"));

    // Test
    System.out.println("Current grid height: " + model.getGridHeight());

    ThreeTriosView view = new ThreeTriosView(model);
    view.setVisible(true);
  }
}