package assignments.Ex3;

public class Index2D implements Pixel2D {
    private int _x;
    private int _y;
    public Index2D(int w, int h) {
        this._x = w;
        this._y = h;
    }
    public Index2D(Pixel2D other) {
        this._x = other.getX();
        this._y = other.getY();
    }


    @Override
    public int getX() {

        return this._x;
    }

    @Override
    public int getY() {

        return this._y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        return Math.sqrt(Math.pow(this._x - p2.getX(), 2) + Math.pow(this._y - p2.getY(), 2));
    }

    @Override
    public String toString() {
        return "(" + this._x + "," + this._y + ")";
    }

    @Override
    public boolean equals(Object p) {
        if(p  instanceof Index2D) {
            if(this._x==((Index2D) p).getX() && this._y==((Index2D) p).getY()) {
                return true;
            }
            else return false;
        }
        else return false;
    }
}