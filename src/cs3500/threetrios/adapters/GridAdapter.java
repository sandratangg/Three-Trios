package cs3500.threetrios.adapters;

import cs3500.threetrios.model.*;
import cs3500.threetrios.provider.model.*;

/**
 * Adapter class to map ThreeTriosGrid to the ProviderIGrid interface.
 */
public class GridAdapter implements ProviderIGrid {

  private final ThreeTriosGrid grid;

  /**
   * Constructs a GridAdapter from a ThreeTriosGrid.
   *
   * @param grid the grid to adapt
   */
  public GridAdapter(ThreeTriosGrid grid) {
    this.grid = grid;
  }

  @Override
  public void playCardToPosition(int row, int col, IThreeTriosCard playedCard, PlayerColor color) {
    if (grid.isLegalMove(row, col, (ICard) playedCard)) {
      grid.placeCard(row, col, (ICard) playedCard);
    } else {
      throw new IllegalArgumentException("Invalid card placement.");
    }
  }

  @Override
  public int getCardCellCount() {
    return grid.getNumCardCells();
  }

  @Override
  public int getWidth() {
    return grid.getColCount();
  }

  @Override
  public int getHeight() {
    return grid.getRowCount();
  }

  @Override
  public ProviderICell getCell(int row, int col) {
    ThreeTriosCell cell = grid.getCell(row, col);
    return new CellAdapter(cell);
  }

  @Override
  public boolean isOutOfBounds(int row, int col) {
    return row < 0 || row >= grid.getRowCount() || col < 0 || col >= grid.getColCount();
  }

  @Override
  public boolean allCellsAreFilled() {
    return grid.isGridFull();
  }

  @Override
  public void flipCardInCell(int row, int col) {
    ThreeTriosCell cell = grid.getCell(row, col);
    if (cell.isEmpty() || cell.isHole()) {
      throw new IllegalStateException("Cannot flip card in an empty or hole cell.");
    }

    ICard card = cell.getCard();
    // Flip logic would be implemented here if applicable
    throw new UnsupportedOperationException("Flip card functionality is not implemented.");
  }

  @Override
  public int getPlayerOwnedCardsCount(PlayerColor color) {
    // Assuming you have a method to count owned cards by color
    // Adapt this if ownership logic is implemented differently
    return 0; // Placeholder
  }
}
