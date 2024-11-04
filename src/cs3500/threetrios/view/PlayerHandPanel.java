package cs3500.threetrios.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;

public class PlayerHandPanel extends JPanel {
  private CellPanel selectedCardPanel = null;

  public PlayerHandPanel(List<ThreeTriosCard> cards, PlayerColor color) {
    setLayout(new GridLayout(cards.size(), 1)); // Vertical layout

    for (ThreeTriosCard card : cards) {
      CellPanel cardPanel = new CellPanel(card, color); // Use CellPanel for card display
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // Set preferred size for the card panel
      cardPanel.setPreferredSize(new Dimension(100, 150)); // Adjust width and height as needed

      cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          handleCardSelection(cardPanel);
        }
      });

      add(cardPanel);
    }
  }

  private void handleCardSelection(CellPanel cardPanel) {
    if (selectedCardPanel != null) {
      selectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    if (selectedCardPanel == cardPanel) {
      selectedCardPanel = null;
    } else {
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
      selectedCardPanel = cardPanel;
    }

    revalidate();
    repaint();
  }
}
