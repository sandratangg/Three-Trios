package cs3500.threetrios.controller;

import cs3500.threetrios.model.*;
import cs3500.threetrios.view.*;
import cs3500.threetrios.strategies.Move;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

/**
 * The controller for managing a single player's actions in the Three Trios game.
 */
public class ThreeTriosController {
  private final ThreeTriosGameModel model;
  private final ThreeTriosView view;
  private final Player player;
  private final PlayerColor gamePlayer;
  private ThreeTriosCard selectedCard;

  /**
   * Constructs a ThreeTriosController with the given model, view, player, and player color.
   *
   * @param model      the game model
   * @param view       the game view
   * @param player     the player (human or AI)
   * @param gamePlayer the color representing the player (RED or BLUE)
   */
  public ThreeTriosController(ThreeTriosGameModel model, ThreeTriosView view, Player player, PlayerColor gamePlayer) {
    this.model = model;
    this.view = view;
    this.player = player;
    this.gamePlayer = gamePlayer;

    setupViewListeners();
    updateView();

    // If the player is AI, make an initial move
    if (!player.isHuman()) {
      makeAIMove();
    }
  }

  /**
   * Sets up listeners for the view to handle user interactions.
   */
  private void setupViewListeners() {
    if (player.isHuman()) {
      // Listener for card selection
      view.addCardSelectionListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (model.getCurrentPlayerColor().equals(gamePlayer)) {
            selectedCard = view.getSelectedCard();
            if (selectedCard != null) {
              System.out.println("Selected card: " + selectedCard);
            }
          }
        }
      });

      // Listener for grid cell clicks
      view.addGridClickListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          if (model.getCurrentPlayerColor().equals(gamePlayer) && selectedCard != null) {
            int row = ((GameGridPanel) view.getGrid()).getClickedRow(e);
            int col = ((GameGridPanel) view.getGrid()).getClickedCol(e);

            try {
              model.placeCard(row, col, selectedCard);
              updateView();
              makeAIMove();
            } catch (IllegalArgumentException ex) {
              view.showMessage("Invalid move: " + ex.getMessage());
            }
          }
        }
      });
    }
  }

  /**
   * Updates the view with the current game state.
   */
  private void updateView() {
    view.setGrid(new GameGridPanel(model));
    view.setPlayerHand(
            new PlayerHandPanel(model.getPlayerHand(PlayerColor.RED), PlayerColor.RED),
            new PlayerHandPanel(model.getPlayerHand(PlayerColor.BLUE), PlayerColor.BLUE)
    );
    view.showMessage("Current player: " + model.getCurrentPlayerColor());
  }

  /**
   * Makes a move for the AI player, if applicable.
   */
  private void makeAIMove() {
    if (!player.isHuman()) {
      Optional<Move> move = player.makeMove(model);
      move.ifPresent(m -> {
        model.placeCard(m.getRow(), m.getCol(), m.getCard());
        updateView();
      });
    }
  }
}