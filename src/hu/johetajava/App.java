package hu.johetajava;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;


public class App {
    static Drawer drawer;
    static World world;


    static Random random;

    public static void main(String[] args) {
        random = new Random();

        world = new World(400, 2000, 900, 600, 3);
        PApplet.main(Drawer.class, args);
    }


    public static void onTick() {
        for (Entity obstacle : world.obstacles) {
            drawer.drawObstacle(obstacle);
        }

        ArrayList<Entity> generation = world.generation;
        for (int i = generation.size() - 1; i >= 0; i--) {
            Entity entity = generation.get(i);
            drawer.displayEntity(entity);
        }

        for (Entity entity : world.generation) {
            if (entity.isAlive) {
                entity.liveTick();
            }
        }

        for (Entity entity : world.generation) {
            if (entity.isAlive) {
                if (NaturalSelection.isDead(entity)) {
                    entity.die();
                }
            }
        }

        for (Entity entity : world.generation) {
            if (entity.isAlive) {
                if (Position.getDistance(entity.position, new Position(drawer.width - 60, drawer.height / 2.0f)) < 20 + entity.extent){
                    entity.die();
                }
            }
        }

        world.tick++;

    }

    static float random(float high) {
        if (high != 0.0F && high == high) {

            float value = 0.0F;

            do {
                value = random.nextFloat() * high;
            } while (value == high);

            return value;
        } else {
            return 0.0F;
        }
    }

    static float random(float low, float high) {
        if (low >= high) {
            return low;
        } else {
            float diff = high - low;
            float value = 0.0F;

            do {
                value = random(diff) + low;
            } while (value == high);

            return value;
        }
    }

    public static void nextGeneration() {

        ArrayList<Entity> nextGeneration = new ArrayList<>();

        // SORTING
        world.generation.sort((o1, o2) -> (int) ((o2.score - o1.score) * 1000000));

        // DISPLAYING SCORES
        for (int i = 0; i < world.generation.size(); i++) {
            Entity entity = world.generation.get(i);
            //System.out.println(i + ". : " + entity.score);
        }

        System.out.println("BEST SCORE: " + world.generation.get(0).score);
        System.out.println("SECOND BEST SCORE: " + world.generation.get(1).score);


        drawer.delay(200);
        // COPY the best entities
        for (int i = 0; i < world.generation.size() * 0.2; i++) {
            //System.out.println("beee   " + i);
            nextGeneration.add(world.generation.get(i));

        }

        System.out.println("HELLLLLLLLO ========");
        System.out.println(world.generation.get(0));

        System.out.println(world.generation.get(0));

        // FILL the remaining space
        int i = 0;

        float totalWeight = 0;
        for (Entity entity : world.generation) {
            totalWeight += entity.score;
        }

        while (nextGeneration.size() < world.generation.size()) {
            int randomIndex = -1;
            double random = Math.random() * totalWeight;
            for (int j = 0; j < world.generation.size(); ++j) {
                random -= world.generation.get(j).score;

                if (random <= 0.0d) {
                    randomIndex = i;
                    break;
                }
            }
            Entity randomEntity = world.generation.get(randomIndex).clone_();

            randomEntity.mutate();

            nextGeneration.add(randomEntity);

            i++;
        }

        // RESET
        for (Entity entity : nextGeneration) {
            entity.reset();
        }

        world.generation = nextGeneration;
        System.out.println(world.generation.get(0));
        world.generation.sort((o1, o2) -> (int) ((o2.score - o1.score) * 1000000));


        System.out.println("=========================================");
        world.tick = 0;
    }

    public static void inTheEndOfTheWorld() {
        for (Entity entity : world.generation) {
            if (entity.isAlive) {
                entity.die();
            }
        }

        App.nextGeneration();

    }
}

