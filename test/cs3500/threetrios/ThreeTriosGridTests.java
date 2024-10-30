package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.model.ThreeTriosPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ThreeTriosGridTests {

  private ThreeTriosGrid smallGrid;
  private ThreeTriosGrid mediumGrid;
  private ThreeTriosGrid largeGrid;
  private ThreeTriosCard sampleCardDragon;
  private ThreeTriosCard sampleCardPhoenix;
  private ThreeTriosPlayer playerRed;
  private ThreeTriosPlayer playerBlue;
  private ThreeTriosCard highAttackCard;
  private ThreeTriosCard lowAttackCard;


  /**
   * Initializes common objects before each test.
   */
  @Before
  public void init() {
    // Sample grids of varying sizes
    smallGrid = new ThreeTriosGrid(2, 2);   // Small 2x2 grid
    mediumGrid = new ThreeTriosGrid(5, 5);  // Medium 5x5 grid
    largeGrid = new ThreeTriosGrid(10, 10); // Large 10x10 grid

    // Sample card for placement and battle testing
    sampleCardDragon = new ThreeTriosCard("Dragon",
            5, 7, 6, 3);
    sampleCardPhoenix = new ThreeTriosCard("Phoenix",
            3, 8, 5, 7);

    // Sample initial hands for players
    List<ThreeTriosCard> redInitialHand = new ArrayList<>();
    redInitialHand.add(sampleCardDragon); // Adding sampleCard to Red's hand
    redInitialHand.add(sampleCardPhoenix);

    List<ThreeTriosCard> blueInitialHand = new ArrayList<>();
    blueInitialHand.add(new ThreeTriosCard("Tiger",
            6, 4, 9, 2));
    blueInitialHand.add(new ThreeTriosCard("Eagle",
            7, 5, 4, 8));

    // Initialize players with colors and initial hands
    playerRed = new ThreeTriosPlayer(PlayerColor.RED, redInitialHand);
    playerBlue = new ThreeTriosPlayer(PlayerColor.BLUE, blueInitialHand);

    // Cards for battle testing
    highAttackCard = new ThreeTriosCard("Dragon",
            8, 7, 6, 5);  // Higher attack values
    lowAttackCard = new ThreeTriosCard("Phoenix",
            3, 3, 3, 3);   // Lower attack values
  }


  // Test constructor with valid dimensions (positive rows and columns)
  // Makes sure grid is created with empty cells.
  @Test
  public void testConstructorValidDimensions() {
    // Small grid
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 2; col++) {
        //check if every cell in small grid isEmpty()
        assertTrue(!smallGrid.isGridFull());

      }
    }

    // Medium grid
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        //check if every cell in medium grid isEmpty()
        assertTrue(!mediumGrid.isGridFull());
      }
    }

    // Large grid
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        //check if every cell in large grid isEmpty()
        assertTrue(!largeGrid.isGridFull());
      }
    }
  }

  // Test constructor with invalid dimensions (rows or columns <= 0)
  // Expect IllegalArgumentException.
  @Test
  public void testConstructorInvalidDimensions() {
    try {
      ThreeTriosGrid invalidGrid = new ThreeTriosGrid(0, 5);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Grid dimensions must be positive."));
    }

    try {
      ThreeTriosGrid invalidGrid = new ThreeTriosGrid(5, 0);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Grid dimensions must be positive."));
    }

    try {
      ThreeTriosGrid invalidGrid = new ThreeTriosGrid(0, 0);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Grid dimensions must be positive."));
    }
  }

  // Test getCardFromCell()
  @Test
  public void testGetCardFromCell() {
    // Place sampleCard in the top-left corner of the small grid
    smallGrid.placeCard(0, 0, sampleCardDragon);
    assertTrue(smallGrid.getCardFromCell(0, 0).equals(sampleCardDragon));

    // Place sampleCard in the center of the medium grid
    mediumGrid.placeCard(2, 2, sampleCardDragon);
    assertTrue(mediumGrid.getCardFromCell(2, 2).equals(sampleCardDragon));

    // Place sampleCard in the bottom-right corner of the large grid
    largeGrid.placeCard(9, 9, sampleCardDragon);
    assertTrue(largeGrid.getCardFromCell(9, 9).equals(sampleCardDragon));
  }

  // Test placeCard in a valid position on an empty cell
  // Makes sure the card is placed correctly and placeCard returns true.
  @Test
  public void testPlaceCardValidPos() {
    // Place sampleCard in the top-left corner of the small grid
    smallGrid.placeCard(0, 0, sampleCardDragon);
    assertEquals(smallGrid.getCardFromCell(0, 0), sampleCardDragon);

    // Place sampleCard in the center of the medium grid
    mediumGrid.placeCard(2, 2, sampleCardDragon);
    assertEquals(mediumGrid.getCardFromCell(2, 2), sampleCardDragon);

    // Place sampleCard in the bottom-right corner of the large grid
    largeGrid.placeCard(9, 9, sampleCardDragon);
    assertEquals(largeGrid.getCardFromCell(9, 9), sampleCardDragon);
  }


  // Test placeCard in an occupied cell (already has a card)
  // Should throw IllegalArgumentException
  @Test
  public void testPlaceCardOccupiedCell() {
    // Place sampleCard in the top-left corner of the small grid
    smallGrid.placeCard(0, 0, sampleCardDragon);
    try {
      smallGrid.placeCard(0, 0, sampleCardDragon);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Card is already placed or cell is a hole."));
    }

    // Place sampleCard in the center of the medium grid
    mediumGrid.placeCard(2, 2, sampleCardDragon);
    try {
      mediumGrid.placeCard(2, 2, sampleCardDragon);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Card is already placed or cell is a hole."));
    }

    // Place sampleCard in the bottom-right corner of the large grid
    largeGrid.placeCard(9, 9, sampleCardDragon);
    try {
      largeGrid.placeCard(9, 9, sampleCardDragon);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Card is already placed or cell is a hole."));
    }
  }

  // Test placeCard in a hole cell (invalid placement)
  // Should throw IllegalStateException
  // Uses the fromFile() method to create a grid from a file.
  // Uses board2.txt as the file (config file that contains a hole cell).
  @Test
  public void testPlaceCardHoleCell() throws FileNotFoundException {
    ThreeTriosGrid gridFile = ThreeTriosGrid.fromFile(
            "docs/ExampleBoards/board2.txt");
    sampleCardPhoenix = new ThreeTriosCard("Dragon", 5,
            7, 6, 3);

    try {
      gridFile.placeCard(1, 2, sampleCardPhoenix);
      fail("Expected IllegalStateException to be thrown for placing card in a hole cell");
    } catch (IllegalStateException e) {
      // Test passes if IllegalStateException is thrown
    }
  }


  //Tests that placeCard throws an IllegalArgumentException for out-of-bounds indices on all grids
  @Test
  public void testPlaceCardOutOfBoundsAllGrids() {
    // Testing out-of-bounds indices on smallGrid (2x2)
    testOutOfBoundsPlaceCard(smallGrid, -1, 0);  // Negative row
    testOutOfBoundsPlaceCard(smallGrid, 2, 2);   // Out of bounds for grid size
    testOutOfBoundsPlaceCard(smallGrid, 0, 3);   // Out of bounds column
    testOutOfBoundsPlaceCard(smallGrid, 3, 3);   // Both row and column out of bounds

    // Testing out-of-bounds indices on mediumGrid (5x5)
    testOutOfBoundsPlaceCard(mediumGrid, -1, 0);  // Negative row
    testOutOfBoundsPlaceCard(mediumGrid, 5, 5);   // Out of bounds for grid size
    testOutOfBoundsPlaceCard(mediumGrid, 0, 6);   // Out of bounds column
    testOutOfBoundsPlaceCard(mediumGrid, 6, 4);   // Out of bounds row

    // Testing out-of-bounds indices on largeGrid (10x10)
    testOutOfBoundsPlaceCard(largeGrid, -1, 0);   // Negative row
    testOutOfBoundsPlaceCard(largeGrid, 10, 10);  // Out of bounds for grid size
    testOutOfBoundsPlaceCard(largeGrid, 0, 11);   // Out of bounds column
    testOutOfBoundsPlaceCard(largeGrid, 11, 9);   // Out of bounds row
  }

  // Helper method to test placeCard with out-of-bounds indices
  // Expects an IllegalArgumentException to be thrown.
  private void testOutOfBoundsPlaceCard(ThreeTriosGrid grid, int row, int col) {
    try {
      grid.placeCard(row, col, sampleCardDragon);
    } catch (IllegalArgumentException e) {
      // Expected exception, test passes for this index
      assertTrue(e.getMessage().contains("Invalid grid index"));
    }
  }


  // Tests getCardFromCell that contains a card.
  @Test
  public void testGetCardFromCellWithCard() {
    // Place a card in a specific cell (e.g., (2, 2))
    mediumGrid.placeCard(2, 2, sampleCardDragon);
    // Verify that getCardFromCell returns the correct card
    assertEquals(sampleCardDragon, mediumGrid.getCardFromCell(2, 2));
  }

  // Tests getCardFromCell from an empty cell (expecting null).
  @Test
  public void testGetCardFromEmptyCell() {
    assertNull(mediumGrid.getCardFromCell(1, 1));
  }

  // Tests getCardFromCell from an out-of-bounds cell, expecting ArrayIndexOutOfBoundsException.
  @Test
  public void testGetCardFromCellOutOfBounds() {
    try {
      mediumGrid.getCardFromCell(6, 6);  // Out of bounds for a 5x5 grid
      fail("Expected ArrayIndexOutOfBoundsException to be thrown for out-of-bounds access");
    } catch (ArrayIndexOutOfBoundsException e) {
      // Test passes if this exception is thrown
    }
  }


// Test battlePhase with valid params where the placed card has a higher attack
// Makes sure the opponent’s card is flipped and ownership changes accordingly.
@Test
public void testBattlePhaseHigherAttackFlipsOwnership() {
  // Place the high attack card for playerRed and low attack card for playerBlue
  mediumGrid.placeCard(2, 2, highAttackCard);  // Center position in 5x5 grid
  playerRed.addToOwned(highAttackCard);

  mediumGrid.placeCard(2, 3, lowAttackCard);   // Adjacent on the East
  playerBlue.addToOwned(lowAttackCard);

  // Invoke battlePhase, expecting highAttackCard to flip ownership of lowAttackCard
  mediumGrid.battlePhase(2, 2, highAttackCard, Direction.EAST, playerRed, playerBlue);

  // Assert that lowAttackCard now belongs to playerRed
  assertTrue(playerRed.owns(lowAttackCard));
  assertFalse(playerBlue.owns(lowAttackCard));
}

// Test battlePhase with valid parameters where the opponent’s card has a higher attack/
// Make's sure the opponent’s card ownership doesn't change.
@Test
public void testBattlePhaseLowerAttackNoFlip() {
  // Place low attack card for playerRed and high attack card for playerBlue
  mediumGrid.placeCard(2, 2, lowAttackCard);
  playerRed.addToOwned(lowAttackCard);

  mediumGrid.placeCard(2, 3, highAttackCard);
  playerBlue.addToOwned(highAttackCard);

  // Use battlePhase, expecting no change in ownership since opponent's attack is higher
  mediumGrid.battlePhase(2, 2, lowAttackCard, Direction.EAST, playerRed, playerBlue);

  // Assert that ownership of highAttackCard has not changed
  assertFalse(playerRed.owns(highAttackCard));
  assertTrue(playerBlue.owns(highAttackCard));
}

// Test battlePhase with out-of-bounds indices: Ensure no changes occur, and no exceptions thrown.
@Test
public void testBattlePhaseOutOfBoundsNoChange() {
  // Place a card in the grid for playerRed
  mediumGrid.placeCard(2, 2, highAttackCard);
  playerRed.addToOwned(highAttackCard);

  // Attempt to initiate battlePhase on an out-of-bounds cell (e.g., (5, 5))
  try {
    mediumGrid.battlePhase(5, 5, highAttackCard, Direction.EAST, playerRed, playerBlue);
  } catch (Exception e) {
    fail("Expected no exception thrown " +
            "for out-of-bounds battlePhase, instead it got: " + e.getMessage());
  }

  // Assert that no changes occurred, confirming ownership remains the same
  assertTrue(playerRed.owns(highAttackCard));
  assertFalse(playerBlue.owns(highAttackCard));
}

  // Test toString for an empty grids
  @Test
  public void testToStringEmptyGrid() {
    //Small empty grid
    String expectedOutput = "__\n__";
    System.out.println(smallGrid.toString(playerRed));
    assertEquals(expectedOutput, smallGrid.toString(playerRed));

    //Medium empty grid
    String expectedEmptyOutput = "_____\n_____\n_____\n_____\n_____";
    assertEquals(expectedEmptyOutput, mediumGrid.toString(playerRed));

    //Large empty grid
    StringBuilder expectedEmptyOutputLarge = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      expectedEmptyOutputLarge.append("__________\n");
    }
    assertEquals(expectedEmptyOutputLarge.toString().trim(), largeGrid.toString(playerRed));
  }

  // Test toString for a grid with red-owned card or blue-owned card
  @Test
  public void testToStringColorPlayerOwned() {
    //Small grid with a red-owned card
    smallGrid.placeCard(0, 0, sampleCardDragon);
    playerRed.addToOwned(sampleCardDragon);
    String expectedRedOwnedOutput = "R_\n__";
    assertEquals(expectedRedOwnedOutput, smallGrid.toString(playerRed));

    //Medium grid with a blue-owned card
    mediumGrid.placeCard(1, 1, sampleCardPhoenix);
    playerBlue.addToOwned(sampleCardPhoenix);
    String expectedBlueOwnedOutput = "_____\n_B___\n_____\n_____\n_____";
    assertEquals(expectedBlueOwnedOutput, mediumGrid.toString(playerRed));

    // Medium grid with red and blue cards
    mediumGrid.placeCard(2, 2, sampleCardDragon);
    mediumGrid.placeCard(0, 4, sampleCardPhoenix);
    String expectedMixedOutput = "____B\n_____\n__R__\n_____\n_____";

    //Large grid with a red-owned card
    largeGrid.placeCard(5, 5, sampleCardDragon);
    StringBuilder expectedCenterRedOutput = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      if (i == 5) {
        expectedCenterRedOutput.append("_____R____\n");
      } else {
        expectedCenterRedOutput.append("__________\n");
      }
    }
    assertEquals(expectedCenterRedOutput.toString().trim(), largeGrid.toString(playerRed));
  }


}


