package cs3500.threetrios.controller;

import cs3500.threetrios.model.*;
import cs3500.threetrios.strategies.Move;
import cs3500.threetrios.strategies.ThreeTriosStrategy;
import java.util.Optional;

/**
 * Represents an AI player using a specified strategy.
 */
public class AIPlayer implements Player {
  private final ThreeTriosStrategy strategy;
  private final PlayerColor color;

  public AIPlayer(ThreeTriosStrategy strategy, PlayerColor color) {
    this.strategy = strategy;
    this.color = color;
  }

  @Override
  public boolean isHuman() {
    return false;
  }

  @Override
  public Optional<Move> makeMove(ReadOnlyThreeTriosModel model) {
    return strategy.chooseMove(model, color);
  }
}
