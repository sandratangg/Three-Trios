package cs3500.threetrios.strategies;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import java.util.List;
import java.util.Optional;

/**
 * A strategy for the Three Trios game that selects the move that maximizes the number of flips.
 * If multiple moves result in the same number of flips, it breaks ties by choosing the
 * upper-leftmost position on the board and the first card in the player's hand.
 */
public class MaximizeFlipsStrategy implements ThreeTriosStrategy {

  /**
   * Chooses the move that flips the maximum number of opponent's cards. If there are ties,
   * it selects the move with the upper-leftmost position on the board.
   *
   * @param model        the current read-only state of the game board
   * @param playerColor  the color of the player making the move (RED or BLUE)
   * @return an Optional containing the chosen move, or empty if no valid moves are available
   */
  @Override
  public Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, PlayerColor playerColor) {
    List<ThreeTriosCard> hand = model.getPlayerHand(playerColor);
    if (hand.isEmpty()) {
      return Optional.empty();
    }

    int width = model.getGridWidth();
    int height = model.getGridHeight();
    Move bestMove = null;
    int maxFlips = -1;

    // Iterate over all positions on the grid
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        // Iterate over each card in the player's hand
        for (ThreeTriosCard card : hand) {
          if (model.isLegalMove(row, col, card)) {
            int flips = model.calculateFlippableCards(row, col, card);

            // Update the best move if a card results in more flips
            if (flips > maxFlips) {
              bestMove = new Move(row, col, card);
              maxFlips = flips;
            } else if (flips == maxFlips && bestMove != null) {
              // Break ties by choosing the upper-leftmost position and first card in hand
              if (row < bestMove.getRow() ||
                      (row == bestMove.getRow() && col < bestMove.getCol())) {
                bestMove = new Move(row, col, card);
              }
            }
          }
        }
      }
    }

    return Optional.ofNullable(bestMove);
  }
}
