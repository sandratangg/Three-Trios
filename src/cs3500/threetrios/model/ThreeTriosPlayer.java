package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 */
public class ThreeTriosPlayer {
  private final PlayerColor color;  // "Red" or "Blue"
  private final List<ThreeTriosCard> hand;

  /**
   * Constructor for a player.
   * @param color the color of the player
   * @param initialHand the initial hand of the player
   */
  public ThreeTriosPlayer(PlayerColor color, List<ThreeTriosCard> initialHand) {
    this.color = color;
    this.hand = new ArrayList<>(initialHand);
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
    return this.hand.contains(card);
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

}
