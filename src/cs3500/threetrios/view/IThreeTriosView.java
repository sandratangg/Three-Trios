package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;

/**
 * Represents the interface for the view in the Three Trios game.
 * Provides methods to update the game grid, display messages,
 * highlight cards, and deselect cards.
 */
public interface IThreeTriosView {

  /**
   * Sets the game grid panel to be displayed.
   *
   * @param gridPanel the new game grid panel to set
   */
  void setGrid(IGameGridPanel gridPanel);

  /**
   * Displays a message to the user, such as the current player's turn or
   * game status updates.
   *
   * @param message the message to display
   */
  void showMessage(String message);

  /**
   * Highlights a card in the specified player's hand.
   *
   * @param cardIndex   the index of the card to highlight
   * @param playerColor the color of the player (RED or BLUE)
   */
  void highlightCard(int cardIndex, PlayerColor playerColor);

  /**
   * Deselects any currently highlighted card in both players' hands.
   */
  void deselectCard();
}
