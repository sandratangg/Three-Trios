package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.MockThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.strategies.CornerPreferenceStrategy;
import cs3500.threetrios.strategies.Move;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class contains unit tests for the {@link CornerPreferenceStrategy} class,
 * which prioritizes placing cards in corner positions on the board in the Three Trios game.
 * It tests the behavior of the strategy under various board configurations.
 */
public class CornerPreferenceStrategyTests {

  private ReadOnlyThreeTriosModel mockModel;
  private CornerPreferenceStrategy strategy;
  private List<ThreeTriosCard> hand;

  /**
   * Sets up the test environment by initializing the strategy and a hand of cards
   * before each test case is executed.
   */
  @Before
  public void setUp() {
    strategy = new CornerPreferenceStrategy();
    hand = Arrays.asList(
            new ThreeTriosCard("Card1", 3, 6, 2, 5),
            new ThreeTriosCard("Card2", 4, 4, 4, 4),
            new ThreeTriosCard("Card3", 8, 1, 7, 3)
    );
  }

  /**
   * Tests the strategy's ability to choose a corner position when available.
   * The strategy should prioritize the upper-left corner.
   */
  @Test
  public void testChooseCorner() {
    mockModel = new MockThreeTriosModel(
            new int[][]{
                    {0, -1, 0},
                    {-1, 1, -1},
                    {0, -1, 0}
            },
            hand,
            PlayerColor.BLUE
    );

    Optional<Move> chosenMove = strategy.chooseMove(mockModel, PlayerColor.BLUE);
    assertTrue(chosenMove.isPresent());
    // Chooses the upper-left corner
    assertEquals(new Move(0, 0, hand.get(0)), chosenMove.get());
  }

  /**
   * Tests the strategy's behavior when all corners are already occupied.
   * It should fall back to the upper-leftmost available non-corner position.
   */
  @Test
  public void testAllCornersOccupied() {
    mockModel = new MockThreeTriosModel(
            new int[][]{
                    {1, -1, 1},
                    {-1, 0, -1},
                    {1, -1, 1}
            },
            hand,
            PlayerColor.RED
    );

    Optional<Move> chosenMove = strategy.chooseMove(mockModel, PlayerColor.RED);
    assertTrue(chosenMove.isPresent());
    // Falls back to upper-leftmost non-corner
    assertEquals(new Move(1, 1, hand.get(0)), chosenMove.get());
  }

  /**
   * Tests the strategy's behavior when there are no valid moves left on the board.
   * The strategy should return an empty result.
   */
  @Test
  public void testNoValidCorners() {
    mockModel = new MockThreeTriosModel(
            new int[][]{
                    {1, 1, 1},
                    {1, -1, 1},
                    {1, 1, 1}
            },
            hand,
            PlayerColor.BLUE
    );

    Optional<Move> chosenMove = strategy.chooseMove(mockModel, PlayerColor.BLUE);
    assertFalse(chosenMove.isPresent()); // No valid moves available
  }
}
