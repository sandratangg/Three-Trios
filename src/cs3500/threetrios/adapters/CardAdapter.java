package cs3500.threetrios.adapters;

import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.provider.model.AttackDirection;
import cs3500.threetrios.provider.model.IThreeTriosCard;

/**
 * Adapter class to map ThreeTriosCard to IThreeTriosCard.
 */
public class CardAdapter implements IThreeTriosCard {

  private final ThreeTriosCard card;

  /**
   * Constructs a CardAdapter from a ThreeTriosCard.
   *
   * @param card the card to adapt
   */
  public CardAdapter(ThreeTriosCard card) {
    if (card == null) {
      throw new NullPointerException("Card cannot be null.");
    }
    this.card = card;
  }

  @Override
  public int getAttackValue(AttackDirection direction) {
    if (direction == null) {
      throw new NullPointerException("Direction cannot be null.");
    }

    // Map provider's AttackDirection to your Direction enum
    Direction adaptedDirection = mapAttackDirection(direction);
    return card.attack(adaptedDirection);
  }

  @Override
  public String getName() {
    return card.getName();
  }

  /**
   * Maps the AttackDirection enum from the provider to the Direction enum in your implementation.
   *
   * @param direction the AttackDirection to map
   * @return the corresponding Direction
   */
  private Direction mapAttackDirection(AttackDirection direction) {
    switch (direction) {
      case NORTH:
        return Direction.NORTH;
      case EAST:
        return Direction.EAST;
      case SOUTH:
        return Direction.SOUTH;
      case WEST:
        return Direction.WEST;
      default:
        throw new IllegalArgumentException("Invalid AttackDirection: " + direction);
    }
  }
}
