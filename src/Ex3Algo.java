package assignments.Ex3;

import exe.ex3.game.Game;
import exe.ex3.game.GhostCL;
import exe.ex3.game.PacManAlgo;
import exe.ex3.game.PacmanGame;

public class Ex3Algo implements PacManAlgo {
    private int _prevDir = -1;

    @Override
    public String getInfo() {
        return "Level 4 Optimized AI: True Path Danger Mapping & Power-up Prioritization.";
    }

    @Override
    public int move(PacmanGame game) {
        String[] parts = game.getPos(0).toString().split(",");
        Index2D pacmanPos = new Index2D(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        int[][] board = game.getGame(0);
        GhostCL[] ghosts = game.getGhosts(0);

        return calculateBestMove(game, ghosts, pacmanPos, board);
    }

    private int calculateBestMove(PacmanGame game, GhostCL[] ghosts, Index2D pacman, int[][] board) {
        Map map = new Map(board);
        int[] dirs = {Game.UP, Game.DOWN, Game.LEFT, Game.RIGHT};
        Index2D[] neighbors = getNeighbors(pacman, board.length, board[0].length);

        int bestDir = -1;
        double maxScore = -Double.MAX_VALUE;

        for (int i = 0; i < neighbors.length; i++) {
            Index2D next = neighbors[i];
            if (!map.isInside(next) || map.getPixel(next) == 1) continue;

            double score = evaluateMove(next, ghosts, board, map);

            if (dirs[i] == _prevDir) score += 5;

            if (score > maxScore) {
                maxScore = score;
                bestDir = dirs[i];
            }
        }

        _prevDir = (bestDir == -1) ? Game.UP : bestDir;
        return _prevDir;
    }

    private double evaluateMove(Index2D next, GhostCL[] ghosts, int[][] board, Map map) {
        double score = 0;

        for (GhostCL g : ghosts) {
            Index2D gPos = getGhostIdx(g);
            double d = getPathDist(next, gPos, board);

            if (g.getStatus() == 1 && g.remainTimeAsEatable(0) < 2) {
                if (d < 3) score -= 10000;
                else score -= (2000.0 / (d * d));
            } else if (g.getStatus() == 1 && g.remainTimeAsEatable(0) > 2) {
                score += (500.0 / (d + 1));
            }
        }

        int cell = map.getPixel(next);
        if (cell == 3) score += 50;
        if (cell == 5) score += 500;

        if (isDeadEnd(next, map)) score -= 100;

        return score;
    }

    private double getPathDist(Pixel2D a, Pixel2D b, int[][] board) {
        Map m = new Map(board);
        Map2D dMap = m.allDistance(a, 1);
        int d = dMap.getPixel(b);
        return (d == -1) ? 100 : d;
    }

    private Index2D getGhostIdx(GhostCL g) {
        String[] p = g.getPos(0).split(",");
        return new Index2D(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }

    private boolean isDeadEnd(Index2D p, Map m) {
        int count = 0;
        for (Index2D n : getNeighbors(p, m.getWidth(), m.getHeight())) {
            if (m.getPixel(n) != 1) count++;
        }
        return count <= 1;
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