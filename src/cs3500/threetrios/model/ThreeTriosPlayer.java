package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

public class ThreeTriosPlayer {
  private final PlayerColor color;  // "Red" or "Blue"
  private final List<ThreeTriosCard> hand;

  public ThreeTriosPlayer(PlayerColor color, List<ThreeTriosCard> initialHand) {
    this.color = color;
    this.hand = new ArrayList<>(initialHand);
  }

  public boolean playCard(ThreeTriosCard card) {
    if (this.hand.contains(card)) {
      this.hand.remove(card);
      return true;
    }
    return false;
  }

  public void addCard(ThreeTriosCard card) {
    this.hand.add(card);  // Add card after a flip
  }

  public boolean owns(ThreeTriosCard card) {
    return this.hand.contains(card);
  }

  // Check player color
  public boolean isRed() {
    return PlayerColor.RED.equals(this.color);
  }

  public boolean isBlue() {
    return PlayerColor.BLUE.equals(this.color);
  }
}
