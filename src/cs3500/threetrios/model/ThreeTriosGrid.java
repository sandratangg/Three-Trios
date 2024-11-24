package cs3500.threetrios.model;

/**
 * Represents a grid in the game.
 */
public class ThreeTriosGrid implements IGrid {
  private final int rows;
  private final int cols;
  private final ThreeTriosCell[][] grid;


  /**
   * Constructs a ThreeTriosGrid with the given dimensions.
   *
   * @param rows the number of rows
   * @param cols the number of columns
   */
  public ThreeTriosGrid(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Grid dimensions must be positive.");
    }
    this.rows = rows;
    this.cols = cols;
    this.grid = new ThreeTriosCell[this.rows][this.cols];
    initializeGrid();
  }

  /**
   * Helper: Initializes the grid with empty cells.
   */
  private void initializeGrid() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new ThreeTriosCell();
      }
    }
  }

  /**
   * Method to return a new copy of this grid.
   *
   * @return Copy of this grid
   */
  public ThreeTriosGrid copy() {
    ThreeTriosGrid newGrid = new ThreeTriosGrid(this.rows, this.cols);

    for (int row = 0; row < this.rows; row++) {
      for (int col = 0; col < this.cols; col++) {
        ThreeTriosCell originalCell = this.grid[row][col];
        ICard cardCopy = originalCell.getCard() != null ? originalCell.getCard() : null;

        // Create a new cell with the hole status and the copied card (or null if no card)
        newGrid.grid[row][col] = new ThreeTriosCell(originalCell.isHole, cardCopy);
      }
    }

    return newGrid;
  }

  /**
   * Helper: Places card only if valid.
   *
   * @param row  the row to place the card
   * @param col  the column to place the card
   * @param card the card to place
   * @throws IllegalArgumentException if the position is invalid
   */
  public void placeCard(int row, int col, ICard card) {
    if (isLegalMove(row, col, card)) {
      grid[row][col].setCard(card);
    } else {
      throw new IllegalArgumentException("Illegal card placement");
    }
  }

  /**
   * Determines if placing a card at a location is legal.
   *
   * @param row  the row to place the card
   * @param col  the column to place the card
   * @param card the card to place
   * @return Returns if card can be placed.
   */
  public boolean isLegalMove(int row, int col, ICard card) {
    if (row < 0 || row >= rows || col < 0 || col >= cols) {
      return false;
    } else {
      return grid[row][col].isEmpty() & !grid[row][col].isHole;
    }

  }

  /**
   * Helper: Battle logic now encapsulated in the grid class itself.
   */
  public void battlePhase(int row, int col, ICard placedCard, Direction direction,
                          ThreeTriosPlayer currentPlayer, ThreeTriosPlayer oppositePlayer) {
    int adjacentRow = getAdjacentRow(row, direction);
    int adjacentCol = getAdjacentCol(col, direction);

    if (isValidPosition(adjacentRow, adjacentCol)) {
      ThreeTriosCell adjacentCell = grid[adjacentRow][adjacentCol];

      if (!adjacentCell.isEmpty() && !adjacentCell.isHole) {  // Check if the cell has a card
        ICard adjacentCard = adjacentCell.card;

        // Only battle if the adjacent card belongs to the opponent
        if (!currentPlayer.owns(adjacentCard)) {
          // Compare attack values in the direction
          if (placedCard.attack(direction) > adjacentCard.attack(getOppositeDirection(direction))) {
            // Flip the card: opponent loses ownership, current player gains it
            currentPlayer.addToOwned(adjacentCard);  // Update grid ownership
            oppositePlayer.removeFromOwned(adjacentCard);  // Reflect the card change in the grid

            // Combo step: the flipped card now does battle with its adjacent cards
            for (Direction adjDirection : Direction.values()) {
              battlePhase(adjacentRow, adjacentCol, adjacentCard, adjDirection,
                      currentPlayer, oppositePlayer);
            }
          }
        }
      }
    }
  }


  private Direction getOppositeDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return Direction.SOUTH;
      case EAST:
        return Direction.WEST;
      case SOUTH:
        return Direction.NORTH;
      case WEST:
        return Direction.EAST;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }

  /**
   * Calculates the number of cards that can be flipped by playing a card at a specific coordinate.
   *
   * @param x the x-coordinate of the card
   * @param y the y-coordinate of the card
   * @param card the card to be played
   * @param player the player who is playing the card
   * @return the number of flippable cards
   */
  public int calculateFlippableCards(int x, int y, ICard card, IPlayer player) {
    boolean[][] visited = new boolean[this.rows][this.cols];
    return calculateFlipsFromPosition(x, y, card, player, visited);
  }

  // Recursive helper method to calculate flips with chain reactions
  private int calculateFlipsFromPosition(int x, int y, ICard card, IPlayer player,
                                         boolean[][] visited) {
    int chainFlips = 0;

    for (Direction direction : Direction.values()) {
      int adjacentRow = getAdjacentRow(x, direction);
      int adjacentCol = getAdjacentCol(y, direction);

      if (isValidPosition(adjacentRow, adjacentCol) && !visited[adjacentRow][adjacentCol]) {
        ThreeTriosCell adjacentCell = getCell(adjacentRow, adjacentCol);

        // Check if there's an opponent card in the adjacent cell
        if (!adjacentCell.isEmpty() && !player.owns(adjacentCell.card)) {
          // Flip condition: check attack values
          if (card.attack(direction) > adjacentCell.card.attack(getOppositeDirection(direction))) {
            // Mark this cell as visited to prevent re-processing
            visited[adjacentRow][adjacentCol] = true;

            // Increment flip count for this card
            chainFlips++;

            // Recursively calculate further flips from this new position
            chainFlips += calculateFlipsFromPosition(adjacentRow, adjacentCol, adjacentCell.card,
                    player, visited);
          }
        }
      }
    }

    return chainFlips;
  }

  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  private int getAdjacentRow(int row, Direction direction) {
    switch (direction) {
      case NORTH:
        return row - 1;
      case SOUTH:
        return row + 1;
      default:
        return row;
    }
  }

  private int getAdjacentCol(int col, Direction direction) {
    switch (direction) {
      case EAST:
        return col + 1;
      case WEST:
        return col - 1;
      default:
        return col;
    }
  }

  /**
   * Returns a string representation of the grid.
   *
   * @param red the red player
   * @return the string representation of the grid
   */
  public String toString(ThreeTriosPlayer red) {
    StringBuilder gridString = new StringBuilder();

    for (int row = 0; row < rows; row++) {
      gridString.append(rowToString(row, cols, red));
      if (row < rows - 1) {  // Add newline between rows, but not after the last row
        gridString.append("\n");
      }
    }

    return gridString.toString();
  }

  /**
   * Helper: Converts a row to a string.
   */
  private String rowToString(int row, int cols, ThreeTriosPlayer red) {
    StringBuilder rowString = new StringBuilder();

    for (int col = 0; col < cols; col++) {
      ThreeTriosCell currentCell = grid[row][col];
      if (currentCell.isHole) {
        rowString.append(" ");
      } else if (currentCell.isEmpty()) {
        rowString.append("_");
      } else {
        if (red.owns(currentCell.card)) {
          rowString.append("R");
        } else {
          rowString.append("B");
        }
      }
    }

    return rowString.toString();
  }

  /**
   * Checks if the grid is fully occupied with no empty cells remaining.
   * A full grid signifies that no further moves can be made, potentially
   * indicating an end state for the game. This method allows for efficient
   * assessment of grid capacity, impacting gameplay decisions and flow.
   *
   * @return true if the grid has no empty cells; false if at least one cell is empty.
   */
  public boolean isGridFull() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (grid[row][col].isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Places an empty card cell at the given row and column.
   *
   * @param row the row
   * @param col the column
   */
  public void placeEmptyCardCell(int row, int col) {
    grid[row][col] = new ThreeTriosCell(false);  // 'false' means it’s not a hole
  }

  /**
   * Places a hole at the given row and column.
   * @param row the row
   * @param col the column
   */
  public void placeHole(int row, int col) {
    grid[row][col] = new ThreeTriosCell(true);  // 'true' means it’s a hole
  }

  // For testing purposes
  /**
   * Returns the card from the cell at the given position (i, j).
   * @param i the row
   * @param j the column
   * @return the card from the cell at the given position
   */
  public ICard getCardFromCell(int i, int j) {
    return grid[i][j].getCard();
  }


  /**
   * Returns the cell at the given row and column.
   * @param row the row of the cell
   * @param col the column of cell
   * @return the cell at the given row and column
   */
  public ThreeTriosCell getCell(int row, int col) {
    return new ThreeTriosCell(this.grid[row][col].isHole,
            this.grid[row][col].getCard());
  }

  /**
   * Counts the total number of card cells in the grid.
   * Card cells are defined as playable cells that are not marked as holes.
   *
   * @return the number of card cells in the grid
   */
  public int getNumCardCells() {
    int numCardCells = 0;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (!grid[row][col].isHole) {
          numCardCells++;
        }
      }
    }

    return numCardCells;
  }

  /**
   * Counts the total number of hole cells in the grid.
   * Hole cells are defined as non-playable cells.
   *
   * @return the number of hole cells in the grid
   */
  public int getNumHoleCells() {
    int numHoleCells = 0;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (grid[row][col].isHole) {
          numHoleCells++;
        }
      }
    }

    return numHoleCells;
  }

  /**
   * Gets the total number of rows in the grid.
   *
   * @return the row count of the grid
   */
  public int getRowCount() {
    return this.rows;
  }


  /**
   * Gets the total number of columns in the grid.
   *
   * @return the column count of the grid
   */
  public int getColCount() {
    return this.cols;
  }


}
