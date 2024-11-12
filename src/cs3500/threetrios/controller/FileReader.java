package cs3500.threetrios.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.model.ThreeTriosGrid;

/**
 * Class that reads files.
 */
public class FileReader {

  /**
   * Reads a list of cards from a file and creates a deck. The file should
   * contain card information in lines, each specifying a card's name and
   * attack values for the north, south, east, and west directions. Attack
   * values should range from 1 to 10, with 'A' representing 10.
   *
   * @param filePath the path to the file containing card data.
   * @return a list of {@link ThreeTriosCard} objects representing the deck.
   * @throws FileNotFoundException if the specified file cannot be found.
   */
  public static List<ThreeTriosCard> readCardsFromFile(String filePath)
          throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filePath));
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

  /**
   * Reads a grid configuration from a file and creates a string representation of the grid.
   * The file should contain grid dimensions in the first line, followed by grid cell configurations,
   * where 'C' represents an empty card cell and 'X' represents a hole.
   *
   * @param filePath the path to the grid configuration file.
   * @return a string representation of the grid configuration.
   * @throws FileNotFoundException if the specified file cannot be found.
   */
  public static ThreeTriosGrid gridFromFile(String filePath) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(filePath));
    int rows = scanner.nextInt();
    int cols = scanner.nextInt();
    scanner.nextLine();  // Move to the next line after the dimensions

    ThreeTriosGrid grid = new ThreeTriosGrid(rows, cols);

    for (int row = 0; row < rows; row++) {
      String line = scanner.nextLine();
      for (int col = 0; col < cols; col++) {
        char cellChar = line.charAt(col);
        if (cellChar == 'C') {
          grid.placeEmptyCardCell(row, col);  // Method to initialize an empty card cell
        } else if (cellChar == 'X') {
          grid.placeHole(row, col);  // Method to mark a hole
        }
      }
    }

    return grid;
  }


  // Helper method to parse attack values, accounting for 'A' (10 in hexadecimal).
  private static int parseCardValue(String value) {
    if (value.equals("A")) {
      return 10;  // 'A' represents 10 in hexadecimal format.
    } else {
      return Integer.parseInt(value);
    }
  }
}


