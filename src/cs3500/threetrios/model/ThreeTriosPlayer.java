package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Three Trios game. A player has a specific color
 * (red or blue), a hand of cards, and a list of cards they own on the grid.
 * This class manages player actions, such as playing cards, adding or removing
 * cards from the hand, and tracking owned cards.
 */
public class ThreeTriosPlayer implements IPlayer {

  private final PlayerColor color;  // "Red" or "Blue"
  private List<ICard> hand;
  private List<ICard> ownedCards;

  /**
   * Constructs a player with a specified color and an initial hand of cards.
   * Each player starts with an empty list of owned cards on the grid.
   *
   * @param color the color of the player, either red or blue.
   * @param initialHand the initial hand of cards for the player.
   */
  public ThreeTriosPlayer(PlayerColor color, List<ThreeTriosCard> initialHand) {
    this.color = color;
    this.hand = new ArrayList<>(initialHand);
    this.ownedCards = new ArrayList<>();
  }


  /**
   * Retrieves the color of the player.
   * @param card the ICard to be played by the player
   * @return true if the card was successfully played
   *         or false if the card is not in the player's hand.
   */
  public boolean playCard(ICard card) {
    if (this.hand.contains(card)) {
      this.hand.remove(card);
      return true;
    }
    return false;
  }

  /**
   * Adds a card to the player's hand.
   * @param card the ICard to be added to the player's hand
   */
  public void addCard(ICard card) {
    this.hand.add(card);  // Add card after a flip
  }

  /**
   * Checks if the player owns the specified card.
   * @param card the ICard to check for ownership
   * @return true if the player owns the card or false if otherwise.
   */
  public boolean owns(ICard card) {
    return this.ownedCards.contains(card);
  }

  /**
   * Checks if the player is red.
   *
   * @return true if the player is red; false otherwise.
   */
  public boolean isRed() {
    return PlayerColor.RED.equals(this.color);
  }

  /**
   * Checks if the player is blue.
   *
   * @return true if the player is blue; false otherwise.
   */
  public boolean isBlue() {
    return PlayerColor.BLUE.equals(this.color);
  }

  /**
   * Returns a string representation of the player.
   *
   * @return the player color as a string.
   */
  @Override
  public String toString() {
    return "Player: " + this.color.toString();
  }

  /**
   * Provides a string representation of the player's hand, listing each card
   * on a new line.
   *
   * @return a string displaying the player's hand of cards.
   */
  public String handToString() {
    StringBuilder handString = new StringBuilder();

    for (ICard card : hand) {
      handString.append(card.toString()).append("\n");
    }

    return handString.toString().trim();
  }

  /**
   * Removes a specified card from the player's hand.
   *
   * @param cardToRemove the card to remove from the hand.
   * @throws IllegalStateException if the card is not in the player's hand.
   */
  public void removeFromHand(ThreeTriosCard cardToRemove) {
    if (!this.hand.contains(cardToRemove)) {
      throw new IllegalStateException("Cannot remove card since this hand does not contain it!");
    }
    this.hand.remove(cardToRemove);
  }

  /**
   * Adds a specified card to the player's hand.
   *
   * @param cardToAdd the card to add to the hand.
   * @throws IllegalStateException if the card is already in the player's hand.
   */
  public void addToHand(ThreeTriosCard cardToAdd) {
    if (this.hand.contains(cardToAdd)) {
      throw new IllegalStateException("Cannot add card since this hand already contains it!");
    }
    this.hand.add(cardToAdd);
  }

  /**
   * Adds a card to the player's owned cards list. Ownership is transferred
   * when a card is captured during a battle phase.
   *
   * @param cardToAdd the card to add to the player's ownership.
   * @throws IllegalStateException if the player already owns the card.
   */
  public void addToOwned(ICard cardToAdd) {
    if (this.owns(cardToAdd)) {
      throw new IllegalStateException("Cannot add card since this player already owns it!");
    }
    this.ownedCards.add(cardToAdd);
  }

  /**
   * Removes a card from the player's owned cards list, used when ownership
   * is transferred to the opponent.
   *
   * @param cardToRemove the card to remove from the player's ownership.
   * @throws IllegalStateException if the player does not own the card.
   */
  public void removeFromOwned(ICard cardToRemove) {
    if (!this.owns(cardToRemove)) {
      throw new IllegalStateException("Cannot remove card since this player does not own it!");
    }
    this.ownedCards.remove(cardToRemove);
  }

  /**
   * Retrieves the number of cards owned by the player.
   *
   * @return the count of cards in the player's owned cards list.
   */
  public int getOwnedCardsSize() {
    return this.ownedCards.size();
  }

  public List<ICard> getOwnedCards() {
    return new ArrayList<ICard>(ownedCards);
  }

  public boolean sameColor(PlayerColor color) {
    return this.color.equals(color);
  }
}
