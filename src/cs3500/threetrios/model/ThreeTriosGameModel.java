package cs3500.threetrios.model;

import java.util.List;

/**
 * Represents the Three Trios game model, containing the game grid, players,
 * and core game logic. This class handles actions such as card placement,
 * turn-switching, battle phases, and determining the game's outcome.
 * The model maintains the current and opposite player to manage game turns.
 */
public class ThreeTriosGameModel extends ReadOnlyThreeTriosModel implements ThreeTrios {

  private ThreeTriosGrid grid;
  private ThreeTriosPlayer redPlayer;
  private ThreeTriosPlayer bluePlayer;
  private ThreeTriosPlayer currentPlayer;
  private ThreeTriosPlayer oppositePlayer;

  /**
   * Constructs a ThreeTrios game model with specified grid dimensions
   * and an initial deck of cards. The deck is divided between two players,
   * who take turns placing cards on the grid.
   *
   * @param rows the number of rows in the game grid.
   * @param cols the number of columns in the game grid.
   * @param deck the list of cards representing the deck, split between players.
   */
  public ThreeTriosGameModel(int rows, int cols, List<ThreeTriosCard> deck) {
    this(new ThreeTriosGrid(rows, cols), deck);
  }

  /**
   * Constructs a ThreeTrios game model with a specified grid and deck of cards.
   *
   * @param grid the game grid to use for the model.
   * @param deck the deck of cards to use for the model.
   */
  public ThreeTriosGameModel(ThreeTriosGrid grid, List<ThreeTriosCard> deck) {
    this.grid = grid;
    int cellsInGrid = grid.getNumCardCells();

    if (deck.size() <= cellsInGrid) {
      throw new IllegalStateException("Must have at least n + 1 cards to split.");
    }

    int cardsPerPlayer = deck.size() / 2;

    this.redPlayer = new ThreeTriosPlayer(PlayerColor.RED, deck.subList(0, cardsPerPlayer));
    this.bluePlayer = new ThreeTriosPlayer(PlayerColor.BLUE,
            deck.subList(cardsPerPlayer, deck.size()));
    this.currentPlayer = redPlayer;
    this.oppositePlayer = bluePlayer;
  }

  /**
   * Attempts to place a card at a specified location on the game grid.
   *
   * @param row  the row index at which to place the card.
   * @param col  the column index at which to place the card.
   * @param card the ThreeTriosCard to be placed on the grid.
   * @throws IllegalArgumentException if the card cannot be placed at the specified location.
   */
  public void placeCard(int row, int col, ICard card) {
    if (!currentPlayer.playCard(card)) {
      throw new IllegalArgumentException("Player does not have this card.");
    }

    try {
      grid.placeCard(row, col, card);
      currentPlayer.addToOwned(card);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid position: (" + row + ", " + col + ")");
    }

    performBattlePhase(row, col, card, currentPlayer, oppositePlayer);
    switchTurn();
  }

  /**
   * Performs the battle phase after a card is placed.
   *
   * @param row the row index of the placed card.
   * @param col the column index of the placed card.
   * @param placedCard the card that was placed.
   */
  private void performBattlePhase(int row, int col, ICard placedCard,
                                  ThreeTriosPlayer currentPlayer,
                                  ThreeTriosPlayer oppositePlayer) {
    for (Direction direction : Direction.values()) {
      grid.battlePhase(row, col, placedCard, direction, currentPlayer, oppositePlayer);
    }
  }

  /**
   * Switches the current player after a turn.
   */
  private void switchTurn() {
    if (isGameOver()) {
      System.out.println(determineWinner());
    } else {
      if (currentPlayer == redPlayer) {
        currentPlayer = bluePlayer;
        oppositePlayer = redPlayer;
      } else {
        currentPlayer = redPlayer;
        oppositePlayer = bluePlayer;
      }
    }
  }

  /**
   * Checks if the game is over by determining if the grid is full.
   *
   * @return true if the grid is fully occupied; false otherwise.
   */
  public boolean isGameOver() {
    return grid.isGridFull();
  }

  /**
   * Determines the winner of the game based on the number of cards owned by each player.
   *
   * @return a string announcing the winner or indicating a tie.
   */
  public String determineWinner() {
    int redPlayerCards = countOwnedCards(redPlayer);
    int bluePlayerCards = countOwnedCards(bluePlayer);

    if (redPlayerCards > bluePlayerCards) {
      return "Red player wins with " + redPlayerCards + " cards!";
    } else if (bluePlayerCards > redPlayerCards) {
      return "Blue player wins with " + bluePlayerCards + " cards!";
    } else {
      return "It's a tie!";
    }
  }

  /**
   * Counts the total number of cards owned by a player.
   *
   * @param player the player for whom to count the owned cards.
   * @return the number of cards owned by the player.
   */
  private int countOwnedCards(ThreeTriosPlayer player) {
    return player.getOwnedCardsSize();
  }

  /**
   * Gets the current player's color.
   *
   * @return the PlayerColor of the current player.
   */
  public PlayerColor currentPlayerColor() {
    return currentPlayer.isRed() ? PlayerColor.RED : PlayerColor.BLUE;
  }

  /**
   * Gets the opposite player's color.
   *
   * @return the PlayerColor of the opposite player.
   */
  public PlayerColor oppositePlayerColor() {
    return currentPlayer.isRed() ? PlayerColor.BLUE : PlayerColor.RED;
  }

  /**
   * Provides a string representation of the current state of the game.
   *
   * @return a formatted string representing the game state.
   */
  public String toString() {

    return currentPlayer.toString()
            + "\n"
            + grid.toString(redPlayer)
            + "\n"
            + currentPlayer.handToString();
  }
}
