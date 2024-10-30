package cs3500.ThreeTrios;

import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosPlayer;
import cs3500.threetrios.model.PlayerColor;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for the ThreeTriosPlayer class.
 */
public class ThreeTriosPlayerTests {

  private ThreeTriosPlayer playerRed;
  private ThreeTriosPlayer playerBlue;
  private ThreeTriosCard sampleCardDragon;
  private ThreeTriosCard sampleCardPhoenix;

  /**
   * Initializes common objects before each test.
   */
  @Before
  public void init() {
    // Initialize sample cards for testing
    sampleCardDragon = new ThreeTriosCard("Dragon", 5, 7, 6, 3);
    sampleCardPhoenix = new ThreeTriosCard("Phoenix", 3, 8, 5, 7);

    // Initialize players with initial hands
    List<ThreeTriosCard> redInitialHand = new ArrayList<>();
    redInitialHand.add(sampleCardDragon);

    List<ThreeTriosCard> blueInitialHand = new ArrayList<>();
    blueInitialHand.add(sampleCardPhoenix);

    playerRed = new ThreeTriosPlayer(PlayerColor.RED, redInitialHand);
    playerBlue = new ThreeTriosPlayer(PlayerColor.BLUE, blueInitialHand);
  }

  // Test player color is correctly initialized
  @Test
  public void testPlayerColor() {
    assertTrue(playerRed.isRed());
    assertFalse(playerRed.isBlue());

    assertTrue(playerBlue.isBlue());
    assertFalse(playerBlue.isRed());
  }

  // Test addToHand method: Ensure a card can be added to a player's hand
  @Test
  public void testAddToHand() {
    ThreeTriosCard newCard = new ThreeTriosCard("Tiger", 6, 4, 9, 2);
    playerRed.addToHand(newCard);
    assertTrue(playerRed.handToString().contains("Tiger"));
  }

  // Test addToHand throws exception when adding duplicate card
  @Test(expected = IllegalStateException.class)
  public void testAddToHandDuplicate() {
    playerRed.addToHand(sampleCardDragon);  // sampleCardDragon is already in hand
  }

  // Test playCard method: Remove card from hand
  @Test
  public void testPlayCard() {
    assertTrue(playerRed.playCard(sampleCardDragon));
    assertFalse(playerRed.handToString().contains("Dragon"));
  }

  // Test playCard method when card is not in hand
  @Test
  public void testPlayCardNotInHand() {
    assertFalse(playerRed.playCard(sampleCardPhoenix));  // sampleCardPhoenix is not in red's hand
  }

  // Test addToOwned: Adds a card to player's owned cards
  @Test
  public void testAddToOwned() {
    playerRed.addToOwned(sampleCardDragon);
    assertTrue(playerRed.owns(sampleCardDragon));
  }

  // Test addToOwned throws exception if card is already owned
  @Test
  public void testAddToOwnedDuplicate() {
    playerRed.addToOwned(sampleCardDragon);
    try {
      playerRed.addToOwned(sampleCardDragon);  // tries to add the same card again
      fail("Expected IllegalStateException to be thrown for adding duplicate card to owned cards");
    } catch (IllegalStateException e) {
      // Test passes if IllegalStateException is thrown
    }
  }

  // Test removeFromOwned: Removes a card from owned cards
  @Test
  public void testRemoveFromOwned() {
    playerRed.addToOwned(sampleCardDragon);
    playerRed.removeFromOwned(sampleCardDragon);
    assertFalse(playerRed.owns(sampleCardDragon));
  }

  // Test removeFromOwned throws exception if card is not owned
  @Test
  public void testRemoveFromOwnedNotOwned() {
    try {
      playerRed.removeFromOwned(sampleCardPhoenix); // tries to remove a card not owned by playerRed
      fail("Expected IllegalStateException to be thrown for removing a card that is not owned");
    } catch (IllegalStateException e) {
      // Test passes and throws IllegalStateException
    }
  }

  // Test toString method
  @Test
  public void testToStringPlayerColor() {
    assertEquals("Player: RED", playerRed.toString());
    assertEquals("Player: BLUE", playerBlue.toString());
  }

  // Test handToString method
  @Test
  public void testHandToString() {
    assertTrue(playerRed.handToString().contains("Dragon"));
    assertTrue(playerBlue.handToString().contains("Phoenix"));
  }

  // Test getOwnedCardsSize method
  @Test
  public void testGetOwnedCardsSize() {
    assertEquals(0, playerRed.getOwnedCardsSize()); // Initial size should be 0
    playerRed.addToOwned(sampleCardDragon);
    assertEquals(1, playerRed.getOwnedCardsSize());
  }

  // Test handToString with cards in the hand
  @Test
  public void testHandToStringWithCards() {
    // Check the handToString when "Dragon" card is in hard
    assertEquals("Dragon 5 7 6 3", playerRed.handToString());

    // Check the handToString for playerBlue when "Phoenix" card is in hand
    assertEquals("Phoenix 3 8 5 7", playerBlue.handToString());
  }

  // Test handToString with an empty hand
  @Test
  public void testHandToStringEmptyHand() {
    // Create a player with an empty hand
    ThreeTriosPlayer playerRedEmpty = new ThreeTriosPlayer(PlayerColor.RED, new ArrayList<>());
    assertTrue(playerRedEmpty.handToString().isEmpty());

    // Create another player with an empty hand
    ThreeTriosPlayer playerBlueEmpty = new ThreeTriosPlayer(PlayerColor.BLUE, new ArrayList<>());
    assertTrue(playerBlueEmpty.handToString().isEmpty());

  }

  // Test handToString with multiple cards in hand
  @Test
  public void testHandToStringMultipleCards() {
    // Add another card to playerRed's hand
    ThreeTriosCard newCard = new ThreeTriosCard("Tiger", 6, 4, 9, 2);
    playerRed.addToHand(newCard);

    // Expected hand string output with both "Dragon" and "Tiger" in hand
    String expectedHand = "Dragon 5 7 6 3\nTiger 6 4 9 2";
    assertEquals(expectedHand, playerRed.handToString());
  }
}
