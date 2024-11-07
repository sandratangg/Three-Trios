package cs3500.threetrios.strategies;

import java.util.Optional;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosPlayer;

class FallbackStrategy implements ThreeTriosStrategy {
  public Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, ThreeTriosPlayer player) {
    for (int row = 0; row < model.getGridHeight(); row++) {
      for (int col = 0; col < model.getGridWidth(); col++) {
        if (model.getCellContents(row, col).isEmpty()) {
          return Optional.of(new Move(row, col, player.getHandCards().get(0)));
        }
      }
    }
    return Optional.empty(); // No open positions
  }
}
