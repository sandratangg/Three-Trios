package cs3500.threetrios.provider.controller;


import cs3500.threetrios.model.PlayerColor;

/**
 * Represents player-related features that the controller for a Three Trios game must support.
 */
public interface IThreeTriosPlayerFeatures {
  /**
   * Handles when a cell on the grid is selected. If the player does not have a card currently
   * selected, the grid should not update.
   *
   * @param row the zero-based index of row on the grid to be played to. Rows are numbered from top
   *            to bottom, starting with 0 at the top.
   * @param col the zero-based index of column on the grid to be played to. Columns are numbered
   *            from left to right, starting with 0 at the left.
   */
  void selectGridCell(int row, int col);

  /**
   * Handles when a card in a player's hand is selected. A player should be able to select a card by
   * clicking on it, and deselect a card by clicking on it again. If a player already has a card
   * selected but selects another card, the initially selected card should be deselected. Players
   * should not be able to select a card from the opposing player's hand when it is their turn.
   *
   * @param handOwner the player whose owns the hand from which the card was selected
   * @param handIdx   the index of the card in the player's hand
   * @throws NullPointerException if handOwner or handIdx is null
   */
  void selectCardFromHand(PlayerColor handOwner, Integer handIdx);
}
