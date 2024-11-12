package cs3500.threetrios.strategies;

import java.util.Optional;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosPlayer;

public interface ThreeTriosStrategy {
  Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, PlayerColor color);
}