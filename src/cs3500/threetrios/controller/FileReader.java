package cs3500.threetrios.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs3500.threetrios.model.ThreeTriosCard;

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
   * @param filename the path to the file containing card data.
   * @return a list of {@link ThreeTriosCard} objects representing the deck.
   * @throws FileNotFoundException if the specified file cannot be found.
   */
  public static String cardFileReader(String filename) throws FileNotFoundException {
    StringBuilder deckBuilder = new StringBuilder();
    Scanner scanner = new Scanner(new File(filename));

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] cardData = line.split(" ");
      String cardName = cardData[0];
      int attackNorth = parseCardValue(cardData[1]);
      int attackSouth = parseCardValue(cardData[2]);
      int attackEast = parseCardValue(cardData[3]);
      int attackWest = parseCardValue(cardData[4]);

      // Add the card information to the deck representation
      deckBuilder.append("Card Name: ").append(cardName)
              .append(", North: ").append(attackNorth)
              .append(", South: ").append(attackSouth)
              .append(", East: ").append(attackEast)
              .append(", West: ").append(attackWest)
              .append("\n");
    }
    scanner.close();
    return deckBuilder.toString();
  }

  /**
   * Reads a grid configuration from a file and creates a string representation of the grid.
   * The file should contain grid dimensions in the first line, followed by grid cell configurations,
   * where 'C' represents an empty card cell and 'X' represents a hole.
   *
   * @param filename the path to the grid configuration file.
   * @return a string representation of the grid configuration.
   * @throws FileNotFoundException if the specified file cannot be found.
   */
  public static String gridFileReader(String filename) throws FileNotFoundException {
    StringBuilder gridBuilder = new StringBuilder();
    Scanner scanner = new Scanner(new File(filename));

    int rows = scanner.nextInt();
    int cols = scanner.nextInt();
    scanner.nextLine();  // Move to the next line after reading dimensions

    gridBuilder.append("Grid Dimensions: ").append(rows).append(" x ").append(cols).append("\n");

    for (int row = 0; row < rows; row++) {
      String line = scanner.nextLine();
      gridBuilder.append("Row ").append(row + 1).append(": ");
      for (int col = 0; col < cols; col++) {
        char cellChar = line.charAt(col);
        if (cellChar == 'C') {
          gridBuilder.append("[Empty Card Cell] ");
        } else if (cellChar == 'X') {
          gridBuilder.append("[Hole] ");
        }
      }
      gridBuilder.append("\n");
    }
    scanner.close();
    return gridBuilder.toString();
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


