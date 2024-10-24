package cs3500.ThreeTrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosGameModel;

/**
 * Tests for the ThreeTriosModel class.
 */
public class ThreeTriosModelTests {
  private Card card;
  private Player player;
  private Grid grid;
  private ThreeTriosGameModel model;

  //create an init date
  @Before
  public void initData() {
    card = new Card("Hello", 1, 1, 1, 1);
    player = new Player("Red", null);
    grid = new Grid(3, 3);
    model = new ThreeTriosGameModel(3, 3, null);
  }

  @Test
  public void testPlaceCard() {
    model.placeCard(1, 1, card);
  }

  //Tests if card is void
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardVoid() {
    model.placeCard(1, 1, null);
  }
}
