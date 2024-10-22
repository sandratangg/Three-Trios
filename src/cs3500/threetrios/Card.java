package cs3500.threetrios;

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
      throw new IllegalArgumentException("value must be an > 1 and < 10");
    }
  }

  public int getAttackValue(Direction direction) {
    switch(direction) {
      case Direction.NORTH:
        //
        break;
      case Direction.EAST:
        //
        break;
      case Direction.WEST:
        //
        break;
      case Direction.SOUTH:
        //
        break;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
    return 0;
  }


}
