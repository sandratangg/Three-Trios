package cs3500.threetrios.model;

import java.util.List;

/**
 * An abstract class representing a read-only model of the Three Trios game.
 * Provides methods to access the current game state without modifying it.
 */
public abstract class ReadOnlyThreeTriosModel {

  public ThreeTriosGrid grid;
  protected ThreeTriosPlayer redPlayer;
  protected ThreeTriosPlayer bluePlayer;
  protected ThreeTriosPlayer currentPlayer;

  /**
   * Returns the grid's width (number of columns).
   *
   * @return the width of the game grid
   */
  public int getGridWidth() {
    return grid.getColCount();
  }

  /**
   * Returns the grid's height (number of rows).
   *
   * @return the height of the game grid
   */
  public int getGridHeight() {
    return grid.getRowCount();
  }

  /**
   * Retrieves the contents of a specific cell in the grid.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the contents of the specified cell
   */
  public ThreeTriosCell getCellContents(int row, int col) {
    return grid.getCell(row, col);
  }

  /**
   * Returns the contents of a player's hand based on the player's color.
   *
   * @param playerColor the color of the player (RED or BLUE)
   * @return the list of cards in the player's hand
   */
  public List<ThreeTriosCard> getPlayerHand(PlayerColor playerColor) {
    if (playerColor.equals(PlayerColor.RED)) {
      return redPlayer.getHandCards();
    } else {
      return bluePlayer.getHandCards();
    }
  }

  /**
   * Determines the owner of the card in a cell at the given coordinate.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the color of the player who owns the card
   * @throws IllegalStateException if no card is found or no player owns the card
   */
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

  /**
   * Checks if it is legal for the current player to play at a given coordinate.
   *
   * @param row  the row index on the board
   * @param col  the column index on the board
   * @param card the card to be played
   * @return true if the move is legal, false otherwise
   */
  public boolean isLegalMove(int row, int col, ICard card) {
    return grid.isLegalMove(row, col, card);
  }

  /**
   * Calculates the number of cards that can be flipped by playing a card at a specific coordinate.
   *
   * @param row  the row index on the board
   * @param col  the column index on the board
   * @param card the card to be played
   * @return the number of flippable cards
   */
  public int calculateFlippableCards(int row, int col, ICard card) {
    return grid.calculateFlippableCards(row, col, card, currentPlayer);
  }

  /**
   * Gets the score (number of owned cards) of a specified player color.
   *
   * @param playerColor the color of the player (RED or BLUE)
   * @return the score of the specified player
   */
  public int getPlayerScore(PlayerColor playerColor) {
    if (playerColor == PlayerColor.RED) {
      return redPlayer.getOwnedCardsSize();
    } else {
      return bluePlayer.getOwnedCardsSize();
    }
  }

  /**
   * Determines if the game is over, such as when the grid is full.
   *
   * @return true if the game is over, false otherwise
   */
  public boolean isGameOver() {
    return grid.isGridFull();
  }

  /**
   * Returns the color of the current player.
   *
   * @return the color of the current player
   */
  public PlayerColor getCurrentPlayerColor() {
    if (currentPlayer.isRed()) {
      return PlayerColor.RED;
    } else {
      return PlayerColor.BLUE;
    }
  }
}
