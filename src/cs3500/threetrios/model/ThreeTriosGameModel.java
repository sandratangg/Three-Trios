package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
   * <p>
   * Invariant: The `currentPlayer` starts as `redPlayer` and alternates
   * with `bluePlayer` on each turn, ensuring consistent turn order.
   * </p>
   * @param rows the number of rows in the game grid.
   * @param cols the number of columns in the game grid.
   * @param deck the list of cards representing the deck, split between players.
   */
  public ThreeTriosGameModel(int rows, int cols, List<ThreeTriosCard> deck) {
    this(new ThreeTriosGrid(rows, cols), deck);
  }


  /**
   * Constructs a ThreeTrios game model with a specified grid and deck of cards.
   * @param grid the game grid to use for the model.
   * @param deck the deck of cards to use for the model.
   */
  public ThreeTriosGameModel(ThreeTriosGrid grid, List<ThreeTriosCard> deck) {
    this.grid = grid;
    int cellsInGrid = grid.getNumCardCells();

    if (deck.size() <= cellsInGrid) {
      throw new IllegalStateException("If there are n card cells in the grid,"
              + "there must be at least n + 1 cards available to split.");
    }

    int cardsPerPlayer = deck.size() / 2;

    this.redPlayer = new ThreeTriosPlayer(PlayerColor.RED, deck.subList(0, cardsPerPlayer));
    this.bluePlayer = new ThreeTriosPlayer(PlayerColor.BLUE,
            deck.subList(cardsPerPlayer, deck.size()));
    this.currentPlayer = redPlayer;
    this.oppositePlayer = bluePlayer; // Red player starts
  }

  public ThreeTriosGameModel(String gridConfigPath, String cardConfigPath) throws FileNotFoundException {
    this(ThreeTriosGrid.fromFile(gridConfigPath), readCardsFromFile(cardConfigPath));
  }


  /**
   * Attempts to place a card at a specified location on the game grid.
   *
   * @param row  the row index at which to place the card.
   * @param col  the column index at which to place the card.
   * @param card the ThreeTriosCard to be placed on the grid.
   * @throws IllegalArgumentException if the card cannot be placed at the specified location.
   * @throws IllegalArgumentException if the player does not have the card.
   * @throws IllegalArgumentException if the position is invalid.
   */
  public void placeCard(int row, int col, ICard card) {
    if (!currentPlayer.playCard(card)) {
      throw new IllegalArgumentException("Player does not have this card.");
    }

    try {
      grid.placeCard(row, col, card);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid position: (" + row + ", " + col + ")");
    }

    performBattlePhase(row, col, card, currentPlayer, oppositePlayer);
    switchTurn();
  }



  // Helper method to perform the battle phase.
  private void performBattlePhase(int row, int col, ICard placedCard,
                                  ThreeTriosPlayer currentPlayer, ThreeTriosPlayer oppositePlayer) {
    for (Direction direction : Direction.values()) {
      grid.battlePhase(row, col, placedCard, direction, currentPlayer, oppositePlayer);
    }
  }

  /**
   * Helper method to switch the turn between players.
   * <p>
   * Invariant: The `currentPlayer` must always alternate between `redPlayer`
   * and `bluePlayer` after each valid move. This method ensures that
   * each player takes turns in sequence and that the game ends only when
   * the grid is full.
   * </p>
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
   * Provides a string representation of the game model's current state,
   * including the active player's information, the grid, and the current
   * player's hand. This representation supports debugging and tracking game progress.
   *
   * @return a string summarizing the game's current state.
   */
  public String toString() {
    StringBuilder gameModelString = new StringBuilder();

    gameModelString.append(currentPlayer.toString());
    gameModelString = newLine(gameModelString);

    gameModelString.append(grid.toString(redPlayer));
    gameModelString = newLine(gameModelString);

    gameModelString.append(currentPlayer.handToString());

    return gameModelString.toString();
  }

  /**
   * Adds a newline to a provided StringBuilder, primarily used to format
   * the string representation of the game model.
   *
   * @param string the StringBuilder to append a newline to.
   * @return the updated StringBuilder with an appended newline.
   */
  public StringBuilder newLine(StringBuilder string) {
    return string.append("\n");
  }

  public IGrid copyGrid() {
    return grid.copy();
  }

  public int getGridWidth() {
    return grid.getColCount();
  }

  public int getGridHeight() {
    return grid.getRowCount();
  }

  public ThreeTriosCell getCellContents(int x, int y) {
    return grid.getCell(x, y);
  }

  public List<ThreeTriosCard> getPlayerHand(PlayerColor playerColor) {
    if (playerColor.equals(PlayerColor.RED)) {
      return redPlayer.getHandCards();
    } else {
      return bluePlayer.getHandCards();
    }
  }

  public PlayerColor getCardOwner(int x, int y) {
    if (currentPlayer.owns(grid.getCardFromCell(x, y))) {
      return currentPlayerColor();
    } else if (oppositePlayer.owns(grid.getCardFromCell(x, y))) {
      return oppositePlayerColor();
    } else {
      throw new IllegalStateException("No player owns a card at this spot!");
    }
  }

  public boolean isLegalMove(int x, int y, ICard card, IPlayer player) {
    return grid.isLegalMove(x, y, card);
  }

  public int calculateFlippableCards(int x, int y, ICard card, IPlayer player) {
    return grid.calculateFlippableCards(x, y, card, player);
  }

  public int getPlayerScore(ThreeTriosPlayer player) {
    return countOwnedCards(player);
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
   * Determines the winner of the game based on the number of cards owned
   * by each player. In the event of a tie, a message indicating the tie
   * is returned.
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
   * Counts the total number of cards owned by a player, including
   * both the cards on the grid and those in the player's hand.
   *
   * @param player the player for whom to count the owned cards.
   * @return the number of cards owned by the player.
   */
  private int countOwnedCards(ThreeTriosPlayer player) {
    return player.getOwnedCardsSize();  // Includes cards in hand
  }

  /**
   * Reads a list of cards from a file and creates a deck. The file should
   * contain card information in lines, each specifying a card's name and
   * attack values for the north, south, east, and west directions. Attack
   * values should range from 1 to 10, with 'A' representing 10.
   *
   * @param filename the path to the file containing card data.
   * @return a list of {@link ThreeTriosCard} objects representing the deck.
   * @throws FileNotFoundException if the specified file cannot be found.
   */
  public static List<ThreeTriosCard> readCardsFromFile(String filename)
          throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filename));
    List<ThreeTriosCard> deck = new ArrayList<>();

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] cardData = line.split(" ");
      String cardName = cardData[0];
      int attackNorth = parseCardValue(cardData[1]);
      int attackSouth = parseCardValue(cardData[2]);
      int attackEast = parseCardValue(cardData[3]);
      int attackWest = parseCardValue(cardData[4]);

      deck.add(new ThreeTriosCard(cardName, attackNorth, attackEast, attackWest, attackSouth));
    }

    return deck;
  }

  // Helper method to parse attack values, accounting for 'A' (10 in hexadecimal).
  private static int parseCardValue(String value) {
    if (value.equals("A")) {
      return 10;  // 'A' represents 10 in hexadecimal format.
    } else {
      return Integer.parseInt(value);
    }
  }

  /**
   * Returns the color of the current player.
   * Determines if the current player is red or blue based on their attributes.
   *
   * @return the {@code PlayerColor} of the current player, either {@code RED} or {@code BLUE}
   */
  public PlayerColor currentPlayerColor() {
    if (this.currentPlayer.isRed()) {
      return PlayerColor.RED;
    } else {
      return PlayerColor.BLUE;
    }
  }

  public PlayerColor oppositePlayerColor() {
    if (this.currentPlayer.isRed()) {
      return PlayerColor.BLUE;
    } else {
      return PlayerColor.RED;
    }
  }

}
