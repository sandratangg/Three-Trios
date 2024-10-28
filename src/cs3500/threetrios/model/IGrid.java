package cs3500.threetrios.model;

public interface IGrid {
  boolean placeCard(int row, int col, ICard card);

  boolean isGridFull();
}

