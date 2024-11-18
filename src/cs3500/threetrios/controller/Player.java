package cs3500.threetrios.controller;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.strategies.Move;
import java.util.Optional;

/**
 * Interface representing a player in the Three Trios game.
 */
public interface Player {
  boolean isHuman();
  Optional<Move> makeMove(ReadOnlyThreeTriosModel model);
}
