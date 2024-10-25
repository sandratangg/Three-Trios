package cs3500.ThreeTrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosCell;

public class ThreeTriosCellTests {
  private ThreeTriosCell noCardCell;
  private ThreeTriosCell holeCell;
  private ThreeTriosCell exampleCardCell1;
  private ThreeTriosCell cardCellWithCard;
  private ThreeTriosCard sampleCard;

  /**
   * Initializes common objects before each test.
   */
  @Before
  public void init() {
    // Default cell card with no card)
    noCardCell = new ThreeTriosCell();

    // Hole cell with no card
    holeCell = new ThreeTriosCell(true);

    // Card cell with a card
    exampleCardCell1 = new ThreeTriosCell();
    exampleCardCell1.setCard();
    // In the ThreeTriosCell class, add a sameCard method that returns something like "this.card.equals(givenCard)"
    exampleCardCell1.sameCard(givenCard);

  }

  //make a s card equals helper to return this.card == card
  // Test default constructor: Ensure the cell is created as a non-hole and without a card (isHole = false, card = null).


// Test hole constructor with isHole set to true: Ensure the cell is created as a hole and does not contain a card.


// Test hole constructor with isHole set to false: Ensure the cell is created as a non-hole and does not contain a card.


// Test isEmpty method on a default cell (non-hole and no card): Ensure it returns true, indicating the cell is empty.


// Test isEmpty method on a hole cell: Ensure it returns false, as holes cannot be considered "empty" even without a card.


// Test isEmpty method on a non-hole cell with a card assigned: Ensure it returns false, as the cell contains a card.

}
