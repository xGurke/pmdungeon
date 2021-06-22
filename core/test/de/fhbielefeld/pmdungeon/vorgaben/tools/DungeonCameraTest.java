package de.fhbielefeld.pmdungeon.vorgaben.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@DisplayName("TestCase: DungeonCamera")
public class DungeonCameraTest {
    private HeadlessApplication headlessApplication;
    private final float vw = 50f;
    private final float vh = 50f;

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

}