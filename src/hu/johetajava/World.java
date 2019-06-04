package hu.johetajava;

import java.util.ArrayList;

import static hu.johetajava.App.drawer;

public class World {

    public int tick = 0;
    public int height;
    public int width;
    ArrayList<Entity> generation;

    ArrayList<Entity> obstacles;

    int lifetime = 100000;

    World(int initialPopulationSize, int lifetime, int width, int height, int obstacleCount) {
        generation = new ArrayList<>();
        obstacles = new ArrayList<>();

        this.height = height;
        this.width = width;

        this.lifetime = lifetime;

        populate(initialPopulationSize);
        createObstacles(obstacleCount);

    }

    private void createObstacles(int obstacleCount) {
        for (int i = 0; i < obstacleCount; i++) {
            obstacles.add(new Entity(80, getRandomPosition(), 0, new float[0], 0));
        }
    }

    void populate(int initialPopulationSize) {
        for (int i = 0; i < initialPopulationSize; i++) {
            generation.add(new Entity(50, getRandomPosition(), 1, Entity.getRandomDeltaDirs(lifetime), 10));
        }
    }

    Position getRandomPosition() {
        return new Position(App.random(width), App.random(height), Entity.getRandomDir());
    }

}
