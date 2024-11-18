package cs3500.threetrios.controller;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.strategies.Move;

import java.util.Optional;

/**
 * Represents a human player in the Three Trios game.
 * Human players interact via the GUI.
 */
public class HumanPlayer implements Player {
  @Override
  public boolean isHuman() {
    return true;
  }

  @Override
  public Optional<Move> makeMove(ReadOnlyThreeTriosModel model) {
    return Optional.empty(); // Human players do not make automated moves
  }
}
