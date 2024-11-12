package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.Posn;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.view.ThreeTriosView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ThreeTriosGUIViewTests {

  private ThreeTriosGameModel gameModel;
  private ThreeTriosGrid grid;
  private List<ThreeTriosCard> deck;
  private List<ThreeTriosCard> gridCardsList;
  private List<Posn> placedCardPositions;

  @Before
  public void setUp() throws FileNotFoundException {
    // Load the basic grid and deck configuration for initialization
    grid = ThreeTriosGrid.fromFile("docs/ExampleBoards/board2.txt");
    deck = ThreeTriosGameModel.readCardsFromFile("docs/ExampleCards/"
            + "card_file2.txt");

    gridCardsList = new ArrayList<>();
    placedCardPositions = new ArrayList<>();

//    for (int card = 0; card < 1; card++) {
//      gridCardsList.add(deck.get(card));
//    }
    gridCardsList.add(deck.get(0));
    gridCardsList.add(deck.get(20));
    gridCardsList.add(deck.get(3));
    gridCardsList.add(deck.get(21));

    placedCardPositions.add(new Posn(0, 0));
    placedCardPositions.add(new Posn(0, 4));
    placedCardPositions.add(new Posn(3, 0));
    placedCardPositions.add(new Posn(1, 0));

    gameModel = new ThreeTriosGameModel(grid, deck, gridCardsList, placedCardPositions);
  }

  @Test
  public void testInitialState() {
    System.out.println(gameModel.currentPlayerColor().toString());
    //System.out.println(gameModel.getPlayerScore(PlayerColor.BLUE));

    ThreeTriosView view = new ThreeTriosView(gameModel);
    while (true) {
      view.setVisible(true);
    }

  }



}
