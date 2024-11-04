package cs3500.threetrios.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class ThreeTriosView extends JFrame implements IThreeTriosView {
  private IGameGridPanel gridPanel;
  private PlayerHandPanel leftHandPanel;
  private PlayerHandPanel rightHandPanel;
  private JLabel messageLabel;

  public ThreeTriosView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");
    setLayout(new BorderLayout());

    // Set up grid panel
    gridPanel = new GameGridPanel(model);
    add((Component) gridPanel, BorderLayout.CENTER);

    // Set up player hands
    leftHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED);
    rightHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE);

    add((Component) leftHandPanel, BorderLayout.WEST);
    add((Component) rightHandPanel, BorderLayout.EAST);

    // Message label at the top
    messageLabel = new JLabel("Current player: RED");
    add(messageLabel, BorderLayout.NORTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  @Override
  public void setGrid(IGameGridPanel gridPanel) {
    this.gridPanel = gridPanel;
  }

  public void setPlayerHand(PlayerHandPanel leftHandPanel, PlayerHandPanel rightHandPanel) {
    this.leftHandPanel = leftHandPanel;
    this.rightHandPanel = rightHandPanel;
  }

  @Override
  public void showMessage(String message) {
    messageLabel.setText(message);
  }

  @Override
  public void highlightCard(int cardIndex, PlayerColor playerColor) {
    /*if (playerColor == PlayerColor.RED) {
      leftHandPanel.highlightCard(cardIndex);
    } else {
      rightHandPanel.highlightCard(cardIndex);
    }*/
  }

  @Override
  public void deselectCard() {
    /*leftHandPanel.deselectCard();
    rightHandPanel.deselectCard();*/
  }
}
