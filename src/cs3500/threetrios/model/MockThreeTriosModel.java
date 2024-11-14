package cs3500.threetrios.model;

import java.util.List;

/**
 * A mock implementation of the {@link ReadOnlyThreeTriosModel} abstract class used for testing
 * strategies. This mock model allows testing without needing a full implementation of the game.
 */
public class MockThreeTriosModel extends ReadOnlyThreeTriosModel {
  private final int[][] board;
  private final List<ThreeTriosCard> hand;
  private final PlayerColor playerColor;

  /**
   * Constructs a mock model with a predefined board, player's hand, and the current player color.
   *
   * @param board        the 2D array representing the board state
   * @param hand         the list of cards available to the player
   * @param playerColor  the color of the current player (RED or BLUE)
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

  /**
   * Checks if placing the given card at the specified position is a legal move.
   * A move is legal if the cell is empty (represented by 0).
   *
   * @param row   the row index on the board
   * @param col   the column index on the board
   * @param card  the card to place
   * @return true if the move is legal, false otherwise
   */
  @Override
  public boolean isLegalMove(int row, int col, ICard card) {
    return board[row][col] == 0;
  }

  /**
   * Calculates the number of cards that can be flipped if the specified card is placed
   * at the given position.
   *
   * @param row   the row index on the board
   * @param col   the column index on the board
   * @param card  the card to place
   * @return the number of cards that can be flipped
   */
  @Override
  public int calculateFlippableCards(int row, int col, ICard card) {
    int flippable = 0;
    int playerCode = (playerColor == PlayerColor.RED) ? 1 : -1;

    // Check the four directions: North, East, South, West
    if (row > 0 && board[row - 1][col] != 0 && board[row - 1][col] != playerCode) {
      flippable++;
    }

    if (row < board.length - 1 && board[row + 1][col] != 0 && board[row + 1][col] != playerCode) {
      flippable++;
    }

    if (col > 0 && board[row][col - 1] != 0 && board[row][col - 1] != playerCode) {
      flippable++;
    }

    if (col < board[0].length - 1 &&
            board[row][col + 1] != 0 && board[row][col + 1] != playerCode) {
      flippable++;
    }

    return flippable;
  }

  /**
   * Retrieves the player's hand of cards.
   *
   * @param playerColor the color of the player (RED or BLUE)
   * @return the list of cards in the player's hand
   */
  @Override
  public List<ThreeTriosCard> getPlayerHand(PlayerColor playerColor) {
    return this.hand;
  }

  /**
   * Gets the color of the current player.
   *
   * @return the color of the current player
   */
  @Override
  public PlayerColor getCurrentPlayerColor() {
    return playerColor;
  }

  /**
   * Retrieves the contents of a cell on the board.
   *
   * @param row the row index of the cell
   * @param col the column index of the cell
   * @return the contents of the specified cell
   */
  @Override
  public ThreeTriosCell getCellContents(int row, int col) {
    return grid.getCell(row, col);
  }
}
