package cs3500.threetrios.view;


import javax.swing.JPanel;
import javax.swing.BorderFactory;


import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ThreeTriosCard;
import cs3500.threetrios.controller.ThreeTriosController;


/**
 * Represents a panel for the player's hand in the Three Trios game, containing all cards in hand.
 */
public class PlayerHandPanel extends JPanel {
  private CellPanel selectedCardPanel = null;
  private ThreeTriosController controller;


  /**
   * Constructs a PlayerHandPanel based on the given list of cards and player color.
   *
   * @param cards the list of cards in the player's hand
   * @param color the color representing the player who owns the hand
   */
  public PlayerHandPanel(List<ThreeTriosCard> cards, PlayerColor color) {
    setLayout(new GridLayout(cards.size(), 1));
    removeAll(); // Clear existing components

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

    revalidate();
    repaint();
  }




  /**
   * Sets the controller for the player hand panel.
   *
   * @param controller the controller to set
   */
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
      controller.onCardSelected(selectedCardPanel.getCard()); // Pass the actual card to the controller
    }


    revalidate();
    repaint();
  }




  /**
   * Returns the card currently selected by the player, or null if no card is selected.
   *
   * @return the selected card, or null if none is selected
   */
  public ThreeTriosCard getSelectedCard() {
    if (selectedCardPanel != null) {
      return selectedCardPanel.getCard();
    }
    return null;
  }
}
