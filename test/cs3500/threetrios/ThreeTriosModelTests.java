package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.List;

import cs3500.threetrios.controller.FileReader;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Test suite for the ThreeTriosGameModel class, ensuring that the model correctly interprets grid
 * configurations, card configurations, and manages gameplay logic accurately.
 */
public class ThreeTriosModelTests {

  private ThreeTriosGameModel gameModel;
  private ThreeTriosGrid grid;
  private List<ThreeTriosCard> deck;

  @Before
  public void setUp() throws FileNotFoundException {
    // Load the grid and deck using the FileReader class
    grid = FileReader.gridFromFile("docs/ExampleBoards/board1.txt");
    deck = FileReader.readCardsFromFile("docs/ExampleCards/card_file1.txt");
    gameModel = new ThreeTriosGameModel(grid, deck);
  }

  @Test
  public void testInitialState() {
    assertEquals("Initial turn should be RED player's", PlayerColor.RED,
            gameModel.currentPlayerColor());
    assertFalse("Game should not be over initially", gameModel.isGameOver());
  }

  @Test
  public void testValidCardPlacement() {
    ThreeTriosCard card = deck.get(0);
    gameModel.placeCard(0, 0, card);
    assertEquals("Card should be placed at (0,0)", card, grid.getCardFromCell(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCardPlacementOccupiedCell() {
    ThreeTriosCard card1 = deck.get(0);
    ThreeTriosCard card2 = deck.get(1);
    gameModel.placeCard(0, 0, card1);
    gameModel.placeCard(0, 0, card2);  // Should throw an exception
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBoundsCardPlacement() {
    ThreeTriosCard card = deck.get(0);
    gameModel.placeCard(0, -1, card);  // Should throw an exception
  }

  @Test
  public void testTurnSwitching() {
    ThreeTriosCard dragonCard = deck.get(0);
    assertEquals("RED player starts", PlayerColor.RED, gameModel.currentPlayerColor());
    gameModel.placeCard(0, 0, dragonCard);
    assertEquals("BLUE player's turn after RED", PlayerColor.BLUE,
            gameModel.currentPlayerColor());
  }

  @Test
  public void testGridConfigParsingBoard1() throws FileNotFoundException {
    grid = FileReader.gridFromFile("docs/ExampleBoards/board1.txt");
    assertNotNull("Grid should be initialized", grid);
    assertEquals("Grid should have 2 rows", 2, grid.getRowCount());
    assertEquals("Grid should have 2 columns", 2, grid.getColCount());
    assertEquals("Grid should have all card cells", 4, grid.getNumCardCells());
    assertEquals("Grid should have no holes", 0, grid.getNumHoleCells());
  }

  @Test
  public void testCardFile1Parsing() throws FileNotFoundException {
    List<ThreeTriosCard> deck =
            FileReader.readCardsFromFile("docs/ExampleCards/card_file1.txt");
    assertNotNull("Deck should not be null", deck);
    assertEquals("Deck should contain 7 cards", 7, deck.size());

    ThreeTriosCard dragonCard = deck.get(0);
    assertEquals("Dragon", dragonCard.getName());
    assertEquals(9, dragonCard.attack(Direction.NORTH));
    assertEquals(8, dragonCard.attack(Direction.SOUTH));
    assertEquals(7, dragonCard.attack(Direction.EAST));
    assertEquals(6, dragonCard.attack(Direction.WEST));
  }

  @Test
  public void testCardFile2Parsing() throws FileNotFoundException {
    List<ThreeTriosCard> deck =
            FileReader.readCardsFromFile("docs/ExampleCards/card_file2.txt");
    assertNotNull("Deck should not be null", deck);
    assertEquals("Deck should contain 26 cards", 26, deck.size());

    ThreeTriosCard eagleCard = deck.get(7);
    assertEquals("Eagle", eagleCard.getName());
    assertEquals(4, eagleCard.attack(Direction.NORTH));
    assertEquals(3, eagleCard.attack(Direction.SOUTH));
    assertEquals(2, eagleCard.attack(Direction.EAST));
    assertEquals(1, eagleCard.attack(Direction.WEST));
  }
}
