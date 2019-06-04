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

        world = new World(40, 10000, 1600, 900, 7);
        PApplet.main(Drawer.class, args);
    }

    public static void setup(){

    }

    public static void onTick() {
        for(Entity obstacle : world.obstacles){
            drawer.drawObstacle(obstacle);
        }

        for (Entity entity : world.generation) {
            drawer.displayEntity(entity);
        }

        for (Entity entity : world.generation) {
            if(entity.isAlive){
                entity.liveTick();
            }
        }

        ArrayList<Entity> entitiesToRemove = new ArrayList<>();
        for(Entity entity : world.generation){
            if(NaturalSelection.isDead(entity)){
                entity.die();
            }
        }

        world.tick++;

        if (world.tick > world.lifetime) System.exit(200);
    }

     static float random(float high) {
        if (high != 0.0F && high == high) {

            float value = 0.0F;

            do {
                value = random.nextFloat() * high;
            } while(value == high);

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
            } while(value == high);

            return value;
        }
    }
}

