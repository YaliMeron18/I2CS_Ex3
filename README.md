# Intro2CS_Ex3 - Pac-Man Game: Server and Algorithm Implementation

This project is a full implementation of the Pac-Man game, including a custom-built server-side infrastructure and an autonomous decision-making algorithm.

## 🚀 How to Run
1. Open the project in your IDE (IntelliJ/Eclipse).
2. Ensure the required JAR files are in the project's library.
3. Run `MyPacmanGame.java`.
4. **Controls:** Use the arrow keys for manual control or the algorithm will execute automatically depending on the main class configuration.

## ***Server-Side Implementation***

The server was built to handle the core mechanics of the game, replacing the need for external black-box game engines.

* **MyPacmanGame**: The central engine of the game. It manages the game timer, validates movements, handles item collection (points and power-ups), and coordinates the interaction between Pacman and the ghosts.
* **Ghost Class**: Manages the enemy entities. Each ghost has its own movement logic and state (active or vulnerable).
* **MapLoader**: A utility class that parses `.txt` files to generate dynamic game boards, allowing for custom level designs.
* **Rendering Engine**: A customized implementation using StdDraw that handles the visual layers of the game, including real-time score updates and animations.

## ***How the Algorithm Works***

The algorithm operates in three distinct modes, prioritized based on the current game state:

1.  **Eat Pinks (Food/Points)**: The default mode where Pacman identifies the nearest point and calculates the optimal path to consume it.
2.  **Eat Ghosts**: Triggered when Pacman consumes a "Green Point" (Power Pellet). If a ghost is within a specific distance, Pacman switches from gathering food to chasing the vulnerable ghosts.
3.  **Run from Ghosts**: A survival mode that activates when a non-vulnerable ghost is within a dangerous proximity. The algorithm calculates escape routes to maintain a safe distance.

### ***Game Logic Details***
* **Collision Detection**: The server continuously monitors coordinates. If Pacman hits an active ghost, the game ends. If he hits a vulnerable ghost, the ghost is respawned at the starting point.
* **Trigger Eatable**: When a green point is consumed, the `triggerEatable` function is called, changing all ghost statuses to vulnerable for a limited duration.
* **Pathfinding**: The algorithm uses a grid-based approach to navigate between walls and optimize the path toward targets.

## ***Technical Implementation***
* **Board Representation**: 2D Integer array (Matrix).
* **GUI**: StdDraw with double buffering for smooth rendering.
* **Language**: Java.

---
