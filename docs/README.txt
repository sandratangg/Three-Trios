Three Trios Game Model

Overview

This codebase models the Three Trios card game, simulating strategic gameplay through classes
representing players, cards, a game grid, and game logic. The code organizes components into a
structured model that enables card placement, directional combat, and turn-based interactions,
capturing the game’s mechanics. The main goal is to encapsulate rules, structure gameplay flow,
and support grid-based card interactions.

Key Components

The design organizes the game model into distinct classes and interfaces:

1. ThreeTriosGameModel: The core game model, managing player turns, card placements, and grid
   interactions. It drives game control flow, from card placement to victory determination. Key
   methods include `placeCard` to initiate card placement on the grid, and `battlePhase`, which
   resolves conflicts between opposing cards.

2. ThreeTriosGrid: Represents the game grid, a two-dimensional array where each cell can hold a
   card or be a hole (non-playable space). This grid manages card placements and battle mechanics
   for directional attacks between neighboring cells.

3. ThreeTriosPlayer: Represents each player, tracking their hand and owned cards on the grid.
   Player color (red or blue) determines ownership, with methods to manage ownership changes from
   battles.

4. ThreeTriosCard: Represents individual cards, each with a name and attack values in four
   cardinal directions. These attributes influence combat and provide unique strategic qualities
   for play. Directional values determine battle outcomes.

5. ThreeTriosCell: Represents each cell on the grid, either as a playable space (holding a card)
   or a hole (non-playable). This class differentiates empty and occupied cells, supporting grid
   functionality.

Key Interfaces

1. ThreeTrios: Interface for the game model, outlining methods for card placement on the grid.
   This interface is the entry point for game interaction, ensuring model alignment with gameplay.

2. IGrid: Interface for grid functionalities, defining methods for card placement and grid
   management. It abstracts grid structure for flexibility and maintainability.

3. IPlayer: Interface for player functionalities, including methods for managing hands and owned
   cards. It supports player operations for consistent interactions.

4. ICard: Interface for cards, defining behaviors like directional attacks and name matching. This
   abstracts card properties, providing a base for all card types.

5. ICell: Interface for cells, defining methods for checking occupancy. This enables uniform cell
   interactions, allowing grid management independent of specific implementations.

Organization

Primary code resides in the `cs3500.threetrios.model` package, containing core classes and
interfaces. The purpose of each file is as follows:

- `ThreeTriosGameModel.java`: Implements the main game model, coordinating actions, grid
   interactions, and game flow.
- `ThreeTriosGrid.java`: Manages grid structure, cell initialization, card placement, and battle
   logic.
- `ThreeTriosPlayer.java`: Defines player attributes, manages hands and owned cards, and supports
   ownership changes.
- `ThreeTriosCard.java`: Defines card properties and encapsulates directional attack values for
   gameplay.
- `ThreeTriosCell.java`: Represents grid cells, distinguishing playable cells and holes for
   accurate grid representation.


Configuration Files

The game setup includes grid and card configuration files:

board1.txt, board2.txt, board3.txt: Define various grid setups, specifying cell types and spaces.
card_file1.txt, card_file2.txt: List available cards and their directional attack values.


Textual Rendering of the Model

To facilitate visualization, we implemented a textual rendering feature (toString() method) within
the ThreeTriosGameModel class, allowing players and developers to view a simplified text-based
representation of the game board.


CHANGES FOR PART 2----------------------------------------------------------------------------------

- Refactored fromFile in ThreeTriosGameModel and readFromFile in ThreeTriosGrid (these components
 handle reading the configuration files) to a FileReader class in the controller package,
 since the model should not directly read the file.

- Created a ReadOnlyThreeTrios class
    - ReadOnlyThreeTriosMode.java: Provides read-only access to the game model.

- Create a Posn class:
    - Posn.java: Represents a position on the grid.

- Updated the ThreeTriosModel.java to ensure the card is placed correctly



Strategies (cs.3500.threetrios.strategies):

    Key Interface:
        1. ThreeTriosStrategy: Interface for card placement strategies, defining methods for selecting
        the next move based on the current game state.
    Key Components:
        - CornerPreferenceStrategy.java: Places cards in grid corners first, since it is harder to flip
        by exposing fewer sides
        - MaximizeFlipsStrategy.java: Chooses moves that flip the maximum number of opponent cards
        in one turn, prioritizing dominance on the grid.
        - Move.java: Encapsulates a card and grid position as a single move, supporting easy comparison
        for strategy-based decisions.

Textual View of the Game (cs3500.threetrios.view)

 The textual view of the game is displayed in the console, showing the game grid, player hands, and
 card placements. The view components include:

 Key Interfaces:
    1. IPlayerHandPanel: Interface for the player hand panel, defining methods for displaying and
        selecting cards in a player’s hand.
    2. IGameGridPanel: Interface for the game grid panel, outlining essential grid-related functions
        for display and interaction.
    3. IThreeTriosView: Interface for the main game view, providing a blueprint for rendering and
        managing game components.

 Key Components:
    - CellPanel.java: Represents individual cells on the game grid, displaying content
        (empty, hole, or card) and handling visual updates.
    - GameGridPanel.java: Displays the main game grid, managing cell arrangement
        and user interactions with the grid.
    - PlayerHandPanel.java: Shows the player’s hand, allowing card selection
        and highlighting of the selected card.
    - ThreeTriosSimpleTextView.java: Provides a simple textual representation of the game grid
        and player hands for easy debugging and testing.
    - ThreeTriosView: The main graphical view class that coordinates and displays the game grid,
        player hands, and other visual components.
