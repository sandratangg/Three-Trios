package cs3500.threetrios.provider.view;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.JPanel;

import cs3500.threetrios.provider.model.IThreeTriosCard;


/**
 * Represents a panel that displays the cards that a player currently has in their hand.
 */
abstract class AbstractPlayerHandPanel extends JPanel {
  /**
   * Sets up a given callback function when a card in the hand is clicked.
   *
   * @param onClick the function that is run when a card in the hand is clicked. This BiConsumer
   *                takes in two specific values: the owner of the hand, and the hand index clicked
   *                respectively. The hand index is zero-indexed, and corresponds to the hand index
   *                specified by the model.
   *
   * @throws NullPointerException if onClick is null
   */
  abstract void setCardOnClick(Consumer<Integer> onClick);

  /**
   * Deselects the currently selected card in the hand panel.
   *
   * @throws IllegalStateException if the hand panel has not received the player hand yet
   */
  abstract void clearCardSelection();

  /**
   * Sets the currently selected card to the card at the given hand index.
   *
   * @param handIdx the card's hand index
   *
   * @throws IllegalArgumentException if handIdx is out of bounds
   * @throws IllegalStateException    if the hand panel has not received the player hand yet
   */
  abstract void setSelectedCard(int handIdx);

  /**
   * Returns the hand index of the currently selected card, or an empty object is no card is
   * currently selected.
   *
   * @return the hand index of the currently selected card, or an empty object is no card is
   *     currently selected
   * @throws IllegalStateException if the hand panel has not received the player hand yet
   */
  abstract Optional<Integer> getSelectedHandIdx();

  /**
   * Indicates whether the player that owns this hand has currently selected a card.
   *
   * @return a boolean representing whether the that owns this hand has currently selected a card
   * @throws IllegalStateException if the hand panel has not received the player hand yet
   */
  abstract boolean hasSelectedCard();

  /**
   * Updates the player hand data stored by the hand panel.
   *
   * @param playerHand a list of cards representing the player's hand
   *
   * @throws NullPointerException if playerHand is null
   */
  abstract void setPlayerHand(List<IThreeTriosCard> playerHand);
}