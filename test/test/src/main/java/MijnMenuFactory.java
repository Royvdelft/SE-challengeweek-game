import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;

public class MijnMenuFactory extends SceneFactory {

  @Override
  public FXGLMenu newMainMenu(){
    return new MijnMenu(MenuType.MAIN_MENU);
  }

//    @Override
//    public FXGLMenu newGameMenu(){
//        return new GameMenu(MenuType.GAME_MENU);
//    }
}
