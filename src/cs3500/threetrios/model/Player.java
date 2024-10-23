package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
  private final String color;  // "Red" or "Blue"
  private final List<Card> hand;

  public Player(String color, List<Card> initialHand) {
    this.color = color;
    this.hand = new ArrayList<>(initialHand);
  }

  public boolean playCard(Card card) {
    if (this.hand.contains(card)) {
      this.hand.remove(card);
      return true;
    }
    return false;
  }

  public void addCard(Card card) {
    this.hand.add(card);  // Add card after a flip
  }

  public boolean owns(Card card) {
    return this.hand.contains(card);
  }

  // Check player color
  public boolean isRed() {
    return "Red".equalsIgnoreCase(this.color);
  }

  public boolean isBlue() {
    return "Blue".equalsIgnoreCase(this.color);
  }
}
