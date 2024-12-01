package cs3500.threetrios.adapters;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.provider.controller.IThreeTriosModelObserver;
import cs3500.threetrios.provider.controller.IThreeTriosPlayerFeatures;

/**
 * Adapter class to integrate ThreeTriosController with the provider's interfaces.
 */
public class ControllerAdapter implements IThreeTriosPlayerFeatures, IThreeTriosModelObserver {

  private final ThreeTriosController controller;

  /**
   * Constructs a ControllerAdapter for a given ThreeTriosController.
   *
   * @param controller the controller to adapt
   */
  public ControllerAdapter(ThreeTriosController controller) {
    if (controller == null) {
      throw new NullPointerException("Controller cannot be null.");
    }
    this.controller = controller;
  }

  @Override
  public void selectGridCell(int row, int col) {
    controller.onGridCellClicked(row, col);
  }

  @Override
  public void selectCardFromHand(PlayerColor handOwner, Integer handIdx) {
    if (handOwner == null || handIdx == null) {
      throw new NullPointerException("Hand owner or hand index cannot be null.");
    }
    controller.onCardSelected(controller.getModel().getPlayerHand(handOwner).get(handIdx));
  }

  @Override
  public void playTurn() {
    // Delegate the playTurn logic to the controller's activate method
    controller.activate();
  }

  @Override
  public void handleGameStateUpdate() {
    // Notify the controller to update the view
    controller.updateView();
  }

  @Override
  public PlayerColor playerColor() {
    return controller.getPlayerColor();
  }
}
