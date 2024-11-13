package cs3500.threetrios.strategies;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;

import java.util.List;
import java.util.Optional;

public class MaximizeFlipsStrategy implements ThreeTriosStrategy {

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

            // Update the best move if we find a card that results in more flips
            if (flips > maxFlips) {
              bestMove = new Move(row, col, card);
              maxFlips = flips;
            } else if (flips == maxFlips && bestMove != null) {
              // Break ties by selecting the upper-leftmost position, and the first card
              if (row < bestMove.getRow() || (row == bestMove.getRow() && col < bestMove.getCol())) {
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
