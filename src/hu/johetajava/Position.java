package hu.johetajava;

public class Position {

    float x;
    float y;
    float dir;

    public Position(float x, float y, float dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public static float getDistance(Position p1, Position p2) {
        float deltaX = p2.x - p1.x;
        float deltaY = p2.y - p1.y;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Position clone_() {
        return new Position(x, y, dir);
    }
}
