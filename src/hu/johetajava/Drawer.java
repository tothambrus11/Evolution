package hu.johetajava;

import processing.core.PApplet;

import java.util.ArrayList;

public class Drawer extends PApplet {

    static final String title = "Processing...";
    static Colors colors;

    public void setup() {
        App.setup();
        surface.setTitle(title);
    }

    public void settings() {
        App.drawer = this;

        size(App.world.width, App.world.height);
        colors = new Colors("dark");
    }

    public void draw() {
        background(0, 0, 0, 1);

        App.onTick();
    }

    public void displayEntity(Entity entity) {
        if (entity.isAlive) {
            fill(51, 118, 165, 200);
        } else {
            fill(137, 30, 57, 200);
        }

        noStroke();
        circle(entity.position.x, entity.position.y, entity.extent);

        stroke(0);
        strokeWeight(2);
        line(
                entity.position.x,
                entity.position.y,
                entity.position.x + cos(radians(entity.position.dir)) * entity.extent / 2,
                entity.position.y + sin(radians(entity.position.dir)) * entity.extent / 2
        );
    }

    public void drawObstacle(Entity obstacle) {
        fill(255);
        stroke(255);
        strokeWeight(0.5f);
        circle(obstacle.position.x, obstacle.position.y, obstacle.extent);
    }
}
