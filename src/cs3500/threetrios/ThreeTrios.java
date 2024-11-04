package cs3500.threetrios;

import java.io.FileNotFoundException;

import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.view.ThreeTriosView;

public final class ThreeTrios {
  public static void main(String[] args) throws FileNotFoundException {
    ThreeTriosGameModel model = new ThreeTriosGameModel(2, 2, ThreeTriosGameModel.readCardsFromFile("docs/ExampleCards/card_file2.txt"));

    // Test grid dimensions to ensure it's initialized
    System.out.println("Grid height: " + model.getGridHeight());
    System.out.println("Grid width: " + model.getGridWidth());



    ThreeTriosView view = new ThreeTriosView(model);
    view.setVisible(true);
  }
}