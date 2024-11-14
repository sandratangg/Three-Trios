package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cs3500.threetrios.model.MockThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.strategies.MaximizeFlipsStrategy;
import cs3500.threetrios.strategies.Move;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class contains unit tests for the {@link MaximizeFlipsStrategy} class,
 * which aims to choose moves that flip the maximum number of opponent's cards
 * in the Three Trios game. It tests the behavior of the strategy under various
 * game board configurations.
 */
public class MaximizeFlipsStrategyTests {

  private ReadOnlyThreeTriosModel mockModel;
  private MaximizeFlipsStrategy strategy;
  private List<ThreeTriosCard> hand;

  /**
   * Sets up the test environment by initializing the strategy and a hand of cards
   * before each test case is executed.
   */
  @Before
  public void setUp() {
    // Initialize the strategy and mock model
    strategy = new MaximizeFlipsStrategy();
    hand = Arrays.asList(
            new ThreeTriosCard("Card1", 5, 3, 4, 2),
            new ThreeTriosCard("Card2", 7, 1, 6, 4),
            new ThreeTriosCard("Card3", 2, 8, 5, 3)
    );
  }

  /**
   * Tests the strategy's ability to choose a move that maximizes the number
   * of flips when there is only one optimal move available.
   * It verifies that the chosen move is correct.
   */
  @Test
  public void testMaximizeFlipsSingleMove() {
    mockModel = new MockThreeTriosModel(
            new int[][]{
                    {1, 0, -1},
                    {0, -1, 1},
                    {-1, 1, 0}
            },
            hand,
            PlayerColor.RED
    );

    Optional<Move> chosenMove = strategy.chooseMove(mockModel, PlayerColor.RED);
    // Assert that the chosen move flips the maximum number of cards
    assertTrue(chosenMove.isPresent());
    assertEquals(new Move(0, 1, hand.get(0)), chosenMove.get());
  }

  /**
   * Tests the strategy's behavior when there are multiple moves that flip the
   * same number of cards. It ensures that the strategy breaks ties correctly
   * by choosing the upper-leftmost position and the first card in the hand.
   */
  @Test
  public void testMaximizeFlipsWithTies() {
    mockModel = new MockThreeTriosModel(
            new int[][]{
                    {1, 1, -1},
                    {0, -1, 0},
                    {-1, 1, 1}
            },
            hand,
            PlayerColor.RED
    );

    Optional<Move> chosenMove = strategy.chooseMove(mockModel, PlayerColor.RED);
    // The strategy breaks ties by choosing the upper-leftmost position and the first card in hand
    assertTrue(chosenMove.isPresent());
    assertEquals(new Move(0, 1, hand.get(0)), chosenMove.get());
  }
}
