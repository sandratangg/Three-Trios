package cs3500.threetrios.model;

import java.util.List;

public abstract class ReadOnlyThreeTriosModel {
  protected ThreeTriosGrid grid;
  protected ThreeTriosPlayer redPlayer;
  protected ThreeTriosPlayer bluePlayer;
  protected ThreeTriosPlayer currentPlayer;

  // Returns the grid's width (number of columns)
  public int getGridWidth() {
    return grid.getColCount();
  }

  // Returns the grid's height (number of rows)
  public int getGridHeight() {
    return grid.getRowCount();
  }

  // Returns the contents of a specific cell in the grid
  public ThreeTriosCell getCellContents(int row, int col) {
    return grid.getCell(row, col);
  }

  // Returns the contents of a player's hand based on the player's color
  public List<ThreeTriosCard> getPlayerHand(PlayerColor playerColor) {
    if (playerColor.equals(PlayerColor.RED)) {
      return redPlayer.getHandCards();
    } else {
      return bluePlayer.getHandCards();
    }
  }

  // Determines the owner of the card in a cell at the given coordinate
  public PlayerColor getCardOwner(int row, int col) {
    ICard card = grid.getCardFromCell(row, col);
    System.out.println("Checking card ownership at position (" + row + ", " + col + ")");
    System.out.println("Card: " + card);

    if (card == null) {
      throw new IllegalStateException("No card found at this position.");
    }

    if (redPlayer.owns(card)) {
      System.out.println("Card owned by RED player");
      return PlayerColor.RED;
    } else if (bluePlayer.owns(card)) {
      System.out.println("Card owned by BLUE player");
      return PlayerColor.BLUE;
    } else {
      System.out.println("No player owns the card at position (" + row + ", " + col + ")");
      throw new IllegalStateException("No player owns the card at this position.");
    }
  }


  // Checks if it is legal for the current player to play at a given coordinate
  public boolean isLegalMove(int row, int col, ICard card) {
    return grid.isLegalMove(row, col, card);
  }

  // Calculates the number of cards that can be flipped by playing a card at a specific coordinate
  public int calculateFlippableCards(int row, int col, ICard card) {
    return grid.calculateFlippableCards(row, col, card, currentPlayer);
  }

  // Gets the score (number of owned cards) of a specified player color
  public int getPlayerScore(PlayerColor playerColor) {
    return playerColor == PlayerColor.RED ? redPlayer.getOwnedCardsSize() : bluePlayer.getOwnedCardsSize();
  }

  // Determines if the game is over (e.g., if the grid is full)
  public boolean isGameOver() {
    return grid.isGridFull();
  }

  // Returns the color of the current player
  public PlayerColor getCurrentPlayerColor() {
    return currentPlayer.isRed() ? PlayerColor.RED : PlayerColor.BLUE;
  }
}
