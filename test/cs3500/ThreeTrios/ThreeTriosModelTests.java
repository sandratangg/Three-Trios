//package cs3500.ThreeTrios;
//
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
//import java.io.FileNotFoundException;
//import java.util.List;
//
//import cs3500.threetrios.model.PlayerColor;
//import cs3500.threetrios.model.ThreeTriosCard;
//import cs3500.threetrios.model.ThreeTriosGameModel;
//import cs3500.threetrios.model.ThreeTriosGrid;
//import cs3500.threetrios.model.ThreeTriosPlayer;
//
///**
// * Test suite for the ThreeTriosGameModel class, ensuring that the model
// * correctly interprets grid configurations, card configurations, and manages
// * gameplay logic accurately.
// */
//public class ThreeTriosModelTests {
//
//  private ThreeTriosGameModel gameModel;
//  private ThreeTriosGrid grid;
//
//  @Before
//  public void setUp() {
//    // No specific setup is required as each test initializes its own game instance
//  }
//
//  // Test grid configuration parsing, ensuring correct grid structure and cell types.
//  @Test
//  public void testGridConfigParsingBoard1() throws FileNotFoundException {
//    grid = ThreeTriosGrid.fromFile("/mnt/data/board1.txt");
//    assertNotNull("Grid should be initialized", grid);
//    assertEquals("Grid should have 5 rows", 5, grid.getRows());
//    assertEquals("Grid should have 5 columns", 5, grid.getCols());
//    assertEquals("Grid should have all card cells", 25, grid.countCardCells());
//    assertEquals("Grid should have no holes", 0, grid.countHoles());
//  }
//
//  @Test
//  public void testGridConfigParsingBoard2() throws FileNotFoundException {
//    grid = ThreeTriosGrid.fromFile("/mnt/data/board2.txt");
//    assertNotNull("Grid should be initialized", grid);
//    assertEquals("Grid should have 5 rows", 5, grid.getRows());
//    assertEquals("Grid should have 5 columns", 5, grid.getCols());
//    assertEquals("Grid should have 16 card cells", 16, grid.countCardCells());
//    assertEquals("Grid should have 9 holes", 9, grid.countHoles());
//  }
//
//  @Test
//  public void testGridConfigParsingBoard3() throws FileNotFoundException {
//    grid = ThreeTriosGrid.fromFile("/mnt/data/board3.txt");
//    assertNotNull("Grid should be initialized", grid);
//    assertEquals("Grid should have 5 rows", 5, grid.getRows());
//    assertEquals("Grid should have 5 columns", 5, grid.getCols());
//    assertEquals("Grid should have 8 card cells", 8, grid.countCardCells());
//    assertEquals("Grid should have 17 holes", 17, grid.countHoles());
//  }
//
//  // Test card configuration parsing, ensuring accurate card values and names are loaded.
//  @Test
//  public void testCardFile1Parsing() throws FileNotFoundException {
//    List<ThreeTriosCard> deck = ThreeTriosGameModel.readCardsFromFile("/mnt/data/card_file1.txt");
//    assertNotNull("Deck should not be null", deck);
//    assertEquals("Deck should contain 7 cards", 7, deck.size());
//
//    ThreeTriosCard dragonCard = deck.get(0);
//    assertEquals("Dragon", dragonCard.getName());
//    assertEquals(9, dragonCard.getAttackNorth());
//    assertEquals(8, dragonCard.getAttackSouth());
//    assertEquals(7, dragonCard.getAttackEast());
//    assertEquals(6, dragonCard.getAttackWest());
//  }
//
//  @Test
//  public void testCardFile2Parsing() throws FileNotFoundException {
//    List<ThreeTriosCard> deck = ThreeTriosGameModel.readCardsFromFile("/mnt/data/card_file2.txt");
//    assertNotNull("Deck should not be null", deck);
//    assertEquals("Deck should contain 27 cards", 27, deck.size());
//
//    ThreeTriosCard eagleCard = deck.get(7);
//    assertEquals("Eagle", eagleCard.getName());
//    assertEquals(4, eagleCard.getAttackNorth());
//    assertEquals(3, eagleCard.getAttackSouth());
//    assertEquals(2, eagleCard.getAttackEast());
//    assertEquals(1, eagleCard.getAttackWest());
//  }
//
//  // Additional test for gameplay logic such as turn-switching and winner determination
//  @Test
//  public void testGamePlayTurnSwitching() throws FileNotFoundException {
//    List<ThreeTriosCard> deck = ThreeTriosGameModel.readCardsFromFile("/mnt/data/card_file1.txt");
//    gameModel = new ThreeTriosGameModel(3, 3, deck);
//
//    ThreeTriosPlayer currentPlayer = gameModel.getCurrentPlayer();
//    assertEquals("Red player should start", PlayerColor.RED, currentPlayer.getColor());
//
//    gameModel.switchTurn();
//    currentPlayer = gameModel.getCurrentPlayer();
//    assertEquals("Turn should switch to Blue", PlayerColor.BLUE, currentPlayer.getColor());
//  }
//
//  @Test
//  public void testDetermineWinner() throws FileNotFoundException {
//    List<ThreeTriosCard> deck = ThreeTriosGameModel.readCardsFromFile("/mnt/data/card_file1.txt");
//    gameModel = new ThreeTriosGameModel(3, 3, deck);
//
//    gameModel.getRedPlayer().addToOwned(deck.get(0)); // Assume adding card to simulate winning condition
//    assertEquals("Red player wins with 1 cards!", gameModel.determineWinner());
//  }
//}
