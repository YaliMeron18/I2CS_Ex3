# Intro2CS_Ex3 - Pac-Man Game: Server and Algorithm Implementation

This project consists of two main components: a custom game server with a graphical interface, and a specialized algorithm designed to play the game autonomously.

## Project Structure

### 1. Game Server Implementation (The Infrastructure)
Unlike the standard assignment which uses a pre-compiled JAR, this project includes a custom-built server environment to manage the game flow.

* **MyPacmanGame**: The central engine that runs the game loop. It handles the synchronization between the logic and the GUI, manages the game state, and coordinates between entities.
* **Ghost Class**: Implements the antagonist entities. It includes autonomous movement patterns and handles the rendering of the ghosts on the screen.
* **MapLoader**: A utility class that enables the system to load various board configurations from text files, allowing for dynamic level design.
* **GUI (StdDraw)**: A customized visualization layer based on the StdDraw library, adapted to support specific drawing signatures required by the project environment.

### 2. Personal Algorithm Implementation (The Logic)
The core of the assignment is the autonomous decision-making algorithm. The algorithm's goal is to maximize the score while ensuring Pacman's survival.

* **Decision Making**: The algorithm analyzes the 2D board matrix in real-time to determine the most efficient path.
* **Priority System**:
    1.  **Point Collection**: Locating and moving towards the nearest "Pink" points to increase the score.
    2.  **Power-Up Strategy**: Identifying "Green" points to enter a temporary state where ghosts can be consumed.
    3.  **Survival Logic**: Calculating the distance from active ghosts and implementing an escape mechanism when a threat is detected.
* **Pathfinding**: Utilizing grid-based navigation to move Pacman between tiles while avoiding wall collisions.

## Technical Details
* **Language**: Java.
* **Graphics**: Based on StdDraw with double buffering to ensure smooth animation.
* **Data Structure**: The game board is represented as a 2D integer array for fast access and collision validation.

## How to Run
1.  Ensure all project files and the required JAR library are present in the IDE.
2.  Place the level configuration file (`level1.txt`) in the root folder.
3.  Run the main method in `MyPacmanGame.java`.
4.  The game will initialize the board and start the execution of the autonomous algorithm or manual control depending on the configuration.

---
