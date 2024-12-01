package cs3500.threetrios.adapters;

import cs3500.threetrios.model.ThreeTriosCell;
import cs3500.threetrios.provider.model.ProviderICell;
import cs3500.threetrios.provider.model.IThreeTriosCard;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ICard;
import cs3500.threetrios.provider.model.CellType;

/**
 * Adapter class to map ThreeTriosCell to ProviderICell.
 */
public class CellAdapter implements ProviderICell {

  private final ThreeTriosCell cell;

  /**
   * Constructs a CellAdapter from a ThreeTriosCell.
   *
   * @param cell the cell to adapt
   */
  public CellAdapter(ThreeTriosCell cell) {
    this.cell = cell;
  }

  @Override
  public void placeCard(IThreeTriosCard card, PlayerColor owner) {
    if (cell.isHole()) {
      throw new UnsupportedOperationException("Cannot place a card in a hole.");
    }
    if (card == null || owner == null) {
      throw new NullPointerException("Card or owner cannot be null.");
    }
    cell.setCard((ICard) card);
  }

  @Override
  public IThreeTriosCard getCard() {
    if (cell.isHole()) {
      throw new UnsupportedOperationException("Cannot retrieve a card from a hole.");
    }
    if (cell.getCard() == null) {
      throw new IllegalStateException("Cell does not contain a card.");
    }
    return (IThreeTriosCard) cell.getCard();
  }

  @Override
  public PlayerColor getCardOwner() {
    if (cell.isHole()) {
      throw new UnsupportedOperationException("Holes do not have card owners.");
    }
    if (cell.getCard() == null) {
      throw new IllegalStateException("Cell does not contain a card.");
    }
    // Placeholder logic for ownership. Replace with actual ownership check if available.
    return PlayerColor.RED; // Example owner
  }

  @Override
  public boolean isOccupiedCardCell() {
    return !cell.isHole() && cell.getCard() != null;
  }

  @Override
  public boolean isUnoccupiedCardCell() {
    return !cell.isHole() && cell.getCard() == null;
  }

  @Override
  public void flipCardInCell() {
    if (cell.isHole()) {
      throw new UnsupportedOperationException("Cannot flip a card in a hole.");
    }
    if (cell.getCard() == null) {
      throw new IllegalStateException("No card to flip in the cell.");
    }
    // Placeholder logic for flipping cards. Implement based on actual ownership flipping.
    throw new UnsupportedOperationException("Flip card functionality is not implemented.");
  }

  @Override
  public CellType getType() {
    return cell.isHole() ? CellType.HOLE : CellType.CARD_CELL;
  }
}
