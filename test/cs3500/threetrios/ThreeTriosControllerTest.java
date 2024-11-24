//package cs3500.threetrios;
//
//import cs3500.threetrios.controller.HumanPlayer;
//import cs3500.threetrios.controller.Player;
//import cs3500.threetrios.controller.ThreeTriosController;
//import cs3500.threetrios.model.PlayerColor;
//import cs3500.threetrios.model.ThreeTriosCard;
//import cs3500.threetrios.model.ThreeTriosGameModel;
//import cs3500.threetrios.model.ThreeTriosGrid;
//import cs3500.threetrios.view.ThreeTriosView;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
///**
// * Test for the ThreeTriosController class.
// */
//public class ThreeTriosControllerTest {
//  private ThreeTriosGameModel model;
//  private ThreeTriosView view;
//  private ThreeTriosController controller;
//  private Player player;
//
//  @Before
//  public void setUp() {
//    // Create a small test grid and deck
//    ThreeTriosGrid grid = new ThreeTriosGrid(3, 3);
//    List<ThreeTriosCard> deck = generateMockDeck();
//    model = new ThreeTriosGameModel(grid, deck);
//
//    // Initialize the player and view
//    player = new HumanPlayer();
//    view = new ThreeTriosView(model);
//
//    // Create the controller
//    controller = new ThreeTriosController(model, player, view, PlayerColor.RED);
//    view.getGrid().setController(controller);
//    view.getLeftHandPanel().setController(controller);
//  }
//
//  private List<ThreeTriosCard> generateMockDeck() {
//    List<ThreeTriosCard> deck = new ArrayList<>();
//    for (int i = 0; i < 10; i++) {
//      deck.add(new ThreeTriosCard("Card " + i, i % 10 + 1, i % 10 + 2, i % 10 + 3, i % 10 + 4));
//    }
//    return deck;
//  }
//
//  @Test
//  public void testOnCardSelectedAndGridCellClickedValidMove() {
//    // Select a card from the RED player's hand
//    ThreeTriosCard card = model.getPlayerHand(PlayerColor.RED).get(0);
//    controller.onCardSelected(card);
//
//    // Place the card on the grid
//    controller.onGridCellClicked(1, 1);
//
//    // Verify the card is placed on the grid
//    assertEquals(card, model.getCellContents(1, 1).getCard());
//    assertTrue(model.getPlayerHand(PlayerColor.RED).stream().noneMatch(c -> c.equals(card)));
//    assertNull(getSelectedCard(controller)); // Verify card selection is cleared
//  }
//
//  @Test
//  public void testOnGridCellClickedInvalidMove() {
//    // Select a card
//    ThreeTriosCard card = model.getPlayerHand(PlayerColor.RED).get(0);
//    controller.onCardSelected(card);
//
//    // Place the card on an invalid cell (e.g., outside grid bounds)
//    try {
//      controller.onGridCellClicked(-1, -1);
//      fail("Expected IllegalArgumentException");
//    } catch (IllegalArgumentException e) {
//      assertEquals("Invalid position: (-1, -1)", e.getMessage());
//    }
//
//    // Verify the card is not removed from the player's hand
//    assertTrue(model.getPlayerHand(PlayerColor.RED).contains(card));
//  }
//
//  @Test
//  public void testOnGridCellClickedWithoutCardSelected() {
//    // Attempt to place a card without selecting one
//    try {
//      controller.onGridCellClicked(1, 1);
//      fail("Expected IllegalStateException");
//    } catch (IllegalStateException e) {
//      assertEquals("No card selected!", e.getMessage());
//    }
//
//    // Verify the grid is unchanged
//    assertTrue(model.getCellContents(1, 1).isEmpty());
//  }
//
//  @Test
//  public void testGameEndsCorrectly() {
//    // Fill the grid to simulate a game over
//    ThreeTriosCard card = model.getPlayerHand(PlayerColor.RED).get(0);
//    for (int row = 0; row < 3; row++) {
//      for (int col = 0; col < 3; col++) {
//        if (model.isGameOver()) {
//          break;
//        }
//        controller.onCardSelected(card);
//        controller.onGridCellClicked(row, col);
//        if (!model.getPlayerHand(PlayerColor.RED).isEmpty()) {
//          card = model.getPlayerHand(PlayerColor.RED).get(0);
//        } else {
//          card = model.getPlayerHand(PlayerColor.BLUE).get(0);
//        }
//      }
//    }
//
//    // Verify the game is over
//    assertTrue(model.isGameOver());
//    assertNotNull(model.determineWinner());
//  }
//
//  @Test
//  public void testCardOwnershipSwitchesAfterTurn() {
//    // Select and place a card as RED
//    ThreeTriosCard redCard = model.getPlayerHand(PlayerColor.RED).get(0);
//    controller.onCardSelected(redCard);
//    controller.onGridCellClicked(1, 1);
//
//    // Verify the turn switches to BLUE
//    assertEquals(PlayerColor.BLUE, model.getCurrentPlayerColor());
//
//    // Select and place a card as BLUE
//    ThreeTriosCard blueCard = model.getPlayerHand(PlayerColor.BLUE).get(0);
//    controller.onCardSelected(blueCard);
//    controller.onGridCellClicked(0, 0);
//
//    // Verify the turn switches back to RED
//    assertEquals(PlayerColor.RED, model.getCurrentPlayerColor());
//  }
//
//  private ThreeTriosCard getSelectedCard(ThreeTriosController controller) {
//    try {
//      var field = ThreeTriosController.class.getDeclaredField("selectedCard");
//      field.setAccessible(true);
//      return (ThreeTriosCard) field.get(controller);
//    } catch (Exception e) {
//      throw new RuntimeException("Reflection error", e);
//    }
//  }
//
//  @Test
//  public void testReplacingCardOnOccupiedCell() {
//    // Select and place a card in a cell
//    ThreeTriosCard card1 = model.getPlayerHand(PlayerColor.RED).get(0);
//    controller.onCardSelected(card1);
//    controller.onGridCellClicked(1, 1);
//
//    // Attempt to place another card in the same cell
//    ThreeTriosCard card2 = model.getPlayerHand(PlayerColor.RED).get(0);
//    controller.onCardSelected(card2);
//    try {
//      controller.onGridCellClicked(1, 1);
//      fail("Expected IllegalArgumentException");
//    } catch (IllegalArgumentException e) {
//      assertEquals("Invalid position: (1, 1)", e.getMessage());
//    }
//
//    // Verify the original card is still in the cell
//    assertEquals(card1, model.getCellContents(1, 1).getCard());
//    assertTrue(model.getPlayerHand(PlayerColor.RED).contains(card2));
//  }
//
//  @Test
//  public void testAttemptingToPlayOutOfTurn() {
//    // Set the current player to BLUE and attempt a move as RED
//    controller = new ThreeTriosController(model, player, view, PlayerColor.RED);
//
//    ThreeTriosCard card = model.getPlayerHand(PlayerColor.RED).get(0);
//    controller.onCardSelected(card);
//
//    try {
//      controller.onGridCellClicked(0, 0);
//      fail("Expected IllegalStateException");
//    } catch (IllegalStateException e) {
//      assertEquals("It's not your turn!", e.getMessage());
//    }
//
//    // Verify that no card was placed
//    assertTrue(model.getCellContents(0, 0).isEmpty());
//    assertTrue(model.getPlayerHand(PlayerColor.RED).contains(card));
//  }
//
//  @Test
//  public void testGameOverWithTie() {
//    // Fill the grid equally between RED and BLUE
//    ThreeTriosCard redCard = model.getPlayerHand(PlayerColor.RED).get(0);
//    ThreeTriosCard blueCard = model.getPlayerHand(PlayerColor.BLUE).get(0);
//
//    for (int i = 0; i < 9; i++) {
//      if (i % 2 == 0) {
//        controller.onCardSelected(redCard);
//        controller.onGridCellClicked(i / 3, i % 3);
//        if (!model.getPlayerHand(PlayerColor.RED).isEmpty()) {
//          redCard = model.getPlayerHand(PlayerColor.RED).get(0);
//        }
//      } else {
//        controller.onCardSelected(blueCard);
//        controller.onGridCellClicked(i / 3, i % 3);
//        if (!model.getPlayerHand(PlayerColor.BLUE).isEmpty()) {
//          blueCard = model.getPlayerHand(PlayerColor.BLUE).get(0);
//        }
//      }
//    }
//
//    // Verify the game ends with a tie
//    assertTrue(model.isGameOver());
//    assertEquals("It's a tie!", model.determineWinner());
//  }
//
//  @Test
//  public void testSkipTurnWhenNoValidMoves() {
//    // Simulate RED player having no valid moves
//    model.getPlayerHand(PlayerColor.RED).clear();
//
//    assertEquals(PlayerColor.RED, model.getCurrentPlayerColor());
//
//    // Ensure the turn automatically skips to BLUE
//    assertEquals(PlayerColor.BLUE, model.oppositePlayerColor());
//  }
//
//  @Test
//  public void testBattlePhaseTriggered() {
//    // Place cards adjacent to each other and verify battles occur
//    ThreeTriosCard redCard = new ThreeTriosCard("Red Card", 5, 4, 3, 2);
//    ThreeTriosCard blueCard = new ThreeTriosCard("Blue Card", 3, 6, 2, 5);
//
//    model.getPlayerHand(PlayerColor.RED).add(redCard);
//    model.getPlayerHand(PlayerColor.BLUE).add(blueCard);
//
//    // Place RED card
//    controller.onCardSelected(redCard);
//    controller.onGridCellClicked(1, 1);
//
//    // Place BLUE card next to it
//    controller.onCardSelected(blueCard);
//    controller.onGridCellClicked(1, 2);
//
//    // Verify RED card flips ownership due to battle
//    assertEquals(PlayerColor.BLUE, model.getCardOwner(1, 1));
//  }
//
//  @Test
//  public void testIllegalCardSelection() {
//    // Attempt to select a card that doesn't exist
//    try {
//      controller.onCardSelected(null);
//      fail("Expected IllegalArgumentException");
//    } catch (IllegalArgumentException e) {
//      assertEquals("Invalid card selection!", e.getMessage());
//    }
//  }
//
//
//
//
//
//
//}
