package cs3500.threetrios.provider.view;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import cs3500.threetrios.model.PlayerColor;
import cs3500.threetrios.provider.model.IThreeTriosCard;

/**
 * Represents a panel on the Three Trios view that displays information about cards in a player's
 * hand, including each card's attack values and the player who owns it.
 */
public class ProviderPlayerHandPanel extends AbstractPlayerHandPanel {
  private List<IThreeTriosCard> hand;
  private final PlayerColor owner;
  private Optional<Integer> selectedCardIdx;

  /**
   * The constructor for a PlayerHandPanel.
   *
   * @param owner the owner of this hand
   */
  public ProviderPlayerHandPanel(PlayerColor owner) {
    this.owner = Objects.requireNonNull(owner, "Owner cannot be null");
    this.selectedCardIdx = Optional.empty();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int widthHeightSum = getWidth() + getHeight();
    Graphics2D g2d = (Graphics2D) g.create();
    int handSize = hand.size();
    if (handSize == 0) {
      return; // Don't paint anything if there are no cards
    }

    Color borderColor = Color.DARK_GRAY;
    int cardHeight = getHeight() / handSize;
    int cardWidth = getWidth();
    // Scale font size according to screen size
    int fontSize = widthHeightSum / 20;
    int attackValHorizontalOffset = cardWidth / 5;
    int attackValVerticalOffset = cardHeight / 5;

    Font font = new Font("Arial", Font.BOLD, fontSize);
    g2d.setFont(font);

    // Draw the cards
    for (int handIdx = 0; handIdx < hand.size(); handIdx++) {
      int cardYPos = handIdx * cardHeight;
      IThreeTriosCard card = hand.get(handIdx);

      DrawCardHelpers.drawCard(g2d, card, 0, cardYPos, cardWidth, cardHeight, fontSize,
              attackValHorizontalOffset, attackValVerticalOffset, owner
      );

      // If this card is highlighted, draw a thick border around it
      if (hasSelectedCard() && handIdx == selectedCardIdx.get()) {
        g2d.setStroke(new BasicStroke(widthHeightSum / 70));
      }
      g2d.setColor(borderColor);
      RectBorder border = new RectBorder(0, cardYPos, cardWidth, cardHeight);
      g2d.draw(border);
      g2d.setStroke(new BasicStroke(1));
    }
  }

  @Override
  void setCardOnClick(Consumer<Integer> onClick) {
    Objects.requireNonNull(onClick, "onClick cannot be null");
    addMouseListener(new MouseAdapter() {
      /**
       * Sets up an event listener that is triggered when a card is clicked. This notifies the
       * callback function of the player whose hand was just clicked, and the hand index of the
       * card.
       *
       * @param e the event to be processed
       */
      @Override
      public void mouseClicked(MouseEvent e) {
        // If the hand is out of cards, do nothing
        if (hand.size() == 0) {
          return;
        }
        Point2D evtPt = e.getPoint();
        int handIdx = getHandIndexFromEventPoint(evtPt);
        onClick.accept(handIdx);
      }
    });
  }

  // Converts from physical coordinates to a zero-based hand index
  private AffineTransform getPhysicalToModel() {
    AffineTransform transform = new AffineTransform();
    transform.scale(1, 1.0 / getCardHeight());
    return transform;
  }

  private double getCardHeight() {
    return getHeight() / hand.size();
  }

  private int getHandIndexFromEventPoint(Point2D evtPt) {
    AffineTransform transform = getPhysicalToModel();
    Point2D modelPt = transform.transform(evtPt, null);
    return (int) Math.floor(modelPt.getY());
  }

  @Override
  public void clearCardSelection() {
    throwISEIfHandNotSet();

    selectedCardIdx = Optional.empty();
  }

  @Override
  public void setSelectedCard(int handIdx) {
    throwISEIfHandNotSet();
    if (handIdx < 0 || handIdx >= hand.size()) {
      throw new IllegalArgumentException("Hand index cannot be out of bounds");
    }

    selectedCardIdx = Optional.of(handIdx);
  }

  // Throws an IllegalStateException if the hand field is null.
  private void throwISEIfHandNotSet() {
    if (hand == null) {
      throw new IllegalStateException("Hand panel has not received hand yet");
    }
  }

  @Override
  public Optional<Integer> getSelectedHandIdx() {
    throwISEIfHandNotSet();
    return selectedCardIdx;
  }

  @Override
  public boolean hasSelectedCard() {
    throwISEIfHandNotSet();
    return !selectedCardIdx.isEmpty();
  }

  @Override
  public void setPlayerHand(List<IThreeTriosCard> hand) {
    Objects.requireNonNull(hand, "Hand cannot be null");
    this.hand = new ArrayList<>(hand);
  }
}