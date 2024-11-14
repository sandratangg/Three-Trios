package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosCell;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the ThreeTriosCell class.
 */
public class ThreeTriosCellTests {
  private ThreeTriosCell noCardCell;
  private ThreeTriosCell holeCell;
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
    cardCellWithCard = new ThreeTriosCell();
    sampleCard = new ThreeTriosCard("Dragon", 5, 7, 6,
            3);

  }

  // Test default constructor: Makes sure the cell is created as a Card Cell and without a card
  // It checks if the cell card is null and if the cell is empty
  // Test isEmpty method on a default cell (non-hole and no card)
  @Test
  public void testDefaultConstructorNoCard() {
    assertNull(noCardCell.card);
    assertTrue(noCardCell.isEmpty());
  }

  // Test hole constructor with isHole is true
  // Makes sure the cell is created as a hole and does not contain a card.
  @Test
  public void testHoleConstructorNoCard() {
    //holeCell is a card cell with no card
    assertNull(holeCell.card);
    assertFalse(holeCell.isEmpty()); //Returns true when the cell is not empty
  }

  // Test hole constructor with isHole set to false
  // Makes sure the cell is made as a non-hole and does not contain a card.
  @Test
  public void testNonHoleConstructorNoCard() {
    ThreeTriosCell cell = new ThreeTriosCell(false);
    assertNull(cell.card);
    assertTrue(cell.isEmpty());
  }

  // Test constructor with a card cell containing a card
  // Make cell is created as a Card Cell and contains the given card in the cell
  @Test
  public void testConstructorWithCard() {
    //Places sample card into card call
    cardCellWithCard.setCard(sampleCard);

    //Checks if sampleCard is the same as the card in the cell
    assertTrue(cardCellWithCard.sameCard(sampleCard));
  }

  // Test isEmpty method on a non-hole cell with a card assigned
  // Makes it returns false, as the cell contains a card.
  @Test
  public void testNonHoleCellWithCard() {
    cardCellWithCard.setCard(sampleCard);
    assertFalse(cardCellWithCard.isEmpty());
  }

}
