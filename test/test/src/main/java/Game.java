import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {

    private static final int MAX_LEVEL = 4;
    private static final int STARTING_LEVEL = 0;
    private boolean level2 = false;
    private boolean level3 = false;

    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(32 * 32);
        gameSettings.setHeight(32 * 32);
        gameSettings.setTitle("Belasting ontwijker");
        gameSettings.setVersion("1.0");
        gameSettings.setSceneFactory(new MijnMenuFactory());
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setDeveloperMenuEnabled(false);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());
        nextLevel();

        player = spawn("player", 64, 832);

        set("player", player);

        Viewport viewport = getGameScene().getViewport();
        viewport.setZoom(2);
        viewport.bindToEntity(player, getAppWidth() / 2d, getAppHeight() / 2d);
        viewport.setBounds(0, 0, getAppWidth(), getAppHeight());
        viewport.setLazy(true);

        Button button = new Button("start");
        button.minWidth(300);
        button.minHeight(200);
        button.setTranslateY(200);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", STARTING_LEVEL);
        vars.put("levelTime", 0.0);
        vars.put("geld", 0);
    }

    @Override
    protected void initUI() {
        Label myText = new Label();
        myText.setStyle("-fx-font-size: 24px; -fx-text-fill: #ffffff;");
        myText.setTranslateX(30);
        myText.setTranslateY(5);
        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("geld").asString());

        FXGL.getGameScene().addUINode(myText);
    }

    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN_PLUS ) {
            @Override
            protected void onCollision(Entity player, Entity coin) {
                inc("geld", +111);
                coin.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN_MIN ) {
            @Override
            protected void onCollision(Entity player, Entity coin) {
                inc("geld", -250);
                coin.removeFromWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.FINISH_POINT) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                nextLevel();
            }
        });

        getPhysicsWorld().setGravity(0, 760);
    }

    public void onPlayerDied() {
        setLevel(geti("level"));
    }

    public void nextLevel() {
        if (geti("level") == MAX_LEVEL) {
            showMessage("Gefeliciteerd, Je hebt alle levels gespeeld!\n" + "Je hebt " + geti("geld") + " euro verdiend!");
            return;
        }

        inc("level", +1);
        setLevel(geti("level"));
    }

    protected void setLevel(int levelNum) {
        if (player != null) {
            player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(64, 864));
            player.setZIndex(Integer.MAX_VALUE);
        }
        Level level = setLevelFromMap("tmx/Lv" + levelNum + ".tmx");
    }

    protected void initInput(){
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveLeft();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stopPLayer();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveRight();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stopPLayer();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jumpPLayer();
            }
        }, KeyCode.W, VirtualButton.A);
    }

    public static void main(String args[]){
        launch(args);
    }

}
