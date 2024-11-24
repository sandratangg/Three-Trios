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

/**
 * Represents a panel that displays the player's hand of cards.
 * It allows the player to select a card, which will be highlighted when clicked.
 */
public class PlayerHandPanel extends JPanel {
  private CellPanel selectedCardPanel = null;
  private final PlayerColor color;

  /**
   * Constructs a PlayerHandPanel with the given list of cards and player color.
   * Each card is displayed in a vertical layout and is selectable by clicking on it.
   *
   * @param cards the list of cards to display
   * @param color the color of the player (RED or BLUE)
   */
  public PlayerHandPanel(List<ThreeTriosCard> cards, PlayerColor color) {
    this.color = color; // Store the player color
    setLayout(new GridLayout(cards.size(), 1)); // Vertical layout

    // Initialize card panels
    for (int i = 0; i < cards.size(); i++) {
      ThreeTriosCard card = cards.get(i);
      CellPanel cardPanel = new CellPanel(card, color); // Use CellPanel for card display
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      // Set preferred size for the card panel
      cardPanel.setPreferredSize(new Dimension(100, 150));

      int cardIndex = i; // Final variable to pass to the listener
      cardPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
          handleCardSelection(cardPanel, cardIndex);
        }
      });

      add(cardPanel);
    }
  }

  /**
   * Handles the selection of a card. If a card is already selected, it is deselected.
   * The newly selected card is highlighted with a yellow border.
   *
   * @param cardPanel the panel of the card that was clicked
   * @param cardIndex the index of the card that was clicked
   */
  private void handleCardSelection(CellPanel cardPanel, int cardIndex) {
    // Deselect previously selected card if any
    if (selectedCardPanel != null) {
      selectedCardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    // Toggle selection state of the clicked card
    if (selectedCardPanel == cardPanel) {
      selectedCardPanel = null; // Deselect if already selected
    } else {
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
      selectedCardPanel = cardPanel;
    }

    // Print the index and player color for debugging purposes
    System.out.println("Card index: " + cardIndex + ", Player color: " + color);

    revalidate();
    repaint();
  }

  public ThreeTriosCard getSelectedCard() {
    if (selectedCardPanel != null) {
      return selectedCardPanel.getCard(); // Assume `CellPanel` has a `getCard` method
    }
    return null;
  }

}