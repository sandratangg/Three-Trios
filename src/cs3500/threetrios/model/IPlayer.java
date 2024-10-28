package cs3500.threetrios.model;

public interface IPlayer {
  boolean playCard(ICard card);
  void addCard(ICard card);
  boolean owns(ICard card);
  String handToString();
  int getOwnedCardsSize();
}