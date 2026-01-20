package assignments.Ex3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a 2D map as a raster matrix.
 * Adapted from Ex2 to meet Ex3 Interface requirements.
 */
public class Map implements Map2D {
    private int[][] _map;
    private boolean _cyclicFlag = true;

    public Map(int w, int h, int v) {
        init(w, h, v);
    }

    public Map(int size) {
        this(size, size, 0);
    }

    public Map(int[][] data) {
        init(data);
    }

    @Override
    public void init(int w, int h, int v) {
        _map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                _map[i][j] = v;
            }
        }
    }

    @Override
    public void init(int[][] arr) {
        if (arr == null) return;
        int w = arr.length;
        int h = arr[0].length;
        _map = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                _map[i][j] = arr[i][j];
            }
        }
    }

    @Override
    public int[][] getMap() {
        return _map;
    }

    @Override
    public int getWidth() {
        return _map.length;
    }

    @Override
    public int getHeight() {
        return _map[0].length;
    }

    @Override
    public int getPixel(int x, int y) {
        return _map[x][y];
    }

    @Override
    public int getPixel(Pixel2D p) {
        return this.getPixel(p.getX(), p.getY());
    }

    @Override
    public void setPixel(int x, int y, int v) {
        _map[x][y] = v;
    }

    @Override
    public void setPixel(Pixel2D p, int v) {
        this.setPixel(p.getX(), p.getY(), v);
    }

    @Override
    public boolean isInside(Pixel2D p) {
        int x = p.getX();
        int y = p.getY();
        return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
    }

    @Override
    public boolean isCyclic() {
        return _cyclicFlag;
    }

    @Override
    public void setCyclic(boolean cy) {
        _cyclicFlag = cy;
    }

    @Override
    public int fill(Pixel2D xy, int new_v) {
        int old_v = getPixel(xy);
        if (old_v == new_v) return 0;

        int count = 0;
        Queue<Pixel2D> q = new LinkedList<>();
        q.add(xy);
        setPixel(xy, new_v);
        count++;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Pixel2D curr = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = curr.getX() + dx[i];
                int ny = curr.getY() + dy[i];

                if (_cyclicFlag) {
                    nx = (nx + getWidth()) % getWidth();
                    ny = (ny + getHeight()) % getHeight();
                }

                Index2D next = new Index2D(nx, ny);
                if (isInside(next) && getPixel(next) == old_v) {
                    setPixel(next, new_v);
                    q.add(next);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Map2D allDistance(Pixel2D start, int obsColor) {
        Map ans = new Map(getWidth(), getHeight(), -1);
        if (!isInside(start) || getPixel(start) == obsColor) return ans;

        Queue<Pixel2D> q = new LinkedList<>();
        ans.setPixel(start, 0);
        q.add(start);

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Pixel2D curr = q.poll();
            int d = ans.getPixel(curr);

            for (int i = 0; i < 4; i++) {
                int nx = curr.getX() + dx[i];
                int ny = curr.getY() + dy[i];

                if (_cyclicFlag) {
                    nx = (nx + getWidth()) % getWidth();
                    ny = (ny + getHeight()) % getHeight();
                }

                Index2D next = new Index2D(nx, ny);
                if (isInside(next) && getPixel(next) != obsColor && ans.getPixel(next) == -1) {
                    ans.setPixel(next, d + 1);
                    q.add(next);
                }
            }
        }
        return ans;
    }

    @Override
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) {
        Map2D dMap = allDistance(p1, obsColor);
        int dist = dMap.getPixel(p2);

        if (dist == -1) return null; // אין מסלול

        Pixel2D[] path = new Pixel2D[dist + 1];
        path[dist] = p2;
        Pixel2D curr = p2;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int d = dist - 1; d >= 0; d--) {
            for (int i = 0; i < 4; i++) {
                int nx = curr.getX() + dx[i];
                int ny = curr.getY() + dy[i];

                if (_cyclicFlag) {
                    nx = (nx + getWidth()) % getWidth();
                    ny = (ny + getHeight()) % getHeight();
                }

                Index2D next = new Index2D(nx, ny);
                if (isInside(next) && dMap.getPixel(next) == d) {
                    path[d] = next;
                    curr = next;
                    break;
                }
            }
        }
        return path;
    }
}