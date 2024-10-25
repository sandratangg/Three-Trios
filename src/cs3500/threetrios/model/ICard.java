package cs3500.threetrios.model;

/**
 * Represents a card in the game.
 */
public interface ICard {
  /**
   * Method to get the attack value for a specific direction.
    */
  int attack(Direction direction);

  /**
   * Method to check if the card name matches a given name
   */
  boolean matchesName(String name);
}
