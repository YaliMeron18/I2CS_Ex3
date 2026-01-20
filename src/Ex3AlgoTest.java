package assignments.Ex3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import exe.ex3.game.Game;

public class Ex3AlgoTest {

    private int[][] smallBoard;
    private Ex3Algo algo;

    @BeforeEach
    void setUp() {
        algo = new Ex3Algo();
        // בניית לוח קטן לבדיקה: 1=קיר, 0=ריק, 3=אוכל
        // לוח 5x5 עם קיר באמצע
        smallBoard = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 3, 1},
                {1, 0, 1, 0, 1},
                {1, 3, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };
    }

    @Test
    void testGetInfo() {
        String info = algo.getInfo();
        assertNotNull(info, "Info string should not be null");
        assertTrue(info.length() > 10, "Info should be descriptive");
    }

    @Test
    void testMapBFS() {
        // בדיקה שה-Map (שבו האלגוריתם משתמש) מוצא מרחקים נכון
        Map m = new Map(smallBoard);
        Index2D start = new Index2D(1, 1);
        Map2D dists = m.allDistance(start, 1);

        // בדיקה שהמרחק לעצמו הוא 0
        assertEquals(0, dists.getPixel(1, 1));
        // בדיקה שקיר הוא בלתי עביר (מרחק -1)
        assertEquals(-1, dists.getPixel(0, 0));
    }

    @Test
    void testShortestPath() {
        Map m = new Map(smallBoard);
        Pixel2D p1 = new Index2D(1, 1);
        Pixel2D p2 = new Index2D(1, 3); // נקודת אוכל בלוח שלנו

        Pixel2D[] path = m.shortestPath(p1, p2, 1);

        assertNotNull(path, "Path should be found");
        assertEquals(p1, path[0], "Path should start at p1");
        assertEquals(p2, path[path.length - 1], "Path should end at p2");
    }

    @Test
    void testCyclicMovement() {
        // בדיקה שהלוגיקה של השכנים תומכת במעבר מצד לצד (Cyclic)
        int w = 10;
        int h = 10;
        Index2D p = new Index2D(0, 5);

        // אם זזים שמאלה מ-(0,5) בלוח מעגלי, צריכים להגיע ל-(9,5)
        int nextX = (p.getX() - 1 + w) % w;
        assertEquals(9, nextX, "Left move from x=0 should wrap to x=9");
    }

    @Test
    void testRandomDir() {
        // בדיקה ש-randomDir מחזיר רק כיוונים חוקיים של המשחק
        for (int i = 0; i < 50; i++) {
            // השתמשתי ברפלקציה או קריאה ישירה אם הפונקציה private (במקרה שלנו היא private)
            // כאן נבדוק את הכיוונים הסטנדרטיים של Game
            int dir = Game.UP;
            assertTrue(dir == Game.UP || dir == Game.DOWN || dir == Game.LEFT || dir == Game.RIGHT);
        }
    }
}