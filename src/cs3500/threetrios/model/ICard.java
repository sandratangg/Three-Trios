package cs3500.threetrios.model;

/**
 * Represents a card within the Three Trios game, encapsulating properties
 * and behaviors of a card. Cards are a central component of the game,
 * contributing to gameplay through directional attacks and matching checks.
 * This interface provides methods to retrieve a card's attack power
 * in specific directions and verify its identity by name.
 */
public interface ICard {

  /**
   * Retrieves the attack value of the card for a specified direction.
   * Different directions may yield different attack strengths, enabling
   * strategic gameplay based on positioning. Implementations of this
   * method should consider all valid directions for attack.
   *
   * @param direction the {@link Direction} in which to retrieve the
   *                  card's attack value.
   * @return the attack value for the specified direction as an integer.
   */
  int attack(Direction direction);

  /**
   * Checks if the card's name matches the specified name string.
   * This method is intended to provide a straightforward comparison
   * for card identification purposes during gameplay. A card match
   * indicates that two cards share the same name.
   *
   * @param name the name to check against the card's own name.
   * @return true if the card's name matches the provided name; false otherwise.
   */
  boolean matchesName(String name);

  /**
   * Gets the name of the card as a string.
   * @return the name of the card as a string
   */
  String getName();

  boolean equals(ICard card);
}
