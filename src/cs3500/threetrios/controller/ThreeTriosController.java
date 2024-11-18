package cs3500.threetrios.controller;

import cs3500.threetrios.model.*;
import cs3500.threetrios.strategies.Move;
import cs3500.threetrios.view.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 * The controller for managing a single player's actions.
 */
public class ThreeTriosController {
  private final ThreeTriosGameModel model;
  private final ThreeTriosView view;
  private final Player player;
  private final ThreeTriosPlayer gamePlayer;
  private ThreeTriosCard selectedCard;

  public ThreeTriosController(ThreeTriosGameModel model, ThreeTriosView view, Player player, ThreeTriosPlayer gamePlayer) {
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

  private void setupViewListeners() {
    if (player.isHuman()) {

      //TO-DO: Implement listener method in the view
      view.addCardSelectionListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {

          //TO-DO: update this logic to check if the player's colors are the same

          if (model.getCurrentPlayer() == gamePlayer) {

            //TO-DO: add ability to get the selected card from the view
            selectedCard = view.getSelectedCard();

            //Pretty sure view does this but can't hurt to double check
            view.highlightSelectedCard(selectedCard);
          }
        }
      });

      view.addGridClickListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {

          //TO-DO: update this logic to check if the player's colors are the same
          if (model.getCurrentPlayer() == gamePlayer && selectedCard != null) {

            //TO-DO: add ability to get the selected row & col from the view
            int row = view.getSelectedRow();
            int col = view.getSelectedCol();
            try {
              model.placeCard(row, col, selectedCard);

              //NOTE: model already switches turns upon card placement + this is a private method
              //model.switchTurn();

              updateView();
              makeAIMove();
            } catch (IllegalArgumentException ex) {

              //TO-DO: Add ability to show errors in view
              view.showError(ex.getMessage());
            }
          }
        }
      });
    }
  }

  private void makeAIMove() {
    if (!player.isHuman()) {
      Optional<Move> move = player.makeMove(model);
      move.ifPresent(m -> {
        model.placeCard(m.getRow(), m.getCol(), m.getCard());

        //NOTE: model already switches turns upon card placement + this is a private method
        //model.switchTurn();

        updateView();
      });
    }
  }

  private void updateView() {

    //TO-DO: Add ability to update the current player in the view
    view.updateTurnIndicator(model.getCurrentPlayerColor());
  }
}
