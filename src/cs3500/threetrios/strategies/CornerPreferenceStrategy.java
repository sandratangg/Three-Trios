package cs3500.threetrios.strategies;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;

import java.util.List;
import java.util.Optional;

public class CornerPreferenceStrategy implements ThreeTriosStrategy {

  @Override
  public Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, PlayerColor playerColor) {
    List<ThreeTriosCard> hand = model.getPlayerHand(playerColor);
    if (hand.isEmpty()) {
      return Optional.empty();
    }

    int width = model.getGridWidth();
    int height = model.getGridHeight();

    // List of corners in the order: top-left, top-right, bottom-left, bottom-right
    int[][] corners = {{0, 0}, {0, width - 1}, {height - 1, 0}, {height - 1, width - 1}};

    // Try placing in the corners first
    for (int[] corner : corners) {
      for (ThreeTriosCard card : hand) {
        if (model.isLegalMove(corner[0], corner[1], card)) {
          return Optional.of(new Move(corner[0], corner[1], card));
        }
      }
    }

    // If no corners are available, try any other valid position
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        for (ThreeTriosCard card : hand) {
          if (model.isLegalMove(row, col, card)) {
            return Optional.of(new Move(row, col, card));
          }
        }
      }
    }

    // No valid moves available
    return Optional.empty();
  }
}
