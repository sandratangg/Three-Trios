package cs3500.threetrios.model;

/**
 * Represents a player in the Three Trios game. A player has a hand of cards
 * and can play, add, and verify ownership of cards. This interface defines
 * essential actions and information retrieval methods related to the player's
 * hand, enabling interaction with game components and enforcing game rules.
 */
public interface IPlayer {

  /**
   * Attempts to play the specified card from the player's hand. The card
   * must belong to the player to be successfully played. Implementations
   * should remove the card from the player's hand if the play is valid.
   *
   * @param card the {@link ICard} to be played by the player.
   * @return true if the card was successfully played; false if the card
   *         is not in the player's hand.
   */
  boolean playCard(ICard card);

  /**
   * Adds a card to the player's hand. This method allows players to acquire
   * new cards during gameplay. Implementations should ensure proper hand
   * management, such as avoiding duplicate cards if required by game rules.
   *
   * @param card the {@link ICard} to be added to the player's hand.
   */
  void addCard(ICard card);

  /**
   * Checks if the player owns the specified card. Ownership implies that
   * the card exists within the player's hand. This method provides a way
   * to verify card possession for gameplay actions and validations.
   *
   * @param card the {@link ICard} to check for ownership.
   * @return true if the player owns the card; false otherwise.
   */
  boolean owns(ICard card);

  /**
   * Returns a string representation of the player's hand. This method
   * provides a summary of the cards in the player's possession, aiding
   * in debugging, display, or other game-related functionality.
   *
   * @return a string listing all cards currently in the player's hand.
   */
  String handToString();

  /**
   * Retrieves the number of cards in the player's hand. This method
   * allows for tracking hand size, which may impact game strategies
   * or indicate endgame conditions.
   *
   * @return the number of cards currently in the player's hand.
   */
  int getOwnedCardsSize();

}
