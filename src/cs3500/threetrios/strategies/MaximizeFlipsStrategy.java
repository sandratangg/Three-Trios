package cs3500.threetrios.strategies;

import java.util.Optional;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosPlayer;

public class MaximizeFlipsStrategy implements ThreeTriosStrategy {
  public Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, PlayerColor color) {
    int maxFlips = 0;
    Move bestMove = null;

    for (int row = 0; row < model.getGridHeight(); row++) {
      for (int col = 0; col < model.getGridWidth(); col++) {
        if (!model.getCellContents(row, col).isEmpty()) continue;

        for (ThreeTriosCard card : model.getPlayerHand(color)) {
          int flips = model.calculateFlippableCards(row, col, card);
          if (flips > maxFlips) {
            maxFlips = flips;
            bestMove = new Move(row, col, card);
          } else if (flips == maxFlips) {
            if (bestMove == null || isUpperLeft(row, col, bestMove.getRow(), bestMove.getCol())) {
              bestMove = new Move(row, col, card);
            }
          }
        }
      }
    }

    return Optional.ofNullable(bestMove);
  }

  private boolean isUpperLeft(int row1, int col1, int row2, int col2) {
    return (row1 < row2) || (row1 == row2 && col1 < col2);
  }
}

