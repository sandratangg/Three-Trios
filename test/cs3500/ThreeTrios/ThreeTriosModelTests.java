//package cs3500.ThreeTrios;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cs3500.threetrios.model.PlayerColor;
//import cs3500.threetrios.model.ThreeTriosCard;
//import cs3500.threetrios.model.ThreeTriosGrid;
//import cs3500.threetrios.model.ThreeTriosPlayer;
//import cs3500.threetrios.model.ThreeTriosGameModel;
//
// * Tests for the ThreeTriosModel class.
// */
//public class ThreeTriosModelTests {
//
//  private ThreeTriosGameModel gameModel;
//  private ThreeTriosPlayer redPlayer;
//  private ThreeTriosPlayer bluePlayer;
//  private ThreeTriosGrid grid;
//
//  @Before
//  public void setUp() {
//    // Prepare a deck of cards to divide between players
//    List<ThreeTriosCard> deck = createDeck();
//
//    // Initialize game model with a 3x3 grid and the created deck
//    gameModel = new ThreeTriosGameModel(3, 3, deck);
//
//    // Initialize players
//    this.redPlayer = gameModel.getRedPlayer();
//    this.bluePlayer = gameModel.getBluePlayer();
//    this.grid = gameModel.getGrid();
//  }
//
//
//
//  // Helper method to create a deck with valid attack values
//  private List<ThreeTriosCard> createDeck() {
//    List<ThreeTriosCard> deck = new ArrayList<>();
//    deck.add(new ThreeTriosCard("Card1", 5, 3, 7, 6));
//    deck.add(new ThreeTriosCard("Card2", 4, 6, 8, 5));
//    deck.add(new ThreeTriosCard("Card3", 7, 2, 5, 9));
//    deck.add(new ThreeTriosCard("Card4", 5, 4, 6, 8));
//    deck.add(new ThreeTriosCard("Card5", 6, 7, 3, 4));
//    deck.add(new ThreeTriosCard("Card6", 9, 1, 4, 7));
//    return deck;
//  }
//
//  @Test
//  public void testGridInitialization() {
//    // Ensure grid initializes correctly
//    assertNotNull("Grid should be initialized", grid);
//    assertEquals("Grid should start empty", 0, grid.countOccupiedCells());
//  }
//
//  @Test
//  public void testInitialPlayerSetup() {
//    // Verify initial setup of players
//    assertEquals("Red player should start with 3 cards", 3, redPlayer.getHand().size());
//    assertEquals("Blue player should start with 3 cards", 3, bluePlayer.getHand().size());
//    assertEquals("Initial player should be redPlayer", redPlayer, gameModel.getCurrentPlayer());
//  }
//
//  @Test
//  public void testValidCardPlacement() {
//    // Place a card on the grid and confirm it updates correctly
//    ThreeTriosCard card = redPlayer.getHand().get(0);
//    boolean placed = gameModel.placeCard(1, 1, card);
//    assertTrue("Card should be placed at (1, 1)", placed);
//    assertEquals("Grid should contain the placed card at (1, 1)", card, grid.getCardAt(1, 1));
//  }
//
//  @Test
//  public void testInvalidCardPlacement() {
//    // Attempt to place a card in an invalid position or on an occupied cell
//    ThreeTriosCard card = redPlayer.getHand().get(0);
//    assertFalse("Placing a card out of bounds should fail", gameModel.placeCard(-1, -1, card));
//
//    gameModel.placeCard(1, 1, card); // Place a card at (1, 1)
//    assertFalse("Placing a card on an occupied cell should fail", gameModel.placeCard(1, 1, bluePlayer.getHand().get(0)));
//  }
//
//  @Test
//  public void testTurnSwitching() {
//    // Ensure turns switch correctly between red and blue players
//    ThreeTriosCard card = redPlayer.getHand().get(0);
//    gameModel.placeCard(1, 1, card);
//    assertEquals("After Red's turn, it should be Blue's turn", bluePlayer, gameModel.getCurrentPlayer());
//
//    card = bluePlayer.getHand().get(0);
//    gameModel.placeCard(1, 2, card);
//    assertEquals("After Blue's turn, it should be Red's turn", redPlayer, gameModel.getCurrentPlayer());
//  }
//
//  @Test
//  public void testBattlePhaseOwnershipFlip() {
//    // Set up a battle to verify ownership flipping
//    ThreeTriosCard redCard = new ThreeTriosCard("RedCard", 5, 6, 7, 6);
//    ThreeTriosCard blueCard = new ThreeTriosCard("BlueCard", 4, 5, 8, 7);
//
//    // Place red card at (1,1) and blue card at (1,2) to trigger a battle
//    gameModel.placeCard(1, 1, redCard);
//    gameModel.placeCard(1, 2, blueCard);
//
//    assertEquals("After battle, Red card should be owned by Blue due to higher attack value", bluePlayer, grid.getCardAt(1, 1).getOwner());
//  }
//
//  @Test
//  public void testComboBattlePhase() {
//    // Set up cards to create a combo situation where multiple battles occur
//
//    // Place initial cards for bluePlayer around (1,1) position
//    ThreeTriosCard blueCardNorth = new ThreeTriosCard("BlueNorth", 3, 5, 6, 8);
//    ThreeTriosCard blueCardEast = new ThreeTriosCard("BlueEast", 6, 4, 5, 7);
//    ThreeTriosCard blueCardWest = new ThreeTriosCard("BlueWest", 8, 5, 4, 6);
//    ThreeTriosCard blueCardSouth = new ThreeTriosCard("BlueSouth", 7, 8, 6, 5);
//
//    // Place bluePlayer's cards in all four directions around (1, 1)
//    gameModel.placeCard(0, 1, blueCardNorth);
//    gameModel.placeCard(1, 2, blueCardEast);
//    gameModel.placeCard(1, 0, blueCardWest);
//    gameModel.placeCard(2, 1, blueCardSouth);
//
//    // Place a powerful red card at (1, 1) to trigger battles in all directions
//    ThreeTriosCard redComboCard = new ThreeTriosCard("RedCombo", 9, 9, 9, 9);
//    gameModel.placeCard(1, 1, redComboCard);
//
//    // Check ownership of surrounding cards to confirm battle outcomes
//    assertEquals("Blue card at (0, 1) should now be owned by Red", redPlayer, grid.getCardAt(0, 1).getOwner());
//    assertEquals("Blue card at (1, 2) should now be owned by Red", redPlayer, grid.getCardAt(1, 2).getOwner());
//    assertEquals("Blue card at (1, 0) should now be owned by Red", redPlayer, grid.getCardAt(1, 0).getOwner());
//    assertEquals("Blue card at (2, 1) should now be owned by Red", redPlayer, grid.getCardAt(2, 1).getOwner());
//  }
//
//  @Test
//  public void testGameEndCondition() {
//    // Fill the grid to end the game
//    for (int i = 0; i < 3; i++) {
//      for (int j = 0; j < 3; j++) {
//        gameModel.placeCard(i, j, redPlayer.getHand().get(0));
//      }
//    }
//    assertTrue("The game should end when the grid is full", gameModel.isGameOver());
//  }
//
//  @Test
//  public void testDetermineWinner() {
//    // Set up a situation where redPlayer should have more cards
//    redPlayer.addOwnedCard(new ThreeTriosCard("ExtraCard", 5, 5, 5, 5));
//    assertEquals("Red player should win with more cards", "Red player wins with 4 cards!", gameModel.determineWinner());
//  }
//
//  @Test
//  public void testCardFile1Parsing() throws FileNotFoundException {
//    List<ThreeTriosCard> deck = ThreeTriosGameModel.readCardsFromFile("/mnt/data/card_file1.txt");
//
//    assertNotNull("Deck should not be null after reading from file", deck);
//    assertEquals("Deck should contain 7 cards", 7, deck.size());
//
//    // Verify individual card details
//    ThreeTriosCard dragonCard = deck.get(0);
//    assertEquals("Dragon", dragonCard.getName());
//    assertEquals(9, dragonCard.getAttackNorth());
//    assertEquals(8, dragonCard.getAttackSouth());
//    assertEquals(7, dragonCard.getAttackEast());
//    assertEquals(6, dragonCard.getAttackWest());
//
//    ThreeTriosCard phoenixCard = deck.get(1);
//    assertEquals("Phoenix", phoenixCard.getName());
//    assertEquals(6, phoenixCard.getAttackNorth());
//    assertEquals(5, phoenixCard.getAttackSouth());
//    assertEquals(4, phoenixCard.getAttackEast());
//    assertEquals(3, phoenixCard.getAttackWest());
//  }
//
//  @Test
//  public void testCardFile2Parsing() throws FileNotFoundException {
//    List<ThreeTriosCard> deck = ThreeTriosGameModel.readCardsFromFile("/mnt/data/card_file2.txt");
//
//    assertNotNull("Deck should not be null after reading from file", deck);
//    assertEquals("Deck should contain 27 cards", 27, deck.size());
//
//    // Verify a few individual card details
//    ThreeTriosCard dragonCard = deck.get(0);
//    assertEquals("Dragon", dragonCard.getName());
//    assertEquals(9, dragonCard.getAttackNorth());
//    assertEquals(8, dragonCard.getAttackSouth());
//    assertEquals(7, dragonCard.getAttackEast());
//    assertEquals(6, dragonCard.getAttackWest());
//
//    ThreeTriosCard eagleCard = deck.get(7);
//    assertEquals("Eagle", eagleCard.getName());
//    assertEquals(4, eagleCard.getAttackNorth());
//    assertEquals(3, eagleCard.getAttackSouth());
//    assertEquals(2, eagleCard.getAttackEast());
//    assertEquals(1, eagleCard.getAttackWest());
//
//    ThreeTriosCard oxCard = deck.get(26);
//    assertEquals("Ox", oxCard.getName());
//    assertEquals(5, oxCard.getAttackNorth());
//    assertEquals(6, oxCard.getAttackSouth());
//    assertEquals(4, oxCard.getAttackEast());
//    assertEquals(7, oxCard.getAttackWest());
//  }
//
//
//}
