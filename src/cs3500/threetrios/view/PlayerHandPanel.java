package cs3500.threetrios.view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.controller.ThreeTriosController;

public class PlayerHandPanel extends JPanel {
  private CellPanel selectedCardPanel = null;
  private final PlayerColor color;
  private ThreeTriosController controller;

  public PlayerHandPanel(List<ThreeTriosCard> cards, PlayerColor color) {
    this.color = color;
    setLayout(new GridLayout(cards.size(), 1));

    for (int i = 0; i < cards.size(); i++) {
      ThreeTriosCard card = cards.get(i);
      CellPanel cardPanel = new CellPanel(card, color);
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      cardPanel.setPreferredSize(new Dimension(100, 150));

      int cardIndex = i;
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
          handleCardSelection(cardPanel, cardIndex);
        }
      });

      add(cardPanel);
    }
  }

  public void setController(ThreeTriosController controller) {
    this.controller = controller;
  }

  private void handleCardSelection(CellPanel cardPanel, int cardIndex) {
    if (selectedCardPanel != null) {
      selectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    if (selectedCardPanel == cardPanel) {
      selectedCardPanel = null;
    } else {
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
      selectedCardPanel = cardPanel;
    }

    if (controller != null && selectedCardPanel != null) {
      controller.onCardSelected(selectedCardPanel.getCard());
    }

    revalidate();
    repaint();
  }

  public ThreeTriosCard getSelectedCard() {
    if (selectedCardPanel != null) {
      return selectedCardPanel.getCard();
    }
    return null;
  }
}
