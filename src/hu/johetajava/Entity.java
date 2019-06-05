package hu.johetajava;

import java.util.Arrays;

import static hu.johetajava.App.drawer;
import static hu.johetajava.App.world;
import static java.lang.Math.*;

public class Entity {

    public float extent;
    public int ticks;
    Position position;
    float speed;

    float[] deltaDirs;

    float turningSpeed;
    boolean isAlive = true;
    float score;
    public float mutationRate;

    Position startPos;
    float startSpeed;
    float startTurningSpeed;

    public Entity(float extent, Position position, float speed, float[] deltaDirs, float turningSpeed, float mutationRate) {
        this.extent = extent;
        this.position = position;
        this.speed = speed;
        this.deltaDirs = deltaDirs;
        this.turningSpeed = turningSpeed;
        this.mutationRate = mutationRate;
        startPos = position.clone_();
        startSpeed = speed;
        startTurningSpeed = turningSpeed;

    }

    public void reset() {
        position.x = 100;
        position.y = 100;
        position.dir = 0;
        isAlive = true;
    }

    static float[] getRandomDeltaDirs(int tickCount) {
        float[] dirs = new float[tickCount];
        for (int tick = 0; tick < tickCount; tick++) {
            dirs[tick] = getRandomDeltaDir();
        }
        return dirs;
    }

    static float getRandomDeltaDir() {
        return App.random(-1, 1);
    }

    public static float getRandomDir() {
        return App.random(1) * 360;
    }

    void liveTick() {
        float deltaDir = deltaDirs[world.tick];
        position.dir += deltaDir * turningSpeed;
        position.dir %= 360;

        position.x += cos(toRadians(position.dir)) * speed;
        position.y += sin(toRadians(position.dir)) * speed;
    }

    public void die() {
        this.isAlive = false;
        this.score = getScore();
        ticks = world.tick;

    }

    float getScore() {
        return 10000 /( Position.getDistance(position, new Position(drawer.width - 80, drawer.height / 2.0f))*5) / world.tick;
    }

    public float getDistance(Entity entity) {
        return Position.getDistance(position, entity.position);
    }


    @Override
    public String toString() {
        return "Entity{" +
                "extent=" + extent +
                ", position=" + position +
                ", speed=" + speed +
                ", deltaDirs=" + Arrays.toString(deltaDirs) +
                ", turningSpeed=" + turningSpeed +
                ", isAlive=" + isAlive +
                ", score=" + score +
                ", mutationRate=" + mutationRate +
                '}';
    }

    public Entity clone_() {
        Entity clone = new Entity(extent, position.clone_(), speed, null, turningSpeed, mutationRate);

        clone.deltaDirs = new float[deltaDirs.length];
        for (int i = 0; i < clone.deltaDirs.length; i++) {
            clone.deltaDirs[i] = deltaDirs[i];
        }
        return clone;
    }

    public Entity mutate() {
        for (int i = 0; i < deltaDirs.length; i++) {
            deltaDirs[i] += drawer.randomGaussian() * mutationRate;
            while (deltaDirs[i] > 1) {
                deltaDirs[i]--;
            }
            while (deltaDirs[i] < -1) {
                deltaDirs[i]++;
            }
        }

        return this;
    }
}
