package de.fhbielefeld.pmdungeon.vorgaben.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector3;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("TestCase: DungeonCamera")
public class DungeonCameraTest {
    private final float vw = 42;
    private final float vh = 69;

    @BeforeEach
    void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        HeadlessApplication headlessApplication = new HeadlessApplication(new Game() {
            @Override
            public void create() {
            }
        }, config);
    }

    //-----------constructor--------------
    @DisplayName("normal constructor")
    @Test
    void testConstructor() {
        IDrawable drawable = mock(IDrawable.class);
        DungeonCamera camera = new DungeonCamera(drawable, vw, vh);

        assertEquals(camera.viewportWidth, vw, "camera viewportWidth must be the same as the passed one");
        assertEquals(camera.viewportHeight, vh, "camera viewportHeight must be the same as the passed one");
        assertEquals(camera.getFollowedObject(), drawable, "followed IDrawable must be the same as the passed one");
    }

    @DisplayName("constructor without IDrawable")
    @Test
    void testConstructorWithNullDrawable() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);

        assertEquals(camera.viewportWidth, vw, "camera viewportWidth must be the same as the passed one");
        assertEquals(camera.viewportHeight, vh, "camera viewportHeight must be the same as the passed one");
        assertNull(camera.getFollowedObject(), "followed must be null");
    }

    //-----------follow+getFollowedObject--------------
    @DisplayName("camera: follow + getFollowedObject")
    @Test
    void testFollowAndGetFollowed() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        IDrawable toFollow = mock(IDrawable.class);

        camera.follow(toFollow);

        assertEquals(camera.getFollowedObject(), toFollow, "set followed object must be returned");
    }

    //-----------setFocusPoint--------------
    @DisplayName("set focus point")
    @Test
    void testSetFocusPoint() throws IllegalAccessException, NoSuchFieldException {
        Field focusField = DungeonCamera.class.getDeclaredField("focusPoint");
        focusField.setAccessible(true);
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        Point point = new Point(vw, vh);

        camera.setFocusPoint(point);

        assertNull(camera.getFollowedObject(), "followed object must be null");
        assertEquals(focusField.get(camera), point, "followed point must be equal");
    }

    //-----------update--------------
    @DisplayName("update camera: follows, focusPoint")
    @Test
    void testUpdateWithParams() {
        // define mock with return value for position
        IDrawable drawable = mock(IDrawable.class);
        when(drawable.getPosition()).thenReturn(new Point(-vw, -vh));
        DungeonCamera camera = new DungeonCamera(drawable, 0, 0);

        camera.update();

        assertEquals(camera.position.x, -vw, "x position must be the same");
        assertEquals(camera.position.y, -vh, "y position must be the same");
        assertEquals(camera.position.z, 0, "z position must be 0"); // z immer 0
    }

    @DisplayName("update camera: all null")
    @Test
    void testUpdateWitAllNull() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        assertNull(camera.getFollowedObject());
        assertEquals(camera.position, new Vector3(0,0,0));

        camera.update();

        assertEquals(camera.position.x, 0, "x position must be 0");
        assertEquals(camera.position.y, 0, "y position must be 0");
        assertEquals(camera.position.z, 0, "z position must be 0");
    }

    @DisplayName("update camera: follows null, focusPoint exists")
    @Test
    void testUpdateWithFollowNullFocusNotNull() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        camera.setFocusPoint(new Point(vw, vh));
        assertEquals(camera.position.x, 0);
        assertEquals(camera.position.y, 0);
        assertEquals(camera.position.z, 0);

        camera.update();

        assertEquals(camera.position.x, vw, "x position must be the same");
        assertEquals(camera.position.y, vh, "x position must be the same");
        assertEquals(camera.position.z, 0, "z position must be 0");
    }

}