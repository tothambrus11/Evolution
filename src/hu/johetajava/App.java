package hu.johetajava;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class App {
    static Drawer drawer;
    static World world;


    static Random random;

    public static void main(String[] args) {
        random = new Random();

        world = new World(40, 400, 1600, 900, 7);
        PApplet.main(Drawer.class, args);
    }

    public static void setup() {

    }

    public static void onTick() {
        for (Entity obstacle : world.obstacles) {
            drawer.drawObstacle(obstacle);
        }

        for (Entity entity : world.generation) {
            drawer.displayEntity(entity);
        }

        for (Entity entity : world.generation) {
            if (entity.isAlive) {
                entity.liveTick();
            }
        }

        ArrayList<Entity> entitiesToRemove = new ArrayList<>();
        for (Entity entity : world.generation) {
            if(entity.isAlive) {
                if (NaturalSelection.isDead(entity)) {
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

        world.generation.sort((o1, o2) -> (int) ((o2.score - o1.score) * 1000000));
        for(Entity entity : world.generation){
            System.out.println(entity.score);
        }

        // A legjobb 5% átöröklődök
        for(int i = 0; i <= world.generation.size()* 0.05; i++){
            nextGeneration.add(world.generation.get(i));
        }

        int totalWeight = 0;
        for (Entity entity : world.generation) {
            totalWeight += entity.score;
        }

        while (nextGeneration.size() < world.generation.size()){
            // Compute the total weight of all items together

            // Now choose a random item
            Entity randomEntity = null;

            double random = Math.random() * totalWeight;
            for (Entity entity : world.generation)
            {
                random -= entity.score;
                if (random <= 0.0)
                {
                    randomEntity = entity;
                    break;
                }
            }

            if (randomEntity != null) {
                nextGeneration.add(randomEntity.clone_().mutate());
            }
            else{
                System.err.println("Rossz a random");
            }
        }
        System.exit(0);
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

