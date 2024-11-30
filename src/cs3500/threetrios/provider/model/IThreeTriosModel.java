package cs3500.threetrios.provider.model;

import cs3500.threetrios.provider.controller.IThreeTriosModelObserver;

/**
 * The behaviours for a two-player game of Three Trios. The game consists of a grid to which players
 * can play to with playable and unplayable cells, and a hand for each player. The game ends when
 * all the playable cells on the grid are full, and the goal of the game is to own as many cards as
 * possible when the game ends.
 */
public interface IThreeTriosModel extends IThreeTriosViewModel {
  /**
   * Starts the game and notifies the current player controller that it is their turn to play.
   *
   * @throws IllegalStateException if either or both player controllers have not been registered
   *                               with the model
   * @throws IllegalStateException if the game is already over, or the game has already been
   *                               started
   */
  void startGame();

  /**
   * Plays a card from the hand of the current turn player's hand, and updates the game's turn state
   * accordingly: if the previous player was red, the next turn should be blue, and vice versa. The
   * battle phase should then be initiated for the played card.
   *
   * @param handIdx the zero-based index position of the card in the player's hand
   * @param gridRow the zero-based index of row on the grid to be played to. Rows are numbered from
   *                top to bottom, starting with 0 at the top.
   * @param gridCol the zero-based index of column on the grid to be played to. Columns are numbered
   *                from left to right, starting with 0 at the left.
   * @throws IllegalArgumentException if the given handIdx, gridRow, or gridCol is out of bounds
   * @throws IllegalArgumentException if the given position is already occupied, or is a card hole
   * @throws IllegalStateException    if the game has not started, or if the game is already over
   * @throws IllegalStateException    if the player has already played to the grid this turn
   */
  void currPlayerPlayToGrid(int handIdx, int gridRow, int gridCol);

  /**
   * Ends the turn of the current player, notifies both controllers that the game state has updated,
   * and notifies the next player controller that it is their turn if the game is not over.
   *
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the player has not played to the grid yet this turn
   */
  void currPlayerEndTurn();

  /**
   * Subscribes a given controller to changes in player turn so that they may be notified when it is
   * their turn.
   *
   * @param controller the controller in question
   * @throws IllegalStateException if the given player color is already taken by another controller
   * @throws NullPointerException  if controller is null
   */
  void subscribe(IThreeTriosModelObserver controller);
}
