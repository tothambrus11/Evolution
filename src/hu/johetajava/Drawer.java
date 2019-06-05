package hu.johetajava;

import processing.core.PApplet;

import static hu.johetajava.App.world;

public class Drawer extends PApplet {

    static final String title = "Processing...";
    static Colors colors;

    public void setup() {
        surface.setTitle(title);
    }

    public void settings() {
        App.drawer = this;

        size(world.width, world.height);
        colors = new Colors("dark");
    }

    public void draw() {
        background(0, 0, 0, 1);

        fill(255, 255, 255);
        circle(width - 80, height / 2.0f, 20);
        if (world.tick < world.lifetime && !world.areAllDead()) {
            App.onTick();
        } else {
            App.inTheEndOfTheWorld();
        }
    }

    public void displayEntity(Entity entity) {
        if (entity.isAlive) {
            fill(51, 118, 165, 200);
        } else {
            fill(137, 30, 57, 200);
        }

        noStroke();

        if (entity.score == world.generation.get(0).score) {
            fill(30, 200, 10);
        }
        circle(entity.position.x, entity.position.y, entity.extent);

        stroke(0);
        strokeWeight(0.5f);
        line(
                entity.position.x,
                entity.position.y,
                entity.position.x + cos(radians(entity.position.dir)) * entity.extent / 2,
                entity.position.y + sin(radians(entity.position.dir)) * entity.extent / 2
        );
        if (entity.isAlive) {
            fill(137, 30, 57, 200);

            //text(entity.getScore(), entity.position.x, entity.position.y);
        } else {
            fill(51, 118, 165, 200);

            //text(entity.score, entity.position.x, entity.position.y);
        }


        String message = "Best entity: ";
        message += "\n tick: " + world.generation.get(0).ticks;
        message += "\n distance: " + Position.getDistance(world.generation.get(0).position, new Position(width - 60, height/2.0f));

        strokeWeight(0.1f);
        textSize(14);
        fill(51, 118, 165, 200);
        text(message, width - 150, 30);
    }

    public void drawObstacle(Entity obstacle) {
        fill(255);
        stroke(255);
        strokeWeight(0.5f);
        circle(obstacle.position.x, obstacle.position.y, obstacle.extent);
    }
}
