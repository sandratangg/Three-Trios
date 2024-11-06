package cs3500.threetrios.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;

public class PlayerHandPanel extends JPanel {
  private CellPanel selectedCardPanel = null;
  private List<ThreeTriosCard> cards;
  private PlayerColor color;

  public PlayerHandPanel(List<ThreeTriosCard> cards, PlayerColor color) {
    this.cards = cards; // Store the list of cards
    this.color = color; // Store the player color
    setLayout(new GridLayout(cards.size(), 1)); // Vertical layout

    for (int i = 0; i < cards.size(); i++) {
      ThreeTriosCard card = cards.get(i);
      CellPanel cardPanel = new CellPanel(card, color); // Use CellPanel for card display
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // Set preferred size for the card panel
      cardPanel.setPreferredSize(new Dimension(100, 150)); // Adjust width and height as needed

      int cardIndex = i; // Final variable to pass to the listener
      cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          handleCardSelection(cardPanel, cardIndex);
        }
      });

      add(cardPanel);
    }
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

    // Print the index and player color
    System.out.println("Card index: " + cardIndex + ", Player color: " + color);

    revalidate();
    repaint();
  }
}
