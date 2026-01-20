package assignments.Ex3;

import java.io.File;
import java.util.Scanner;

public class MapLoader {
    public static int[][] loadMap(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int rows = sc.nextInt();
            int cols = sc.nextInt();
            int[][] map = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    map[i][j] = sc.nextInt();
                }
            }
            sc.close();
            return map;
        } catch (Exception e) {
            // לוח ברירת מחדל
            return new int[][]{
                    {1, 1, 1, 1, 1, 1, 1},
                    {1, 3, 3, 3, 3, 3, 1},
                    {1, 3, 1, 5, 1, 3, 1},
                    {1, 3, 3, 3, 3, 3, 1},
                    {1, 1, 1, 1, 1, 1, 1}
            };
        }
    }
}