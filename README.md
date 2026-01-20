# Intro2CS_Ex3 - Pac-Man Game: Server and Autonomous Algorithm

This project presents a comprehensive implementation of the Pac-Man game, featuring a custom-built game server and a sophisticated decision-making algorithm.

## Project Architecture

### 1. Game Server Infrastructure
The infrastructure was developed to handle the game's core mechanics and real-time visualization:
* **MyPacmanGame**: The central engine managing the game loop (Update-Render-Pause). It handles collision detection, score tracking, and coordinates between entities.
* **Ghost Class**: Manages ghost entities, including their movement logic and rendering.
* **MapLoader**: A utility class for loading and parsing level configurations from text files into a 2D matrix.
* **GUI Layer**: A modified StdDraw implementation adapted for the specific project requirements, ensuring smooth double-buffered rendering.

---

### 2. Autonomous Algorithm (Logic & Strategy)

The algorithm is designed to navigate Pacman through the maze by switching between three logical modes based on environmental threats and opportunities:

#### **Mode Selection Logic**
The system constantly monitors the distance to ghosts (defined in `Parameters.java`).
* **Eat Ghosts Mode**: Active when Pacman consumes a Green Power Pellet and a ghost is within a reachable distance.
* **Run from Ghosts Mode**: Triggered when a non-vulnerable ghost is within the "Danger Zone" distance.
* **Eat Pinks Mode**: The default state when no immediate danger is detected or when ghosts are too far to pursue.

#### **Implementation of Modes**

**1. Eat Pinks (Point Collection)**
The algorithm uses a Breadth-First Search (BFS) approach via the `allDistance` method. It maps the distances from Pacman's current position to all points on the grid. It searches for the nearest tile containing a Pink (3) or Green (5) point and calculates the `shortestPath` to that target.

**2. Eat Ghosts (Offensive)**
When ghosts become vulnerable, the algorithm identifies the closest ghost using a 2D distance calculation. Once the target ghost is selected, the `closestGhost_path` function generates an intercept path using the shortest-path logic.

**3. Run from Ghosts (Survival & Danger Score)**
To avoid being caught, the algorithm evaluates every possible move by calculating a **Danger Score**. The goal is to choose the direction with the lowest risk:
* **Ghost Proximity**: Danger is calculated as `1000.0 / (distance^2 + 1)`. Squaring the distance gives significantly higher weight to closer ghosts.
* **Dead-End Avoidance**: A `isDeadEnd` function checks if a move leads to a trap (surrounded by 3 or more walls). Traps receive a heavy score penalty (-5000.0).
* **Stutter Prevention**: To prevent Pacman from oscillating back and forth between two tiles, a small bonus (+5) is given to the direction Pacman is already moving, encouraging momentum.
* **Final Calculation**: The total score balances ghost distance and the path to the nearest Green point. The algorithm then selects the move with the highest resulting value (representing the safest path).

#### **Path Execution**
The `calculate_path` function orchestrates these modes and passes the selected route to `followPath`. This utility identifies the next immediate step (Up, Down, Left, or Right) relative to Pacman's current coordinates and executes the move.

---
## Technical Specifications
* **Data Structures**: 2D Integer arrays for board representation and distance mapping.
* **Algorithms**: BFS for pathfinding, weighted heuristic scoring for survival.
* **Environment**: Java, StdDraw GUI.

