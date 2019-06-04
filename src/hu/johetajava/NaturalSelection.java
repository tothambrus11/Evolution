package hu.johetajava;

public class NaturalSelection {

    public static boolean isDead(Entity entity){
        return entity.position.x < 0 ||
                entity.position.y < 0 ||
                entity.position.x > App.world.width ||
                entity.position.y > App.world.height ||
                collidedWithObstacles(entity);
    }

    private static boolean collidedWithObstacles(Entity entity) {
        for(Entity obstacle : App.world.obstacles){
            if(obstacle.getDistance(entity) < (obstacle.extent / 2 )+ (entity.extent / 2)){
                return true;
            }
        }
        return false;
    }

}
