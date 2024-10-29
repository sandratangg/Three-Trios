package cs3500.ThreeTrios;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGameModel;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.model.ThreeTriosPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThreeTriosGridTests {

  private ThreeTriosGrid smallGrid;
  private ThreeTriosGrid mediumGrid;
  private ThreeTriosGrid largeGrid;
  private ThreeTriosCard sampleCardDragon;
  private ThreeTriosCard sampleCard;
  private ThreeTriosPlayer playerRed;
  private ThreeTriosPlayer playerBlue;

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
    sampleCardDragon = new ThreeTriosCard("Dragon", 5, 7, 6, 3);

    // Sample initial hands for players
    List<ThreeTriosCard> redInitialHand = new ArrayList<>();
    redInitialHand.add(sampleCardDragon); // Adding sampleCard to Red's hand
    redInitialHand.add(new ThreeTriosCard("Phoenix", 3, 8, 5, 7));

    List<ThreeTriosCard> blueInitialHand = new ArrayList<>();
    blueInitialHand.add(new ThreeTriosCard("Tiger", 6, 4, 9, 2));
    blueInitialHand.add(new ThreeTriosCard("Eagle", 7, 5, 4, 8));

    // Initialize players with colors and initial hands
    playerRed = new ThreeTriosPlayer(PlayerColor.RED, redInitialHand);
    playerBlue = new ThreeTriosPlayer(PlayerColor.BLUE, blueInitialHand);
  }


  // Test constructor with valid dimensions (positive rows and columns): Ensure the grid is created with empty cells.
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

// Test constructor with invalid dimensions (rows or columns <= 0): Expect IllegalArgumentException.
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

// Test getCardFromCell
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

// Test placeCard in a valid position on an empty cell: Ensure the card is successfully placed, and placeCard returns true.
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
@Test
  public void testPlaceCardHoleCell() {
    
  }


// Test placeCard with out-of-bounds indices: Ensure placeCard returns false without any exception.


// Test battlePhase with valid parameters where the placed card has a higher attack: Ensure the opponent’s card is flipped and ownership changes accordingly.


// Test battlePhase with valid parameters where the opponent’s card has a higher attack: Ensure no changes to the opponent’s card ownership.


// Test battlePhase with out-of-bounds indices: Ensure no changes occur, and no

  //Test getCardFromCell
}
