package cs3500.threetrios.strategies;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * A wrapper strategy that logs the decisions made by a given strategy to a transcript file.
 * It records the current player, the board state, and the chosen move.
 */
public class TranscriptStrategyWrapper implements ThreeTriosStrategy {

  private final ThreeTriosStrategy wrappedStrategy;
  private final PrintWriter transcriptWriter;

  /**
   * Constructs a TranscriptStrategyWrapper for a given strategy. It logs the moves chosen
   * by the wrapped strategy to a file named "strategy-transcript.txt".
   *
   * @param strategy the strategy to wrap and log
   */
  public TranscriptStrategyWrapper(ThreeTriosStrategy strategy) {
    this.wrappedStrategy = strategy;
    try {
      // Initialize the writer to automatically append to the transcript file
      this.transcriptWriter = new PrintWriter(new FileWriter("strategy-transcript.txt",
              true));
    } catch (IOException e) {
      throw new RuntimeException("Failed to open transcript file", e);
    }
  }

  /**
   * Logs the board state and the player making the move, then delegates to the wrapped strategy
   * to choose the move. The chosen move is also logged.
   *
   * @param board  the current state of the game board
   * @param player the player making the move (RED or BLUE)
   * @return an Optional containing the chosen move, or empty if no move is possible
   */
  @Override
  public Optional<Move> chooseMove(ReadOnlyThreeTriosModel board, PlayerColor player) {
    // Log the player making the move
    transcriptWriter.println("Choosing move for player: " + player);

    // Log the current board state
    transcriptWriter.println("Current board state:");
    for (int row = 0; row < board.getGridHeight(); row++) {
      for (int col = 0; col < board.getGridWidth(); col++) {
        transcriptWriter.print(board.getCellContents(row, col) + " ");
      }
      transcriptWriter.println();
    }

    // Use the wrapped strategy to choose a move
    Optional<Move> chosenMove = wrappedStrategy.chooseMove(board, player);

    // Log the chosen move
    transcriptWriter.println("Chosen move: " + chosenMove.orElse(null));
    transcriptWriter.println("==================================");
    transcriptWriter.flush();

    return chosenMove;
  }

  /**
   * Closes the transcript file to ensure all logs are saved.
   * This method should be called when the wrapper is no longer needed.
   */
  public void close() {
    transcriptWriter.close();
  }
}
