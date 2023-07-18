import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import org.jetbrains.annotations.NotNull;

public class MijnMenuFactory extends SceneFactory {

  @NotNull
  @Override
  public FXGLMenu newMainMenu() {
    return new MijnMenu(MenuType.MAIN_MENU);
  }
}
