package cs3500.threetrios.model;

/**
 * Represents a card in the ThreeTrios game.
 */
public class Card {

  private final String name;

  private final int attackNorth;
  private final int attackEast;
  private final int attackWest;
  private final int attackSouth;

  public Card(String name, int attackNorth, int attackEast, int attackWest, int attackSouth) {

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

  private static void validCardAttackValue(int attackValue) {
    if (attackValue < 1 || attackValue > 10) {
      throw new IllegalArgumentException("value must be between 1 and 10");
    }
  }

  // Instead of getters, provide a more general method to return the attack value based on direction

  //Instead of this attack getter, we can write a Compare attack value helper method that returns
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

  // No need to expose the card name directly, as it can be used within the game flow itself
  public boolean matchesName(String name) {
    return this.name.equals(name);
  }
}
