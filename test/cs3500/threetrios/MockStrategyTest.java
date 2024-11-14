package cs3500.threetrios;

import cs3500.threetrios.model.MockThreeTriosModel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.strategies.CornerPreferenceStrategy;
import cs3500.threetrios.strategies.TranscriptStrategyWrapper;
import cs3500.threetrios.strategies.ThreeTriosStrategy;
import cs3500.threetrios.strategies.Move;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class serves as a test for evaluating the behavior of a specific strategy
 * within the Three Trios game. It sets up a mock game model and applies a strategy
 * to determine the best move.
 */
public class MockStrategyTest {

  /**
   * The main method to run the test. It initializes a 3x3 game board with no holes,
   * creates a hand of cards, and applies a strategy to choose the best move.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    // Initialize a 3x3 board with no holes (0 represents an empty cell)
    int[][] board = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    // Example hand of cards (using placeholders)
    List<ThreeTriosCard> hand = Arrays.asList(
            new ThreeTriosCard("Card1", 5, 3, 7, 4),
            new ThreeTriosCard("Card2", 2, 6, 5, 3)
    );

    // Initialize the mock model
    MockThreeTriosModel model = new MockThreeTriosModel(board, hand, PlayerColor.RED);

    // Wrap the chosen strategy with the transcript wrapper
    ThreeTriosStrategy baseStrategy = new CornerPreferenceStrategy();
    TranscriptStrategyWrapper strategyWithTranscript = new TranscriptStrategyWrapper(baseStrategy);

    // Run the strategy and generate the transcript
    Optional<Move> move = strategyWithTranscript.chooseMove(model, PlayerColor.RED);
    System.out.println("Final chosen move: " + move.orElse(null));

    // Close the transcript file
    strategyWithTranscript.close();
  }
}
