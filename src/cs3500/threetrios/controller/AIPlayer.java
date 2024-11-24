package cs3500.threetrios.controller;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.strategies.Move;
import cs3500.threetrios.strategies.ThreeTriosStrategy;
import java.util.Optional;

/**
 * Represents an AI player using a specified strategy.
 */
public class AIPlayer implements Player {
  private final ThreeTriosStrategy strategy;
  private final PlayerColor color;

  /**
   * Constructs an AI player with the given strategy and color.
   * @param strategy the strategy to use.
   * @param color the color of the player.
   */
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
