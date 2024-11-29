package cs3500.threetrios.provider.model;

import java.util.List;

import cs3500.threetrios.provider.model.IGrid;
import cs3500.threetrios.provider.model.PlayerColor;

/**
 * Represents the read-only methods that a Three Trios model must implement. This read-only model is
 * used in the view to ensure that the client cannot modify the model's state, but still has access
 * to its state.
 */
public interface IThreeTriosViewModel {
  /**
   * Allows access to a copy of the player with the specified color's hand. Mutating this list
   * should not affect the internal state of the game.
   *
   * @param color the non-null PlayerColor value corresponding to the player in question
   *
   * @return a copy of the player's hand
   * @throws NullPointerException if the given color is null
   */
  List<IThreeTriosCard> getPlayerHand(cs3500.threetrios.provider.model.PlayerColor color);


  /**
   * Returns whether the game is over, or in other words when all the card cells on the game's grid
   * have been occupied.
   *
   * @return a boolean representing whether the game is over
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Returns the game's outcome, represented by an enum. The outcome of the game is determined by
   * the total number of cards a player owns, which includes the cards placed on the grid and the
   * cards in a player's hand.
   *
   * @return "Red wins" if red owns more cards at the end of the game, "Blue wins" if blue owns more
   *     cards, and "Tie" if both players own the same number of cards
   * @throws IllegalStateException if the game has not started
   * @throws IllegalStateException if the game is not over
   */
  GameOutcome getGameOutcome();

  /**
   * Returns the color of the player whose turn it currently is.
   */
  cs3500.threetrios.model.PlayerColor getCurrTurnPlayerColor();

  /**
   * Returns a copy of the grid. Mutating the return of this function should not affect the state of
   * the game.
   *
   * @return the grid object used in this model
   */
  IGrid getGrid();

  /**
   * Returns whether it is legal for the current player to play to the given grid position. A
   * position cannot be played to if it is out of bounds, if it is a hole, or if it is already
   * occupied by another card.
   *
   * @param gridRow the zero-based index of row on the grid to be played to. Rows are numbered from
   *                top to bottom, starting with 0 at the top.
   * @param gridCol the zero-based index of column on the grid to be played to. Columns are numbered
   *                from left to right, starting with 0 at the left.
   *
   * @return a boolean representing whether the given position can be played to
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if the given position is out of bounds
   */
  boolean isLegalMove(int gridRow, int gridCol);

  /**
   * Returns the potential number of cards that could be flipped if the current player plays a given
   * card in their hand to a given position on the grid. This method should not modify the game's
   * state.
   *
   * @param handIdx the zero-based index of the card in the player's hand
   * @param gridRow the zero-based index of row on the grid to be played to. Rows are numbered from
   *                top to bottom, starting with 0 at the top.
   * @param gridCol the zero-based index of column on the grid to be played to. Columns are numbered
   *                from left to right, starting with 0 at the left.
   *
   * @return a boolean representing whether the given position can be played to
   * @throws IllegalStateException    if the game has not started
   * @throws IllegalArgumentException if handIdx is out of bounds, or if gridRow and gridCol are out
   *                                  of bounds
   */
  int getPotentialCardsFlipped(int handIdx, int gridRow, int gridCol);

  /**
   * Returns the score of the player corresponding to the given color, which is the number of cards
   * that player owns in their hand, plus the number of cards that player owns on the grid.
   *
   * @param color the color of the player in question
   *
   * @return the player's score
   * @throws IllegalStateException if the game has not started
   * @throws NullPointerException  if color is null
   */
  int getPlayerScore(PlayerColor color);
}
