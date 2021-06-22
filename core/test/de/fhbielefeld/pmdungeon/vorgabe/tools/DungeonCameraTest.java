package de.fhbielefeld.pmdungeon.vorgabe.tools;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;
import de.fhbielefeld.pmdungeon.vorgaben.tools.DungeonCamera;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TestCase: DungeonCamera")
public class DungeonCameraTest {

    @DisplayName("normal constructor")
    @Test
    void normalConstructor() {
        float vw = Constants.VIRTUALHEIGHT * Constants.WIDTH / (float) Constants.HEIGHT;
        float vh = Constants.VIRTUALHEIGHT;
        IDrawable drawable = mock(IDrawable.class);

        // OrthographicCamera test = new OrthographicCamera(50, 50);

        // from setupCamera()
        DungeonCamera camera = new DungeonCamera(drawable, vw, vw);
        camera.position.set(0, 0, 0);
        camera.zoom += 1;
        camera.update();

        assertEquals(camera.viewportWidth, vw);
        assertEquals(camera.viewportHeight, vh);
        assertEquals(camera.getFollowedObject(), drawable);
    }

    @DisplayName("constructor without IDrawable")
    @Test
    void constructorWithNullDrawable() {
        float vw = Constants.VIRTUALHEIGHT * Constants.WIDTH / (float) Constants.HEIGHT;
        float vh = Constants.VIRTUALHEIGHT;

        /* DungeonCamera camera = new DungeonCamera(null, vw, vw);
        camera.position.set(0, 0, 0);
        camera.zoom += 1;
        camera.update();

        assertEquals(camera.viewportWidth, vw);
        assertEquals(camera.viewportHeight, vh);
        assertNull(camera.getFollowedObject());
         */
    }

}
