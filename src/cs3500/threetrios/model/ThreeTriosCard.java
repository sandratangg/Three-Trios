package cs3500.threetrios.model;

/**
 * Represents a card in the Three Trios game. This class encapsulates the
 * properties and behaviors associated with a game card, including its name
 * and attack values in four cardinal directions. Each card can perform
 * directional attacks, and its strength is determined by these values.
 *
 * <p>The ThreeTriosCard class is central to game mechanics, as it defines
 * how cards interact based on their attributes, affecting gameplay outcomes
 * through strategic placement and attack.
 */
public class ThreeTriosCard implements ICard {

  private final String cardName;

  private final int attackNorth;
  private final int attackEast;
  private final int attackWest;
  private final int attackSouth;

  /**
   * Constructs a ThreeTriosCard with specified directional attack values
   * and a name. Each attack value represents the card's strength in a given
   * direction, used in battle comparisons.
   *
   * @param name the name of the card, used for identification and gameplay interactions.
   * @param attackNorth the attack value for the north direction; must be between 1 and 10.
   * @param attackEast the attack value for the east direction; must be between 1 and 10.
   * @param attackWest the attack value for the west direction; must be between 1 and 10.
   * @param attackSouth the attack value for the south direction; must be between 1 and 10.
   * @throws IllegalArgumentException if any attack value is outside the range 1 to 10.
   */
  public ThreeTriosCard(String name, int attackNorth, int attackEast,
                        int attackWest, int attackSouth) {

    validCardAttackValue(attackNorth);
    validCardAttackValue(attackEast);
    validCardAttackValue(attackWest);
    validCardAttackValue(attackSouth);

    this.cardName = name;
    this.attackNorth = attackNorth;
    this.attackEast = attackEast;
    this.attackWest = attackWest;
    this.attackSouth = attackSouth;
  }

  // Helper : Validates the attack value of the card.
  private static void validCardAttackValue(int attackValue) {
    if (attackValue < 1 || attackValue > 10) {
      throw new IllegalArgumentException("Value must be between 1 and 10.");
    }
  }

  public String getName() {
    return this.cardName;
  }

  /**
   * Gets the attack value of the card for a specified direction.
   * @param direction uses the Direction enum to get the card's attack value
   * @return the attack value for the specified direction as an integer.
   */
  public int attack(Direction direction) {
    switch (direction) {
      case NORTH:
        return this.attackNorth;
      case EAST:
        return this.attackEast;
      case WEST:
        return this.attackWest;
      case SOUTH:
        return this.attackSouth;
      default:
        throw new IllegalArgumentException("Invalid direction.");
    }
  }

  /**
   * Checks if the card's name matches a given name. This method allows for
   * indirect card identification without exposing the name directly.
   *
   * @param name the name to check against the card's name.
   * @return true if the given name matches the card's name; false otherwise.
   */
  public boolean matchesName(String name) {
    return this.cardName.equals(name);
  }



  /**
   * Provides a string representation of the card, formatted as
   * "CardName X Y Z W" where each letter represents an attack value
   * (with 10 represented as 'A'). This method offers a human-readable
   * display of the card's properties, aiding in debugging and gameplay.
   *
   * @return a string showing the card's name and attack values in order.
   */
  @Override
  public String toString() {
    return this.cardName + " " + buildAttackString();
  }

  // Helper : Converts attack values to string, handling 10 as 'A'.
  private String convertAttackValue(int attackValue) {
    if (attackValue == 10) {
      return "A";
    } else {
      return Integer.toString(attackValue);
    }
  }

  // Helper : Constructs the string representation of attack values for the card.
  private String buildAttackString() {
    return convertAttackValue(attackNorth)
            + " "
            + convertAttackValue(attackEast)
            + " "
            + convertAttackValue(attackWest)
            + " "
            + convertAttackValue(attackSouth);
  }

}
