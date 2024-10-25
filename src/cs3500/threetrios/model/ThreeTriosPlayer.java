package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class ThreeTriosPlayer {
  private final PlayerColor color;  // "Red" or "Blue"
  private List<ThreeTriosCard> hand;
  private List<ThreeTriosCard> ownedCards;

  /**
   * Constructor for a player.
   * @param color the color of the player
   * @param initialHand the initial hand of the player
   */
  public ThreeTriosPlayer(PlayerColor color, List<ThreeTriosCard> initialHand) {
    this.color = color;
    this.hand = new ArrayList<>(initialHand);
    this.ownedCards = new ArrayList<>();
  }

  /**
   * Method to play a card.
   * @param card the card to play
   * @return true or false whether the card was played
   */
  public boolean playCard(ThreeTriosCard card) {
    if (this.hand.contains(card)) {
      this.hand.remove(card);
      return true;
    }
    return false;
  }

  /**
   * Method to add a card to the player's hand.
   * @param card the card to add
   */
  public void addCard(ThreeTriosCard card) {
    this.hand.add(card);  // Add card after a flip
  }

  /**
   * Method to check if the player owns a card.
   * @param card the card to check
   * @return true or false whether the player owns the card
   */
  public boolean owns(ThreeTriosCard card) {
    return this.ownedCards.contains(card);
  }

  /**
   * Method to check if the player is red.
   */
  public boolean isRed() {
    return PlayerColor.RED.equals(this.color);
  }

  /**
   * Method to check if the player is blue.
   */
  public boolean isBlue() {
    return PlayerColor.BLUE.equals(this.color);
  }

  /**
   * Method to print the player.
   * @return the player color as a string
   */
  @Override
  public String toString() {
    return "Player: " + this.color.toString();
  }

  public String handToString() {
    StringBuilder handString = new StringBuilder();

    for (ThreeTriosCard card : hand) {
      handString.append(card.toString()).append("\n");
    }

    return  handString.toString().trim();
  }

  public void removeFromHand(ThreeTriosCard cardToRemove) {
    if (!this.hand.contains(cardToRemove)) {
      throw new IllegalStateException("Cannot remove card since this hand does not contain it!");
    }
    this.hand.remove(cardToRemove);
  }

  public void addToHand(ThreeTriosCard cardToAdd) {
    if (!this.hand.contains(cardToAdd)) {
      throw new IllegalStateException("Cannot add card since this hand already contains it!");
    }
    this.hand.add(cardToAdd);
  }

  public void addToOwned(ThreeTriosCard cardToAdd) {
    if (!this.owns(cardToAdd)) {
      throw new IllegalStateException("Cannot add card since this player already owns it!");
    }
    this.ownedCards.add(cardToAdd);
  }

  public void removeFromOwned(ThreeTriosCard cardToRemove) {
    if (!this.owns(cardToRemove)) {
      throw new IllegalStateException("Cannot remove card since this player does not own it!");
    }
    this.ownedCards.remove(cardToRemove);
  }

  public int getOwnedCardsSize() {
    return this.ownedCards.size();
  }

}
