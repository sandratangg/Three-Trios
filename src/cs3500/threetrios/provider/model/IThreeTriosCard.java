package cs3500.threetrios.provider.model;

/**
 * Represents a card in a game of Three Trios. A card has a name, and attack values for all 4
 * cardinal directions (North, East, South, and West). A card can be flipped when it changes owners,
 * which causes its color to be changed.
 */
public interface IThreeTriosCard {
  /**
   * Returns the card's attack value in the given direction.
   *
   * @param direction the desired direction
   *
   * @return the attack value corresponding to the given direction
   * @throws NullPointerException if direction is null
   */
  int getAttackValue(AttackDirection direction);

  /**
   * Get the name of the card.
   *
   * @return the name of the card
   */
  String getName();
}
