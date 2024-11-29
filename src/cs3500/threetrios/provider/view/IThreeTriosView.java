package cs3500.threetrios.provider.view;

import java.util.Optional;

import cs3500.threetrios.provider.model.PlayerColor;
import cs3500.threetrios.provider.controller.IThreeTriosPlayerFeatures;

/**
 * Represents the features in the view.
 */
public interface IThreeTriosView {

  /**
   * Registers the controller's feature callbacks with the view.
   *
   * @param features the controller's callbacks for handling user interactions
   *
   * @throws NullPointerException if features is null
   */
  void subscribe(IThreeTriosPlayerFeatures features);

  /**
   * Sets the visibility of the view. If the view is to be set to visible, this sets up the GUI
   * before doing so.
   *
   * @param visible whether the view should be visible
   *
   * @throws IllegalStateException if the game has not started
   */
  void setVisible(boolean visible);

  /**
   * Used to check if a player has selected a card in their hand.
   *
   * @param player the player in question
   *
   * @return a boolean that represents whether they have selected a card from their hand
   * @throws NullPointerException if player is null
   */
  boolean hasSelectedCard(PlayerColor player);

  /**
   * Returns the hand index corresponding to the card selected by a given player. If the player has
   * not selected a card, this will return an empty object.
   *
   * @param player the player in question
   *
   * @return the card selected by the player, or an empty object if there is no card selected
   * @throws NullPointerException if player is null
   */
  Optional<Integer> getSelectedHandCardIdx(PlayerColor player);

  /**
   * Sets the currently selected card for the given player to the given card index.
   *
   * @param player  the player in question
   * @param handIdx the hand index
   *
   * @throws IllegalArgumentException if handIdx is out of bounds
   * @throws NullPointerException     if player is null
   */
  void setSelectedCard(PlayerColor player, int handIdx);

  /**
   * Deselects the currently selected card in the given player's hand.
   *
   * @param player the player in question
   *
   * @throws NullPointerException if player is null
   */
  void clearCardSelection(PlayerColor player);

  /**
   * Shows a pop-up window that contains the given message.
   */
  void showDialog(String message);

  /**
   * Re-renders the entire view to reflect changes in the state of the game; including both player
   * hands and the grid.
   */
  void rerender();

  /**
   * Re-renders the representation of the player's hand displayed on the view to reflect changes in
   * the model's state.
   *
   * @param player the player color corresponding to the player who owns the hand in question
   *
   * @throws NullPointerException if player is null
   */
  void rerenderHand(PlayerColor player);
}
