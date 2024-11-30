package cs3500.threetrios.provider.view;


import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.*;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.provider.controller.IThreeTriosPlayerFeatures;
import cs3500.threetrios.provider.model.GameOutcome;
import cs3500.threetrios.provider.model.IThreeTriosCard;
import cs3500.threetrios.provider.model.IThreeTriosViewModel;

/**
 * View class for the Three Trios game.
 */
public class ProviderThreeTriosViewProvider extends JFrame implements ProviderIThreeTriosView {
  private final AbstractGridPanel gridPanel;
  private final AbstractPlayerHandPanel redPlayerHandPanel;
  private final AbstractPlayerHandPanel bluePlayerHandPanel;
  private IThreeTriosPlayerFeatures features;
  private final IThreeTriosViewModel model;

  /**
   * Constructor for the view.
   */
  public ProviderThreeTriosViewProvider(IThreeTriosViewModel model) {
    this.model = Objects.requireNonNull(model, "Model cannot be null");

    // Set up hand and grid panels
    gridPanel = new ProviderGridPanel();
    redPlayerHandPanel = new ProviderPlayerHandPanel(PlayerColor.RED);
    bluePlayerHandPanel = new ProviderPlayerHandPanel(PlayerColor.BLUE);
    setUpGUI();
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }

  // Sets up the GUI of the view, including the two hand panels, the grid, and other layout/resizing
  // logic.
  private void setUpGUI() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Pass grid and player hands from model to panels
    gridPanel.setGrid(model.getGrid());
    // Set player hand
    List<IThreeTriosCard> redHand = model.getPlayerHand(PlayerColor.RED);
    List<IThreeTriosCard> blueHand = model.getPlayerHand(PlayerColor.BLUE);
    redPlayerHandPanel.setPlayerHand(redHand);
    bluePlayerHandPanel.setPlayerHand(blueHand);
    // Set current player
    setTitle("Current player: " + model.getCurrTurnPlayerColor());

    // Set size, layout, and position
    BorderLayout layout = new BorderLayout();
    layout.setHgap(10);
    setLayout(layout);
    pack(); // Set size before centering
    setLocationRelativeTo(null); // Center window

    redPlayerHandPanel.setPreferredSize(new Dimension(getPanelWidth(), getHeight()));
    bluePlayerHandPanel.setPreferredSize(new Dimension(getPanelWidth(), getHeight()));

    this.add(gridPanel, BorderLayout.CENTER);
    this.add(redPlayerHandPanel, BorderLayout.WEST);
    this.add(bluePlayerHandPanel, BorderLayout.EAST);

    // Resize the hand panels and grid when the window is resized
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        redPlayerHandPanel.setPreferredSize(new Dimension(getPanelWidth(), getHeight()));
        bluePlayerHandPanel.setPreferredSize(new Dimension(getPanelWidth(), getHeight()));
        // The grid panel is placed at the center, so it should resize automatically
        repaint();
      }
    });
  }

  // Returns the width of the hand panel.
  private int getPanelWidth() {
    return getWidth() / 7;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(800, 600);
  }

  @Override
  public void subscribe(IThreeTriosPlayerFeatures features) {
    this.features = Objects.requireNonNull(features, "Features cannot be null");

    redPlayerHandPanel.setCardOnClick((Integer handIdx) -> {
      this.features.selectCardFromHand(PlayerColor.RED, handIdx);
    });
    bluePlayerHandPanel.setCardOnClick((Integer handIdx) -> {
      this.features.selectCardFromHand(PlayerColor.BLUE, handIdx);
    });
    gridPanel.setCellOnClick((Integer gridRow, Integer gridCol) -> {
      this.features.selectGridCell(gridRow, gridCol);
    });
  }

  // Returns the corresponding hand panel for a given player.
  private AbstractPlayerHandPanel getHandPanel(PlayerColor player) {
    AbstractPlayerHandPanel handPanel =
            player == PlayerColor.RED ? redPlayerHandPanel : bluePlayerHandPanel;
    return handPanel;
  }

  @Override
  public boolean hasSelectedCard(PlayerColor player) {
    Objects.requireNonNull(player, "Player cannot be null");
    AbstractPlayerHandPanel handPanel = getHandPanel(player);
    return handPanel.hasSelectedCard();
  }

  @Override
  public Optional<Integer> getSelectedHandCardIdx(PlayerColor player) {
    Objects.requireNonNull(player, "Player cannot be null");
    AbstractPlayerHandPanel handPanel = getHandPanel(player);
    return handPanel.getSelectedHandIdx();
  }

  @Override
  public void setSelectedCard(PlayerColor player, int handIdx) {
    Objects.requireNonNull(player, "Player cannot be null");
    AbstractPlayerHandPanel handPanel = getHandPanel(player);

    // This will throw an error if the hand index is out of bounds
    handPanel.setSelectedCard(handIdx);
  }

  @Override
  public void clearCardSelection(PlayerColor player) {
    AbstractPlayerHandPanel handPanel = getHandPanel(player);
    handPanel.clearCardSelection();
  }

  @Override
  public void rerenderHand(PlayerColor handOwner) {
    Objects.requireNonNull(handOwner, "Hand owner should not be null");
    AbstractPlayerHandPanel handPanel = getHandPanel(handOwner);
    handPanel.setPlayerHand(model.getPlayerHand(handOwner));
    handPanel.repaint();
  }

  @Override
  public void showDialog(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void rerender() {
    // Supply the hand panels with the updated player hands
    redPlayerHandPanel.setPlayerHand(model.getPlayerHand(PlayerColor.RED));
    bluePlayerHandPanel.setPlayerHand(model.getPlayerHand(PlayerColor.BLUE));
    // Update the title
    setTitle("Current player: " + model.getCurrTurnPlayerColor());
    // Supply grid with updated grid
    gridPanel.setGrid(model.getGrid());
    repaint();
    if (model.isGameOver()) {
      showGameOverMessage();
    }
  }

  private void showGameOverMessage() {
    GameOutcome outcome = model.getGameOutcome();
    String playerName;
    int score;
    switch (outcome) {
      case RED_WINS:
        playerName = "RED";
        score = model.getPlayerScore(PlayerColor.RED);
        break;
      case BLUE_WINS:
        playerName = "BLUE";
        score = model.getPlayerScore(PlayerColor.BLUE);
        break;
      default:
        int redScore = model.getPlayerScore(PlayerColor.RED);
        int blueScore = model.getPlayerScore(PlayerColor.BLUE);
        String msg =
                String.format("Tie game, RED had %d points, BLUE had %d points", redScore, blueScore);
        showDialog(msg);
        return;
    }
    String msg =
            String.format("Game over, %s wins with %d points", playerName, score);
    showDialog(msg);
  }
}
