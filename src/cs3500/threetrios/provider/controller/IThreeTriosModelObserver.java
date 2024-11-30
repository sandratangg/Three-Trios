package cs3500.threetrios.provider.controller;


import cs3500.threetrios.model.PlayerColor;

/**
 * Features interface for the controller to receive notifications from the model.
 */
public interface IThreeTriosModelObserver {
  /**
   * Prompts the player who the controller is responsible for to play its turn. This should also
   * rerender the view to update the board, since the other player just played.
   */
  void playTurn();

  /**
   * Notifies the controller that the state of the game has changed. Game state updates occur when
   * the player turn changes, the game ends, or when a player plays to the grid.
   */
  void handleGameStateUpdate();

  /**
   * Returns the player color that this model is associated with.
   *
   * @return the player color trhis model is associated with
   */
  PlayerColor playerColor();
}
