package cs3500.threetrios.model;

import cs3500.threetrios.strategies.Move;
import java.util.List;

/**
 * A mock implementation of the ReadOnlyThreeTriosModel abstract class used for testing strategies.
 */
public class MockThreeTriosModel extends ReadOnlyThreeTriosModel {
  private final int[][] board;
  private final List<ThreeTriosCard> hand;
  private final PlayerColor playerColor;

  /**
   * Constructor to initialize the mock model with a predefined board, player's hand, and player color.
   */
  public MockThreeTriosModel(int[][] board, List<ThreeTriosCard> hand, PlayerColor playerColor) {
    this.board = board;
    this.hand = hand;
    this.playerColor = playerColor;

    // Initialize the grid and players
    this.grid = new ThreeTriosGrid(board.length, board[0].length);
    this.redPlayer = new ThreeTriosPlayer(PlayerColor.RED, hand);
    this.bluePlayer = new ThreeTriosPlayer(PlayerColor.BLUE, hand);
    this.currentPlayer = playerColor == PlayerColor.RED ? redPlayer : bluePlayer;
  }

  @Override
  public boolean isLegalMove(int row, int col, ICard card) {
    // A move is legal if the cell is empty (represented by 0)
    return board[row][col] == 0;
  }

  @Override
  public int calculateFlippableCards(int row, int col, ICard card) {
    // Mock flippable cards calculation: counts adjacent cells occupied by the opponent
    int flippable = 0;
    int playerCode = (playerColor == PlayerColor.RED) ? 1 : -1;

    // Check the four directions: North, East, South, West
    if (row > 0 && board[row - 1][col] != 0 && board[row - 1][col] != playerCode) flippable++;
    if (row < board.length - 1 && board[row + 1][col] != 0 && board[row + 1][col] != playerCode) flippable++;
    if (col > 0 && board[row][col - 1] != 0 && board[row][col - 1] != playerCode) flippable++;
    if (col < board[0].length - 1 && board[row][col + 1] != 0 && board[row][col + 1] != playerCode) flippable++;

    return flippable;
  }

  @Override
  public List<ThreeTriosCard> getPlayerHand(PlayerColor playerColor) {
    return this.hand;
  }

  @Override
  public PlayerColor getCurrentPlayerColor() {
    return playerColor;
  }

  @Override
  public ThreeTriosCell getCellContents(int row, int col) {
    // Return a dummy cell with the current card state for simplicity
    return grid.getCell(row, col);
  }

  @Override
  public int getPlayerScore(PlayerColor playerColor) {
    return playerColor == PlayerColor.RED ? redPlayer.getOwnedCardsSize() : bluePlayer.getOwnedCardsSize();
  }

  @Override
  public boolean isGameOver() {
    return grid.isGridFull();
  }
}
