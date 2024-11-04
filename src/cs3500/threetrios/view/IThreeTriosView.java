package cs3500.threetrios.view;

import cs3500.threetrios.model.PlayerColor;

public interface IThreeTriosView {
  void setGrid(IGameGridPanel gridPanel);
  void showMessage(String message);
  void highlightCard(int cardIndex, PlayerColor playerColor);
  void deselectCard();
}
