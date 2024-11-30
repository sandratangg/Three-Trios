package cs3500.threetrios.view;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.model.Posn;
import cs3500.threetrios.model.ReadOnlyThreeTriosModel;

public class ThreeTriosView extends JFrame implements IThreeTriosView {
  private GameGridPanel gridPanel;
  private PlayerHandPanel leftHandPanel;
  private PlayerHandPanel rightHandPanel;
  private JLabel messageLabel;
  private ReadOnlyThreeTriosModel model;

  public ThreeTriosView(ReadOnlyThreeTriosModel model) {
    super("Three Trios Game");
    this.model = model;
    setLayout(new BorderLayout());

    gridPanel = new GameGridPanel(model);
    add((Component) gridPanel, BorderLayout.CENTER);

    leftHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED);
    rightHandPanel = new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE);
    add((Component) leftHandPanel, BorderLayout.WEST);
    add((Component) rightHandPanel, BorderLayout.EAST);

    messageLabel = new JLabel("Current player: RED");
    add(messageLabel, BorderLayout.NORTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
  }

  @Override
  public void setGrid(IGameGridPanel gridPanel) {
    this.gridPanel = (GameGridPanel) gridPanel;
  }

  public void setPlayerHand(PlayerHandPanel leftHandPanel, PlayerHandPanel rightHandPanel) {
    remove(this.leftHandPanel);
    remove(this.rightHandPanel);
    this.leftHandPanel = leftHandPanel;
    this.rightHandPanel = rightHandPanel;
    add(this.leftHandPanel, BorderLayout.WEST);
    add(this.rightHandPanel, BorderLayout.EAST);
    revalidate();
    repaint();
  }

  @Override
  public void showMessage(String message) {
    messageLabel.setText(message);
  }

  @Override
  public void highlightCard(int cardIndex, PlayerColor playerColor) {

  }

  @Override
  public void deselectCard() {

  }

  public GameGridPanel getGrid() {
    if (gridPanel == null) {
      gridPanel = new GameGridPanel(model);
    }
    return gridPanel;
  }

  public PlayerHandPanel getLeftHandPanel() {
    return this.leftHandPanel;
  }

  public PlayerHandPanel getRightHandPanel() {
    return this.rightHandPanel;
  }

  public void setPlayerRepresentation(PlayerColor playerColor) {
    // Remove any existing components in the NORTH position
    BorderLayout layout = (BorderLayout) getContentPane().getLayout();
    Component existingComponent = layout.getLayoutComponent(BorderLayout.NORTH);
    if (existingComponent != null) {
      remove(existingComponent);
    }

    // Add the new player label
    JLabel playerLabel = new JLabel("Player: " + playerColor, JLabel.CENTER);
    add(playerLabel, BorderLayout.NORTH);

    // Refresh the frame
    revalidate();
    repaint();
  }



}
