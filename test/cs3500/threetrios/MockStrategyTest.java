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

public class MockStrategyTest {
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
