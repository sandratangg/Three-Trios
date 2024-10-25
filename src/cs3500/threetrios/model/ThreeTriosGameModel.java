package cs3500.threetrios.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a ThreeTrios game model.
 */
public class ThreeTriosGameModel implements ThreeTrios {
  private final ThreeTriosGrid grid;
  private final ThreeTriosPlayer redPlayer;
  private final ThreeTriosPlayer bluePlayer;
  private ThreeTriosPlayer currentPlayer;
  private ThreeTriosPlayer oppositePlayer;

  /**
   * Constructor for a ThreeTrios game model.
   * @param rows the number of rows in the grid
   * @param cols the number of columns in the grid
   * @param deck lists of cards that represents the deck
   */
  public ThreeTriosGameModel(int rows, int cols, List<ThreeTriosCard> deck) {
    this.grid = new ThreeTriosGrid(rows, cols);
    int cardsPerPlayer = deck.size() / 2;

    this.redPlayer = new ThreeTriosPlayer(PlayerColor.RED, deck.subList(0, cardsPerPlayer));
    this.bluePlayer = new ThreeTriosPlayer(PlayerColor.BLUE, deck.subList(cardsPerPlayer, deck.size()));
    this.currentPlayer = redPlayer;
    this.oppositePlayer = bluePlayer; // Red player starts
  }

  /**
   * Method to place a card on the grid.
   * @param row the row to place the card
   * @param col the column to place the card
   * @return true or false whether the card was placed
   */
  public boolean placeCard(int row, int col, ThreeTriosCard card) {
    if (!currentPlayer.playCard(card)) {
      throw new IllegalArgumentException("Player does not have this card.");
    }

    if (!grid.placeCard(row, col, card)) {
      return false;  // Invalid move
    }

    performBattlePhase(row, col, card, currentPlayer, oppositePlayer);
    switchTurn();
    return true;
  }

  // Helper method to perform the battle phase
  private void performBattlePhase(int row, int col, ThreeTriosCard placedCard, ThreeTriosPlayer currentPlayer, ThreeTriosPlayer oppositePlayer) {
    for (Direction direction : Direction.values()) {
      grid.battlePhase(row, col, placedCard, direction, currentPlayer, oppositePlayer);
    }
  }

  // Helper method to switch the turn
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

  public String toString() {
    StringBuilder gameModelString = new StringBuilder();

    gameModelString.append(currentPlayer.toString());
    gameModelString = newLine(gameModelString);

    gameModelString.append(grid.toString(redPlayer));
    gameModelString = newLine(gameModelString);

    gameModelString.append(currentPlayer.handToString());

    return gameModelString.toString();
  }

  public StringBuilder newLine(StringBuilder string) {
    return string.append("\n");
  }

  public boolean isGameOver() {
    return grid.isGridFull();
  }

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

  private int countOwnedCards(ThreeTriosPlayer player) {
    return player.getOwnedCardsSize();  // Include cards in hand
  }

  public static List<ThreeTriosCard> readCardsFromFile(String filename) throws FileNotFoundException {
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

      deck.add(new ThreeTriosCard(cardName, attackNorth, attackSouth, attackEast, attackWest));
    }

    return deck;
  }

  // Helper method to parse attack values, accounting for 'A' (10 in hexadecimal)
  private static int parseCardValue(String value) {
    if (value.equals("A")) {
      return 10;  // A stands for 10 in hexadecimal
    } else {
      return Integer.parseInt(value);
    }
  }
}
