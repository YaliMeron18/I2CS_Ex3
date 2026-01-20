package assignments.Ex3;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

public class Ex3Algo implements PacManAlgo {
    private int _prevDir = -1;
    // פרמטרים שניתן לשנות כדי להשפיע על רמת ה"פחד"
    private final double DANGER_DIST = 6.0;

    @Override
    public String getInfo() {
        return "Friend's Logic Optimized for Level 3: Higher Green Priority & Safer Movement.";
    }

    @Override
    public int move(PacmanGame game) {
        String posStr = game.getPos(0).replaceAll("[^0-9,]", "");
        String[] parts = posStr.split(",");
        Index2D pacman = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        int[][] board = game.getGame(0);
        GhostCL[] ghosts = game.getGhosts(0);
        Map map = new Map(board);

        return calculate_path(ghosts, pacman, board, map);
    }

    private int calculate_path(GhostCL[] ghosts, Index2D pacman, int[][] board, Map map) {
        boolean dangerZone = false;
        GhostCL targetGhost = null;
        double minDist = Double.MAX_VALUE;

        for (GhostCL g : ghosts) {
            double d = pacman.distance2D(getGhostPos(g));
            if (d < minDist) {
                minDist = d;
                targetGhost = g;
            }
            // אם רוח מסוכנת קרובה מדי
            if (g.getStatus() == 1 && g.remainTimeAsEatable(0) < 0.6 && d < DANGER_DIST) {
                dangerZone = true;
            }
        }

        // 1. מצב בריחה (משופר)
        if (dangerZone) {
            return runFromGhosts(pacman, ghosts, board, map);
        }

        // 2. מצב ציד (אגרסיבי יותר)
        if (targetGhost != null && targetGhost.getStatus() == 1 && targetGhost.remainTimeAsEatable(0) > 2.0) {
            if (minDist < 12.0) {
                Pixel2D[] path = map.shortestPath(pacman, getGhostPos(targetGhost), 1);
                if (path != null && path.length > 1) return followPath(pacman, path[1], board);
            }
        }

        // 3. מצב איסוף נקודות
        Pixel2D[] foodPath = eatPinks(pacman, board, map);
        if (foodPath != null && foodPath.length > 1) {
            return followPath(pacman, foodPath[1], board);
        }

        return runFromGhosts(pacman, ghosts, board, map);
    }

    private int runFromGhosts(Index2D pacman, GhostCL[] ghosts, int[][] board, Map map) {
        int[] dirs = {Game.UP, Game.DOWN, Game.LEFT, Game.RIGHT};
        Index2D[] neighbors = getNeighbors(pacman, board.length, board[0].length);

        double bestScore = -Double.MAX_VALUE;
        int bestDir = Game.UP;

        for (int i = 0; i < neighbors.length; i++) {
            Index2D next = neighbors[i];
            if (map.getPixel(next) == 1) continue;

            double ghostDanger = 0;
            for (GhostCL g : ghosts) {
                if (g.getStatus() == 1 && g.remainTimeAsEatable(0) < 0.6) {
                    double d = next.distance2D(getGhostPos(g));
                    // נוסחת החבר עם "קנס" גבוה יותר לקרבה ממשית
                    ghostDanger += (2000.0 / ((d * d * d) + 1));
                }
            }

            double greenDist = distToNearestGreen(next, board, map);

            // שיפור הנוסחה: הגדלת משקל המרחק מהירוק כדי שירוץ אליו בלחץ
            double totalScore = -(ghostDanger * 8) - (greenDist * 4);

            // קנס מבוי סתום (הגדלתי ל-10,000 בשביל שלב 3)
            if (isDeadEnd(next, map)) totalScore -= 10000.0;

            // בונוס המשכיות (מונע רעידות)
            if (dirs[i] == _prevDir) totalScore += 15;

            if (totalScore > bestScore) {
                bestScore = totalScore;
                bestDir = dirs[i];
            }
        }
        _prevDir = bestDir;
        return bestDir;
    }

    private Pixel2D[] eatPinks(Index2D pacman, int[][] board, Map map) {
        Map2D distMap = map.allDistance(pacman, 1);
        Pixel2D target = null;
        int minDist = Integer.MAX_VALUE;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == 3 || board[x][y] == 5) {
                    int d = distMap.getPixel(x, y);
                    if (d != -1 && d < minDist) {
                        minDist = d;
                        target = new Index2D(x, y);
                    }
                }
            }
        }
        return target != null ? map.shortestPath(pacman, target, 1) : null;
    }

    private double distToNearestGreen(Index2D p, int[][] board, Map map) {
        Map2D dists = map.allDistance(p, 1);
        int minG = 100;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == 5) {
                    int d = dists.getPixel(x, y);
                    if (d != -1 && d < minG) minG = d;
                }
            }
        }
        return minG;
    }

    private int followPath(Index2D p, Pixel2D next, int[][] board) {
        int w = board.length, h = board[0].length;
        if (next.getX() == (p.getX() + 1) % w) return Game.RIGHT;
        if (next.getX() == (p.getX() - 1 + w) % w) return Game.LEFT;
        if (next.getY() == (p.getY() + 1) % h) return Game.UP;
        return Game.DOWN;
    }

    private Index2D getGhostPos(GhostCL g) {
        String[] p = g.getPos(0).replaceAll("[^0-9,]", "").split(",");
        return new Index2D(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }

    private boolean isDeadEnd(Index2D p, Map m) {
        int ways = 0;
        for (Index2D n : getNeighbors(p, m.getWidth(), m.getHeight())) {
            if (m.getPixel(n) != 1) ways++;
        }
        return ways < 2;
    }

    private Index2D[] getNeighbors(Index2D p, int w, int h) {
        return new Index2D[] {
                new Index2D(p.getX(), (p.getY() + 1) % h),
                new Index2D(p.getX(), (p.getY() - 1 + h) % h),
                new Index2D((p.getX() - 1 + w) % w, p.getY()),
                new Index2D((p.getX() + 1) % w, p.getY())
        };
    }
}

