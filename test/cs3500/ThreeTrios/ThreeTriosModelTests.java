package cs3500.ThreeTrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.model.ThreeTriosPlayer;
import cs3500.threetrios.model.ThreeTriosGameModel;

/**
 * Tests for the ThreeTriosModel class.
 */
public class ThreeTriosModelTests {
  private ThreeTriosCard card;
  private ThreeTriosPlayer player;
  private ThreeTriosGrid grid;
  private ThreeTriosGameModel model;

  //create an init date
  @Before
  public void initData() {
    card = new ThreeTriosCard("Hello", 1, 1, 1, 1);
    player = new ThreeTriosPlayer(PlayerColor.RED, null);
    grid = new ThreeTriosGrid(3, 3);
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
