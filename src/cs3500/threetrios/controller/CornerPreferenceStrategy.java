package cs3500.threetrios.controller;

import java.util.List;
import java.util.Optional;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosPlayer;

class CornerPreferenceStrategy implements ThreeTriosStrategy {
  public Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, ThreeTriosPlayer player) {
    int gridHeight = model.getGridHeight();
    int gridWidth = model.getGridWidth();
    List<int[]> corners = List.of(
            new int[]{0, 0},
            new int[]{0, gridWidth - 1},
            new int[]{gridHeight - 1, 0},
            new int[]{gridHeight - 1, gridWidth - 1}
    );

    Move bestMove = null;
    int minOpponentFlips = Integer.MAX_VALUE;

    for (int[] corner : corners) {
      int row = corner[0];
      int col = corner[1];

      if (!model.getCellContents(row, col).isEmpty()) continue;

      for (ThreeTriosCard card : player.getHandCards()) {
        int opponentFlips = model.calculateFlippableCards(row, col, card);
        if (opponentFlips < minOpponentFlips) {
          minOpponentFlips = opponentFlips;
          bestMove = new Move(row, col, card);
        } else if (opponentFlips == minOpponentFlips) {
          if (bestMove == null || isUpperLeft(row, col, bestMove.row, bestMove.col)) {
            bestMove = new Move(row, col, card);
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
