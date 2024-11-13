package cs3500.threetrios.strategies;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class TranscriptStrategyWrapper implements ThreeTriosStrategy {
  private final ThreeTriosStrategy wrappedStrategy;
  private final PrintWriter transcriptWriter;

  public TranscriptStrategyWrapper(ThreeTriosStrategy strategy) {
    this.wrappedStrategy = strategy;
    try {
      // Initialize the writer to automatically generate the file
      this.transcriptWriter = new PrintWriter(new FileWriter("strategy-transcript.txt", true));
    } catch (IOException e) {
      throw new RuntimeException("Failed to open transcript file", e);
    }
  }

  @Override
  public Optional<Move> chooseMove(ReadOnlyThreeTriosModel board, PlayerColor player) {
    // Log player color
    transcriptWriter.println("Choosing move for player: " + player);

    // Log the board state row by row
    transcriptWriter.println("Current board state:");
    for (int row = 0; row < board.getGridHeight(); row++) {
      for (int col = 0; col < board.getGridWidth(); col++) {
        transcriptWriter.print(board.getCellContents(row, col) + " ");
      }
      transcriptWriter.println();
    }

    // Call the original strategy's chooseMove method
    Optional<Move> chosenMove = wrappedStrategy.chooseMove(board, player);

    // Log the chosen move
    transcriptWriter.println("Chosen move: " + chosenMove.orElse(null));
    transcriptWriter.println("==================================");
    transcriptWriter.flush();

    return chosenMove;
  }


  // Ensure the file is properly closed when the wrapper is done
  public void close() {
    transcriptWriter.close();
  }
}
