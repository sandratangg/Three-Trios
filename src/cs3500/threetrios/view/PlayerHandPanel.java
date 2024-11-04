package cs3500.threetrios.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.model.PlayerColor;

public class PlayerHandPanel extends JPanel implements IPlayerHandPanel {
  private PlayerColor playerColor;
  private JButton[] cardButtons;
  private int selectedCard = -1;

  public PlayerHandPanel(PlayerColor playerColor) {
    this.playerColor = playerColor;
    setLayout(new GridLayout(0, 1));

    // Assume a fixed hand size for simplicity
    cardButtons = new JButton[10]; // Adjust size as needed
    for (int i = 0; i < cardButtons.length; i++) {
      final int cardIndex = i;
      cardButtons[i] = new JButton(playerColor + " Card " + (i + 1));
      cardButtons[i].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (selectedCard == cardIndex) {
            deselectCard();
          } else {
            System.out.println("Card clicked: " + cardIndex + " owned by " + playerColor);
            highlightCard(cardIndex);
          }
        }
      });
      add(cardButtons[i]);
    }
  }

  @Override
  public void highlightCard(int cardIndex) {
    if (selectedCard != -1) {
      cardButtons[selectedCard].setBackground(null);
    }
    selectedCard = cardIndex;
    cardButtons[cardIndex].setBackground(Color.YELLOW);
  }

  @Override
  public void deselectCard() {
    if (selectedCard != -1) {
      cardButtons[selectedCard].setBackground(null);
      selectedCard = -1;
    }
  }
}
