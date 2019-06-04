package hu.johetajava;

import static hu.johetajava.App.drawer;
import static hu.johetajava.App.world;
import static java.lang.Math.*;

public class Entity {

    public float extent;
    Position position;
    float speed;

    float[] deltaDirs;

    float turningSpeed;
    boolean isAlive = true;
    float score;

    public Entity(float extent, Position position, float speed, float[] deltaDirs, float turningSpeed) {
        this.extent = extent;
        this.position = position;
        this.speed = speed;
        this.deltaDirs = deltaDirs;
        this.turningSpeed = turningSpeed;
    }

    static float[] getRandomDeltaDirs(int tickCount){
        float[] dirs = new float[tickCount];
        for(int tick = 0; tick < tickCount; tick++){
            dirs[tick] = getRandomDeltaDir();
        }
        return dirs;
    }

    static float getRandomDeltaDir(){
        return App.random(-1, 1);
    }

    public static float getRandomDir() {
        return App.random(1)*360;
    }

    void liveTick(){
        float deltaDir = deltaDirs[world.tick];
        position.dir += deltaDir * turningSpeed;
        position.dir %= 360;

        position.x += cos(toRadians(position.dir)) * speed;
        position.y += sin(toRadians(position.dir)) * speed;
    }

    public void die() {
        this.isAlive = false;
        this.score = getScore();
    }

    float getScore() {
        return world.tick;
    }

    public float getDistance(Entity entity) {
        return Position.getDistance(position, entity.position);
    }
}