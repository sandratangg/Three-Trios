package cs3500.threetrios.model;

/**
 * Represents a cell within the Three Trios game grid. A cell can either
 * be empty or occupied by a card, determining its role in the gameplay.
 * This interface provides a method to check the occupancy status of the cell.
 * Implementations of this interface may also contain additional methods to
 * manage and interact with the contents of a cell.
 */
public interface ICell {

  /**
   * Checks if the cell is currently empty. An empty cell is one that
   * does not contain any card, making it available for placing a new card.
   * This method allows the game to determine potential moves and valid placements.
   *
   * @return true if the cell is empty and available; false if it contains a card.
   */
  boolean isEmpty();


  boolean isHole();
}
