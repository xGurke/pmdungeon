package de.fhbielefeld.pmdungeon.vorgaben.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector3;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("TestCase: DungeonCamera")
public class DungeonCameraTest {
    private HeadlessApplication headlessApplication;
    private final float vw = 42;
    private final float vh = 69;

    @BeforeEach
    void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        headlessApplication = new HeadlessApplication(new Game() {
            @Override
            public void create() { }
        }, config);
    }

    @AfterEach
    void tearDown() {
        headlessApplication.exit();
    }

    //-----------constructor--------------
    @DisplayName("normal constructor")
    @Test
    void normalConstructor() {
        IDrawable drawable = mock(IDrawable.class);
        DungeonCamera camera = new DungeonCamera(drawable, vw, vh);

        assertEquals(camera.viewportWidth, vw);
        assertEquals(camera.viewportHeight, vh);
        assertEquals(camera.getFollowedObject(), drawable);
    }

    @DisplayName("constructor without IDrawable")
    @Test
    void constructorWithNullDrawable() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);

        assertEquals(camera.viewportWidth, vw);
        assertEquals(camera.viewportHeight, vh);
        assertNull(camera.getFollowedObject());
    }

    //-----------removeEntity+getFollowed--------------
    @DisplayName("camera follow + get Followed")
    @Test
    void followAndGetFollowed() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        IDrawable toFollow = mock(IDrawable.class);

        camera.follow(toFollow);

        assertEquals(camera.getFollowedObject(), toFollow);
    }

    //-----------setFocusPoint--------------
    @DisplayName("set focus point")
    @Test
    void setFocusPoint() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        Point point = new Point(vw, vh);

        camera.setFocusPoint(point);

        assertNull(camera.getFollowedObject());
        // assertEquals(camera.focusPoint); ist private --> maybe DungeonCamera mocken?
    }

    //-----------update--------------
    @DisplayName("update camera with all params")
    @Test
    void updateWithParams() {
        // define mock with return value
        IDrawable drawable = mock(IDrawable.class);
        when(drawable.getPosition()).thenReturn(new Point(-vw, -vh));
        DungeonCamera camera = new DungeonCamera(drawable, 0, 0);

        camera.update();

        assertEquals(camera.position.x, -vw);
        assertEquals(camera.position.y, -vh);
        assertEquals(camera.position.z, 0); // z immer 0
    }

    @DisplayName("update camera follows, focus being null")
    @Test
    void updateWitAllNull() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        assertNull(camera.getFollowedObject());
        assertEquals(camera.position, new Vector3(0,0,0));

        camera.update();

        assertEquals(camera.position.x, 0);
        assertEquals(camera.position.y, 0);
        assertEquals(camera.position.z, 0);
    }

    @DisplayName("update camera with follows being null and focus not being null")
    @Test
    void updateWithFollowNullFocusNotNull() {
        DungeonCamera camera = new DungeonCamera(null, vw, vh);
        camera.setFocusPoint(new Point(vw, vh));
        assertEquals(camera.position.x, 0);
        assertEquals(camera.position.y, 0);
        assertEquals(camera.position.z, 0);

        camera.update();

        assertEquals(camera.position.x, vw);
        assertEquals(camera.position.y, vh);
        assertEquals(camera.position.z, 0);
    }

}