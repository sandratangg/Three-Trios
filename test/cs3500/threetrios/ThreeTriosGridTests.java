package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.FileReader;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGrid;
import cs3500.threetrios.model.ThreeTriosPlayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Test suite for the ThreeTriosGrid class.
 */
public class ThreeTriosGridTests {

  private ThreeTriosCard sampleCardPhoenix;

  /**
   * Initializes common objects before each test.
   */
  @Before
  public void init() {
    // Sample grids of varying sizes
    ThreeTriosGrid smallGrid = new ThreeTriosGrid(2, 2);   // Small 2x2 grid
    ThreeTriosGrid mediumGrid = new ThreeTriosGrid(5, 5);  // Medium 5x5 grid
    ThreeTriosGrid largeGrid = new ThreeTriosGrid(10, 10); // Large 10x10 grid

    // Sample cards for testing
    ThreeTriosCard sampleCardDragon = new ThreeTriosCard("Dragon", 5, 7,
            6, 3);
    sampleCardPhoenix = new ThreeTriosCard("Phoenix", 3, 8, 5,
            7);

    // Sample hands for players
    List<ThreeTriosCard> redInitialHand = new ArrayList<>();
    redInitialHand.add(sampleCardDragon);
    redInitialHand.add(sampleCardPhoenix);

    List<ThreeTriosCard> blueInitialHand = new ArrayList<>();
    blueInitialHand.add(new ThreeTriosCard("Tiger", 6, 4, 9,
            2));
    blueInitialHand.add(new ThreeTriosCard("Eagle", 7, 5, 4,
            8));

    // Initialize players
    ThreeTriosPlayer playerRed = new ThreeTriosPlayer(PlayerColor.RED, redInitialHand);
    ThreeTriosPlayer playerBlue = new ThreeTriosPlayer(PlayerColor.BLUE, blueInitialHand);

    // Cards for battle testing
    ThreeTriosCard highAttackCard = new ThreeTriosCard("Dragon", 8, 7,
            6, 5);
    ThreeTriosCard lowAttackCard = new ThreeTriosCard("Phoenix", 3, 3,
            3, 3);
  }

  /**
   * Tests placing a card in a hole cell (invalid placement).
   * Uses `FileReader` to read a grid from file.
   */
  @Test
  public void testPlaceCardHoleCell() throws FileNotFoundException {
    ThreeTriosGrid gridFile = FileReader.gridFromFile("docs/ExampleBoards/board2.txt");
    sampleCardPhoenix = new ThreeTriosCard("Dragon", 5, 7, 6,
            3);

    try {
      gridFile.placeCard(1, 2, sampleCardPhoenix);
      fail("Expected IllegalArgumentException for illegal card placement");
    } catch (IllegalArgumentException e) {
      // Test passes if IllegalArgumentException is thrown
      assertTrue(e.getMessage().contains("Illegal card placement"));
    }
  }

  /**
   * Tests reading a grid configuration from a file using `FileReader`.
   */
  @Test
  public void testGridConfigParsingBoard() throws FileNotFoundException {
    ThreeTriosGrid grid = FileReader.gridFromFile("docs/ExampleBoards/board1.txt");
    assertNotNull("Grid should be initialized", grid);
    assertEquals("Grid should have 2 rows", 2, grid.getRowCount());
    assertEquals("Grid should have 2 columns", 2, grid.getColCount());
    assertEquals("Grid should have all card cells", 4, grid.getNumCardCells());
    assertEquals("Grid should have no holes", 0, grid.getNumHoleCells());

    grid = FileReader.gridFromFile("docs/ExampleBoards/board2.txt");
    assertEquals("Grid should have 5 rows", 5, grid.getRowCount());
    assertEquals("Grid should have 5 columns", 5, grid.getColCount());
    assertEquals("Grid should have 13 card cells", 13, grid.getNumCardCells());
    assertEquals("Grid should have 12 holes", 12, grid.getNumHoleCells());

    grid = FileReader.gridFromFile("docs/ExampleBoards/board3.txt");
    assertEquals("Grid should have 5 rows", 5, grid.getRowCount());
    assertEquals("Grid should have 5 columns", 5, grid.getColCount());
    assertEquals("Grid should have 6 card cells", 6, grid.getNumCardCells());
    assertEquals("Grid should have 19 holes", 19, grid.getNumHoleCells());
  }
}
