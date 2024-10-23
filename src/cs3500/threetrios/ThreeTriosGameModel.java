package cs3500.threetrios;

import java.util.List;

public class ThreeTriosGameModel {
  private final Grid grid;
  private final Player redPlayer;
  private final Player bluePlayer;
  private Player currentPlayer;

  public ThreeTriosGameModel(int rows, int cols, List<Card> deck) {
    this.grid = new Grid(rows, cols);
    int cardsPerPlayer = deck.size() / 2;

    this.redPlayer = new Player("Red", deck.subList(0, cardsPerPlayer));
    this.bluePlayer = new Player("Blue", deck.subList(cardsPerPlayer, deck.size()));
    this.currentPlayer = redPlayer;  // Red player starts
  }

  public boolean placeCard(int row, int col, Card card) {
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

  private void performBattlePhase(int row, int col, Card placedCard) {
    for (Direction direction : Direction.values()) {
      grid.battlePhase(row, col, placedCard, direction);
    }
  }

  private void switchTurn() {
    currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
  }
}
