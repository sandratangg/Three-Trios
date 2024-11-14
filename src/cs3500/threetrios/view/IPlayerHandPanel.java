package cs3500.threetrios.view;

/**
 * Represents the interface for the player's hand panel in the Three Trios game.
 * Provides methods to highlight and deselect cards in the player's hand.
 */
public interface IPlayerHandPanel {

  /**
   * Highlights the card at the specified index in the player's hand.
   * The highlighted card will be visually distinguished (e.g., with a border).
   *
   * @param cardIndex the index of the card to highlight
   */
  void highlightCard(int cardIndex);

  /**
   * Deselects any currently highlighted card in the player's hand.
   * Removes any visual indication of selection.
   */
  void deselectCard();
}
