package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import cs3500.threetrios.model.MockThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.strategies.CornerPreferenceStrategy;
import cs3500.threetrios.strategies.Move;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CornerPreferenceStrategyTests {
  private ReadOnlyThreeTriosModel mockModel;
  private CornerPreferenceStrategy strategy;
  private List<ThreeTriosCard> hand;

  @Before
  public void setUp() {
    strategy = new CornerPreferenceStrategy();
    hand = Arrays.asList(
            new ThreeTriosCard("Card1", 3, 6, 2, 5),
            new ThreeTriosCard("Card2", 4, 4, 4, 4),
            new ThreeTriosCard("Card3", 8, 1, 7, 3)
    );
  }

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
    assertEquals(new Move(0, 0, hand.get(0)), chosenMove.get()); // Chooses the upper-left corner
  }

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
    assertEquals(new Move(1, 1, hand.get(0)), chosenMove.get()); // Falls back to upper-leftmost non-corner
  }

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
