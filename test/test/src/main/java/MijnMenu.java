import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import com.almasb.fxgl.audio.Music;

public class MijnMenu extends FXGLMenu {
  public MijnMenu(@NotNull MenuType type) {
    super(type);

    Button button = new Button("Speel de game!");
    Button buttonExit = new Button("Sluit de game af.");
    BorderPane pane = new BorderPane();
    Image afbeelding = new Image("assets/markRutte.png");
    Label label = new Label("Belastingsontwijkers");
    label.setTextFill(Color.VIOLET);
    label.setStyle("-fx-font-size: 70px; color: BLUE;");
    FXGL.getSettings().setGlobalMusicVolume(1);
    Music muziek = FXGL.getAssetLoader().loadMusic("Sywert.mp3");
    FXGL.getAudioPlayer().loopMusic(muziek);


    VBox vBox = new VBox(5);
    VBox gameTitleAndButtonsBox = new VBox(5);
    gameTitleAndButtonsBox.getChildren().add(vBox);
    ImageView iv = new ImageView(afbeelding);
    iv.setFitHeight(260);
    iv.setFitWidth(380);

    vBox.getChildren().addAll(iv, label, button, buttonExit);
    pane.setMinHeight(FXGL.getAppHeight());
    pane.setMinWidth(FXGL.getAppWidth());
    pane.setCenter(vBox);
    gameTitleAndButtonsBox.setAlignment(Pos.CENTER);
    vBox.setAlignment(Pos.CENTER);



    button.setOnAction(e->{
      fireNewGame();
      FXGL.getAudioPlayer().stopMusic(muziek);
    });

    buttonExit.setOnAction(e->{
      fireExit();
    });

    BackgroundImage backgroundImage = new BackgroundImage(new Image("assets/denHaag.jpg", FXGL.getAppWidth(), FXGL.getAppHeight(), false, false),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

    getContentRoot().setBackground(new Background(backgroundImage));

    getContentRoot().getChildren().addAll(pane);


  }



}

