package cs3500.threetrios.strategies;

import java.util.Optional;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

/**
 * Represents a strategy for the Three Trios game. Implementations of this interface
 * define how a move is chosen for a player based on the current state of the game board.
 */
public interface ThreeTriosStrategy {

  /**
   * Determines the best move for the specified player based on the given game model.
   *
   * @param model the current read-only state of the game board
   * @param color the color of the player (RED or BLUE) for whom the move is being chosen
   * @return an Optional containing the chosen move, or empty if no valid moves are available
   */
  Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, PlayerColor color);
}
