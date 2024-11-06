package cs3500.threetrios.controller;

import java.util.Optional;

import cs3500.threetrios.model.ReadOnlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosPlayer;

interface ThreeTriosStrategy {
  Optional<Move> chooseMove(ReadOnlyThreeTriosModel model, ThreeTriosPlayer player);
}