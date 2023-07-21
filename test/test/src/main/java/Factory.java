
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Factory implements EntityFactory{


    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(6, 8)));

        // this avoids player sticking to walls
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(new Point2D(5,5), BoundingShape.circle(12)))
                .bbox(new HitBox(new Point2D(10,25), BoundingShape.box(10, 17)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("CoinPlus")
    public Entity newCoinPlus(SpawnData data) {
        return entityBuilder(data)
                .at(400, 400)
                .viewWithBBox("witte_envelop_1.png")
                .with(new CollidableComponent(true))
                .type(EntityType.COIN_PLUS)
                .build();
    }

    @Spawns("CoinMin")
    public Entity newCoinMin(SpawnData data) {
        return entityBuilder(data)
                .at(400, 400)
                .viewWithBBox("blauwe_envelop_1.png")
                .with(new CollidableComponent(true))
                .type(EntityType.COIN_MIN)
                .build();
    }

    @Spawns("Platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("StartingPoint")
    public Entity newStartingPoint(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.STARTING_POINT)
                .build();
    }
    @Spawns("FinishPoint")
    public Entity newFinishPoint(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.FINISH_POINT)
                .viewWithBBox(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.GRAY))
                .with(new CollidableComponent(true))
                .build();
    }
}