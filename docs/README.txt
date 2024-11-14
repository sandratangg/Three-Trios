Three Trios Game Model

Overview:
    This codebase models the Three Trios card game, simulating strategic gameplay through classes
representing players, cards, a game grid, and core game logic. The project organizes components
into a structured model to encapsulate rules, structure gameplay flow, and support grid-based
card interactions.

    The main objective is to capture the game’s mechanics, allowing for card placement, directional
combat, and turn-based interactions while maintaining separation between model, view,
and controller components.

---------------------------------------------------------------------------------------------------
Part 1: Original Implementation

Key Components:
The design organizes the game model into distinct classes and interfaces:

ThreeTriosGameModel:
The core game model, managing player turns, card placements, and grid interactions.
Handles game flow from card placement to victory determination.

    Key methods:
    placeCard for placing cards on the grid.
    battlePhase for resolving conflicts between opposing cards.


ThreeTriosGrid:
Represents the game grid, structured as a 2D array where each cell can hold a card or be a hole
(non-playable).
Manages card placements and battle mechanics for directional attacks.

ThreeTriosPlayer:
Represents each player, tracking their hand and owned cards on the grid.
Player color (red or blue) determines ownership of cards.
Manages ownership changes resulting from battles.

ThreeTriosCard:
Represents individual cards, each with a name and attack values in four cardinal directions.
Attack values influence battles when placed adjacent to other cards.

ThreeTriosCell:
Represents each cell on the grid, differentiating between playable spaces and holes.
Supports grid functionality by distinguishing empty and occupied cells.

--------------------------------------------------------------------------------------------------
Part 2: Refactoring and Enhancements

Key Updates:

Refactored File Reading:

    Moved fromFile and readCardsFromFile methods from ThreeTriosGameModel and
ThreeTriosGrid to a separate FileReader class in the controller package.
    This change ensures that the model focuses solely on game logic, with file handling delegated
to the controller.

Added Read-Only Access:

    Introduced ReadOnlyThreeTriosModel to enforce read-only access for views and strategies,
preventing unintended modifications to the game state.

Introduced Posn Class:

    Added Posn class to represent grid positions, used for specifying coordinates during card
placement and movement.

---------------------------------------------------------------------------------------------------
Detailed Breakdown of Updated Structure

Model (cs3500.threetrios.model)

ThreeTriosGameModel:
    Updated to work with the refactored FileReader.
    Supports card placement, battle phases, turn switching, and victory determination.
    Now includes a determineWinner method for game end conditions.

ThreeTriosGrid:
    Manages grid structure and cell initialization, including battle logic between neighboring cells
    Provides methods like placeCard, isLegalMove, and battlePhase for handling gameplay
    mechanics.

ThreeTriosPlayer:
    Manages player attributes, including hands, owned cards, and turn switching.
    Updated methods for adding/removing owned cards during battles.

ThreeTriosCard:
    Encapsulates properties like directional attack values and card names.
    Includes utility methods for attack comparisons and card identification.

ThreeTriosCell:
    Represents a cell on the game grid, distinguishing between holes and playable cells.
    Provides methods for checking occupancy and placing cards.

Posn:
    Represents coordinates on the grid, simplifying position handling for card placements.

ReadOnlyThreeTriosModel:
    Provides read-only access to game state, ensuring strategies and views cannot alter the model
    directly.


Controller (cs3500.threetrios.controller)

FileReader:
    Handles reading grid and card data from external files.
    Supports the following configuration files:
    board1.txt, board2.txt, board3.txt for grid setups.
    card_file1.txt, card_file2.txt for card definitions.

Strategies (cs3500.threetrios.strategies)

ThreeTriosStrategy Interface:
    Defines methods for selecting moves based on game state.
    Allows for flexible AI strategies that can be easily swapped.

MaximizeFlipsStrategy:
    Selects moves that flip the maximum number of opponent cards in one turn.

CornerPreferenceStrategy:
    Prefers placing cards in corners, which are harder to flip due to fewer adjacent cells.

TranscriptStrategyWrapper:
    Wraps existing strategies to log moves to a transcript file for analysis.

Move:
    Represents a card and its position on the grid as a single move.

View (cs3500.threetrios.view)

Graphical View:

    ThreeTriosView: Main graphical interface integrating grid and player hands.

    GameGridPanel: Displays the grid and handles card placements.

    PlayerHandPanel: Displays the player's hand and allows card selection.

    CellPanel: Represents individual cells on the grid, displaying card or hole status.

Testing (test.cs3500.threetrios)

The project includes JUnit tests for comprehensive coverage:

    ThreeTriosModelTests: Tests game model functionalities.
    ThreeTriosGridTests: Tests for grid configurations and card placements.
    ThreeTriosPlayerTests: Tests player operations, including card ownership.
    ThreeTriosCardTests: Tests card attributes and attack comparisons.
    Strategies Tests: Covers strategy implementations like MaximizeFlipsStrategyTests and
    CornerPreferenceStrategyTests.


Configuration Files:
The game is configured using text files located in the docs folder:

ExampleBoards:
    board1.txt, board2.txt, board3.txt: Define various grid layouts, specifying playable cells
    and holes.

ExampleCards:
    card_file1.txt, card_file2.txt: Define card names and their directional attack values.


Included Files in This Project:

Screenshots:
    Located in the docs/ViewScreenshots folder:
        GameStart.png: Demonstrates the game starting state.
        IntermediateState.png: Shows the game in progress.
        RedPlayerCard.png and BluePlayerCard.png: Illustrate card placements for both players.

Strategy Transcript:
    A plain-text file named strategy-transcript.txt is located in the docs folder.
    This file contains a transcript generated by the mocked model for the simplest strategy choosing
    a move for Red on the initial 3x3 board configuration with no holes.

Executable JAR File:
    The runnable JAR file (ThreeTrios.jar) is included in the root of the project directory.
    To run: java -jar ThreeTrios.jar