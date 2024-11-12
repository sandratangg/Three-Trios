package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cs3500.threetrios.model.MockThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.strategies.MaximizeFlipsStrategy;
import cs3500.threetrios.strategies.Move;

public class MaximizeFlipsStrategyTests {
  private ReadOnlyThreeTriosModel mockModel;
  private MaximizeFlipsStrategy strategy;
  private List<ThreeTriosCard> hand;

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
    // The strategy should break ties by choosing the upper-leftmost position and the first card in hand
    assertTrue(chosenMove.isPresent());
    assertEquals(new Move(0, 1, hand.get(0)), chosenMove.get());
  }
}
