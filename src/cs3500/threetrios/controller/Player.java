package cs3500.threetrios.controller;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.strategies.Move;
import java.util.Optional;

/**
 * Interface representing a player in the Three Trios game.
 */
public interface Player {
  /**
   * Returns whether the player is human or AI.
   * @return true if the player is human, false if AI
   */
  boolean isHuman();

  /**
   * Makes a move in the game based on the given model.
   * @param model the read-only model of the game
   * @return an optional containing the move to make, or empty if the player is human
   */
  Optional<Move> makeMove(ReadOnlyThreeTriosModel model);
}