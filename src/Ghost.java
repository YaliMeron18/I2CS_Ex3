package assignments.Ex3;

import java.awt.Color;

public class Ghost {
    private int x, y;
    private Color color;

    public Ghost(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void moveRandomly(int[][] board) {
        int dir = (int)(Math.random() * 4);
        int nx = x, ny = y;
        if (dir == 0) nx--; // למעלה
        else if (dir == 1) nx++; // למטה
        else if (dir == 2) ny--; // שמאלה
        else if (dir == 3) ny++; // ימינה

        if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length && board[nx][ny] != 1) {
            this.x = nx;
            this.y = ny;
        }
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(y, x, 0.4); // עכשיו רק 3 פרמטרים!

        // עיניים
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledCircle(y - 0.15, x - 0.1, 0.1);
        StdDraw.filledCircle(y + 0.15, x - 0.1, 0.1);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}