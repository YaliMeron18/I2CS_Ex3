package assignments.Ex3;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class MyPacmanGame {
    private int[][] board;
    private int px = 1, py = 1; // מיקום פקמן
    private Ghost[] ghosts;
    private int score = 0;
    private boolean isRunning = true;

    public MyPacmanGame(String mapFile) {
        board = MapLoader.loadMap(mapFile);
        // יצירת רוחות עם צבעים אמיתיים
        ghosts = new Ghost[]{
                new Ghost(2, 1, Color.RED),
                new Ghost(2, 5, Color.PINK)
        };
    }

    public void play() {
        int rows = board.length;
        int cols = board[0].length;

        // הגדרות StdDraw הסטנדרטי
        StdDraw.setCanvasSize(cols * 50, rows * 50);
        StdDraw.setXscale(-0.5, cols - 0.5);
        StdDraw.setYscale(rows - 0.5, -0.5); // הופך ציר Y למטריצה
        StdDraw.enableDoubleBuffering();

        while (isRunning) {
            update();
            render();
            StdDraw.show();
            StdDraw.pause(100);
        }
    }

    private void update() {
        int nx = px, ny = py;
        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) nx--;
        else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) nx++;
        else if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) ny--;
        else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) ny++;

        // בדיקת קירות וגבולות
        if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length && board[nx][ny] != 1) {
            px = nx; py = ny;
            // אכילת אוכל
            if (board[px][py] == 3 || board[px][py] == 5) {
                score += (board[px][py] == 5) ? 50 : 10;
                board[px][py] = 0;
            }
        }

        // תנועת רוחות ובדיקת פסילה
        for (Ghost g : ghosts) {
            g.moveRandomly(board);
            if (g.getX() == px && g.getY() == py) {
                isRunning = false;
            }
        }
    }

    private void render() {
        StdDraw.clear(Color.BLACK);

        // ציור הלוח
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 1) {
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.filledSquare(c, r, 0.45);
                } else if (board[r][c] == 3) {
                    StdDraw.setPenColor(Color.PINK);
                    StdDraw.filledCircle(c, r, 0.1);
                } else if (board[r][c] == 5) {
                    StdDraw.setPenColor(Color.GREEN);
                    StdDraw.filledCircle(c, r, 0.2);
                }
            }
        }

        // ציור פקמן
        StdDraw.setPenColor(Color.YELLOW);
        StdDraw.filledCircle(py, px, 0.4);

        // ציור רוחות
        for (Ghost g : ghosts) g.draw();

        // ציור ציון
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
        StdDraw.textLeft(0, -0.2, "Score: " + score);

        if (!isRunning) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(board[0].length / 2.0, board.length / 2.0, "GAME OVER");
        }
    }

    public static void main(String[] args) {
        MyPacmanGame game = new MyPacmanGame("level1.txt");
        game.play();
    }
}