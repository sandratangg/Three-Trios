package cs3500.threetrios.model;

import java.util.List;

public class ThreeTriosGameModel implements ThreeTrios {
  private final ThreeTriosGrid grid;
  private final ThreeTriosPlayer redPlayer;
  private final ThreeTriosPlayer bluePlayer;
  private ThreeTriosPlayer currentPlayer;

  public ThreeTriosGameModel(int rows, int cols, List<ThreeTriosCard> deck) {
    this.grid = new ThreeTriosGrid(rows, cols);
    int cardsPerPlayer = deck.size() / 2;

    this.redPlayer = new ThreeTriosPlayer(PlayerColor.RED, deck.subList(0, cardsPerPlayer));
    this.bluePlayer = new ThreeTriosPlayer(PlayerColor.BLUE, deck.subList(cardsPerPlayer, deck.size()));
    this.currentPlayer = redPlayer;  // Red player starts
  }

  public boolean placeCard(int row, int col, ThreeTriosCard card) {
    if (!currentPlayer.playCard(card)) {
      throw new IllegalArgumentException("Player does not have this card.");
    }

    if (!grid.placeCard(row, col, card)) {
      return false;  // Invalid move
    }

    performBattlePhase(row, col, card);
    switchTurn();
    return true;
  }

  private void performBattlePhase(int row, int col, ThreeTriosCard placedCard) {
    for (Direction direction : Direction.values()) {
      grid.battlePhase(row, col, placedCard, direction);
    }
  }

  private void switchTurn() {
    if (currentPlayer == redPlayer) {
      // If the current player is red, switch to blue
      currentPlayer = bluePlayer;
    } else {
      // If the current player is blue, switch to red
      currentPlayer = redPlayer;
    }
  }
}
