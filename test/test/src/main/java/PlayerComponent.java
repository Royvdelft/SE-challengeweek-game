

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    private AnimatedTexture texture;

    private AnimationChannel animIdle, animWalk;

    private int jumps = 2;

    public PlayerComponent() {
        animIdle = new AnimationChannel(FXGL.image("idle.png"), 11, 78, 58, Duration.seconds(1.1), 0, 10);
        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(39, 29));
        entity.getViewComponent().addChild(texture);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 2;
            }
        });
    }

    public void moveLeft() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-170);
    }

    public void moveRight() {
        getEntity().setScaleX(1);
        physics.setVelocityX(170);
    }

    public void stopPLayer() {
        physics.setVelocityX(0);
    }

    public void jumpPLayer() {
        if (jumps == 0)
            return;

        physics.setVelocityY(-400);

        jumps--;
    }
}
