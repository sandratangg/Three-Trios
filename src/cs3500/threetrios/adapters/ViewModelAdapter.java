package cs3500.threetrios.adapters;

import cs3500.threetrios.model.*;
import cs3500.threetrios.provider.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter class to map ReadOnlyThreeTriosModel to IThreeTriosViewModel.
 */
public class ViewModelAdapter implements IThreeTriosViewModel {

  private final ReadOnlyThreeTriosModel model;

  /**
   * Constructs a ViewModelAdapter from a ReadOnlyThreeTriosModel.
   *
   * @param model the model to adapt
   */
  public ViewModelAdapter(ReadOnlyThreeTriosModel model) {
    if (model == null) {
      throw new NullPointerException("Model cannot be null.");
    }
    this.model = model;
  }

  @Override
  public List<IThreeTriosCard> getPlayerHand(PlayerColor color) {
    if (color == null) {
      throw new NullPointerException("Player color cannot be null.");
    }
    List<ThreeTriosCard> hand = model.getPlayerHand(color);

    // Adapt the hand of ThreeTriosCard to IThreeTriosCard
    return hand.stream()
            .map(CardAdapter::new)
            .collect(Collectors.toList());
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public GameOutcome getGameOutcome() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over yet.");
    }

    int redScore = model.getPlayerScore(PlayerColor.RED);
    int blueScore = model.getPlayerScore(PlayerColor.BLUE);

    if (redScore > blueScore) {
      return GameOutcome.RED_WINS;
    } else if (blueScore > redScore) {
      return GameOutcome.BLUE_WINS;
    } else {
      return GameOutcome.TIE;
    }
  }

  @Override
  public PlayerColor getCurrTurnPlayerColor() {
    return model.getCurrentPlayerColor();
  }

  @Override
  public ProviderIGrid getGrid() {
    return new GridAdapter(model.grid);
  }

  @Override
  public boolean isLegalMove(int gridRow, int gridCol) {
    return model.isLegalMove(gridRow, gridCol, null); // Replace null with the appropriate card if needed.
  }

  @Override
  public int getPotentialCardsFlipped(int handIdx, int gridRow, int gridCol) {
    List<ThreeTriosCard> hand = model.getPlayerHand(model.getCurrentPlayerColor());
    if (handIdx < 0 || handIdx >= hand.size()) {
      throw new IllegalArgumentException("Invalid hand index.");
    }
    ThreeTriosCard card = hand.get(handIdx);

    return model.calculateFlippableCards(gridRow, gridCol, card);
  }

  @Override
  public int getPlayerScore(PlayerColor color) {
    if (color == null) {
      throw new NullPointerException("Player color cannot be null.");
    }
    return model.getPlayerScore(color);
  }
}
