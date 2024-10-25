package cs3500.threetrios.model;

public interface ICard {
  // Method to get the attack value for a specific direction
  int attack(Direction direction);

  // Method to check if the card name matches a given name
  boolean matchesName(String name);
}
