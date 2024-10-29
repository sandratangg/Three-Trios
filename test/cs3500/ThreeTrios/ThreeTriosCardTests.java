package cs3500.ThreeTrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.ThreeTriosCard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the ThreeTriosCard class.
 */
public class ThreeTriosCardTests {

  private ThreeTriosCard card1;
  private ThreeTriosCard card2;
  private ThreeTriosCard cardWith10;

  /**
   * Initializes common objects before each test.
   */
  @Before
  public void init() {
    card1 = new ThreeTriosCard("Dragon", 5, 7, 6, 3);
    card2 = new ThreeTriosCard("Phoenix", 1, 10, 9, 8);
    cardWith10 = new ThreeTriosCard("Lion", 10, 10, 10, 10);
  }

  // Test constructor with valid input.
  // Makes sure the card is successfully created and not null.
  @Test
  public void testConstructorValidValues() {
    assertNotNull(card1);
    assertNotNull(card2);
    assertNotNull(cardWith10);
  }


  // Test constructor with attack values below/above valid range.
  // Expect IllegalArgumentException.
  @Test
  public void testConstructorAttackValuesBelowRange() {
    //Checks with 0 value
    try {
      new ThreeTriosCard("Dragon", 0, 7, 6, 3);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Value must be between 1 and 10"));
    }

    //Checks with negative value
    try {
      new ThreeTriosCard("Dragon", -5, -1, -6, 3);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Value must be between 1 and 10"));
    }

    //Checks with value greater than 10
    try {
      new ThreeTriosCard("Dragon", 14, 7, 12, 3);
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Value must be between 1 and 10"));
    }
  }

  // Test constructor with boundary values (1 and 10).
// Makes sure the card is successfully created and not null.
  @Test
  public void testConstructorBoundaryValues() {
    ThreeTriosCard card1 = new ThreeTriosCard("Dragon", 1, 1, 1, 1);
    ThreeTriosCard card2 = new ThreeTriosCard("Phoenix", 10, 10, 10, 10);
    assertNotNull(card1);
    assertNotNull(card2);
  }


  // Test attack method with valid directions.
// Makes sure correct attack values are returned for each direction (NORTH, EAST, WEST, SOUTH).
  @Test
  public void testAttackValidDirections() {
    //Checks with NORTH direction
    assertTrue(card1.attack(Direction.NORTH) == 5);
    assertTrue(card2.attack(Direction.NORTH) == 1);
    assertTrue(cardWith10.attack(Direction.NORTH) == 10);

    //Checks with EAST direction
    assertTrue(card1.attack(Direction.EAST) == 7);
    assertTrue(card2.attack(Direction.EAST) == 10);
    assertTrue(cardWith10.attack(Direction.EAST) == 10);

    //Checks with WEST direction
    assertTrue(card1.attack(Direction.WEST) == 6);
    assertTrue(card2.attack(Direction.WEST) == 9);
    assertTrue(cardWith10.attack(Direction.WEST) == 10);

    //Checks with SOUTH direction
    assertTrue(card1.attack(Direction.SOUTH) == 3);
    assertTrue(card2.attack(Direction.SOUTH) == 8);
    assertTrue(cardWith10.attack(Direction.SOUTH) == 10);
  }

  // Test attack method with invalid direction (null): Expect NullPointerException.
  @Test(expected = NullPointerException.class)
  public void testAttackInvalidDirection() {
    card1.attack(null);
  }


  // Test attack method with invalid direction (default case): Expect IllegalArgumentException.
  // NOTE: THIS CANNOT BE TESTED AS THE DEFAULT CASE IN THE SWITCH STATEMENT IS NOT REACHABLE
  // UNLESS THE ENUM IS EDITED.


  // Test matchesName with valid matching name: Ensure true is returned for a matching name.
  @Test
  public void testMatchesNameValidMatchingName() {
    assertTrue(card1.matchesName("Dragon"));
    assertTrue(card2.matchesName("Phoenix"));
    assertTrue(cardWith10.matchesName("Lion"));
  }

  // Test matchesName with a non-matching name: Ensure false is returned for a non-matching name.
  @Test
  public void testMatchesNameNonMatchingName() {
    assertFalse(card1.matchesName("Phoenix"));
    assertFalse(card2.matchesName("Dragon"));
    assertFalse(cardWith10.matchesName("Dragon"));
  }

  // Test matchesName with null: Ensure false is returned when null is passed.
  @Test
  public void testMatchesNameNull() {
    assertFalse(card1.matchesName(null));
    assertFalse(card2.matchesName(null));
    assertFalse(cardWith10.matchesName(null));
  }

  // Test matchesName with an empty string: Ensure false is returned for an empty string.
  @Test
  public void testMatchesNameEmptyString() {
    assertFalse(card1.matchesName(""));
    assertFalse(card2.matchesName(""));
    assertFalse(cardWith10.matchesName(""));
  }

  // Test toString with valid attack values: Ensure correct string representation of the card is returned.
  @Test
  public void testToStringValidAttackValues() {
    //Checks with valid attack values
    assertTrue(card1.toString().equals("Dragon 5 7 6 3"));
    assertTrue(card2.toString().equals("Phoenix 1 A 9 8"));
    assertTrue(cardWith10.toString().equals("Lion A A A A"));
  }

  // Test toString when attack value is 10: Ensure 'A' is used to represent the attack value of 10.
  @Test
  public void testToStringAttackValue10() {
    //Checks with attack value 10
    assertTrue(card2.toString().equals("Phoenix 1 A 9 8"));
    assertTrue(cardWith10.toString().equals("Lion A A A A"));
  }

}
