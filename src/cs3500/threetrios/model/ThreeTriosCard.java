package cs3500.threetrios.model;

/**
 * Represents a card in the ThreeTrios game.
 */
public class ThreeTriosCard implements ICard {

  private final String name;

  private final int attackNorth;
  private final int attackEast;
  private final int attackWest;
  private final int attackSouth;

  /**
   * Constructor for the ThreeTriosCard.
   * @param name the name of the card
   * @param attackNorth the attack value for the north direction
   * @param attackEast the attack value for the east direction
   * @param attackWest the attack value for the west direction
   * @param attackSouth the attack value for the south direction
   */
  public ThreeTriosCard(String name, int attackNorth, int attackEast, int attackWest, int attackSouth) {

    validCardAttackValue(attackNorth);
    validCardAttackValue(attackEast);
    validCardAttackValue(attackWest);
    validCardAttackValue(attackSouth);

    this.name = name;
    this.attackNorth = attackNorth;
    this.attackEast = attackEast;
    this.attackWest = attackWest;
    this.attackSouth = attackSouth;
  }

  // Helper : Validates the attack value of the card
  private static void validCardAttackValue(int attackValue) {
    if (attackValue < 1 || attackValue > 10) {
      throw new IllegalArgumentException("Value must be between 1 and 10");
    }
  }

  // Instead of getters, provide a more general method to return the attack value based on direction
  // Instead of this attack getter, we can write a Compare attack value helper method that returns
  // an int (1 for greater, 0 for equal, and -1 for less than)

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
        throw new IllegalArgumentException("Invalid direction");
    }
  }


  /**
   * Method to check if the card name matches a given name.
   * @param name the name to check
   * @return true if the name matches, false otherwise
   * No need to expose the card name directly, as it can be used within the game flow itself
   */
  public boolean matchesName(String name) {
    return this.name.equals(name);
  }


  /**
   * Returns a string representation of the card.
   * Example: "CardName 1 2 3 4"
   * Note: If the attack value is 10, it is represented as 'A'.
   */
  @Override
  public String toString() {
    return this.name + " " + buildAttackString();
  }

  // Helper : Converts attack values to string, handling 10 as 'A'
  private String convertAttackValue(int attackValue) {
    if (attackValue == 10) {
      return "A";
    } else {
      return Integer.toString(attackValue);
    }
  }

  // Helper : Constructs the string representation for the card
  private String buildAttackString() {
    return convertAttackValue(attackNorth) + " " +
            convertAttackValue(attackEast) + " " +
            convertAttackValue(attackWest) + " " +
            convertAttackValue(attackSouth);
  }

}
