package cs3500.threetrios.controller;

import cs3500.threetrios.model.ThreeTriosCard;

class Move {
  int row;
  int col;
  ThreeTriosCard card;

  Move(int row, int col, ThreeTriosCard card) {
    this.row = row;
    this.col = col;
    this.card = card;
  }
}
