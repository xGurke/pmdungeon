package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.HUD;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.TextStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;

@DisplayName("TestCase: MainController")
public class MainControllerTest {
    protected HeadlessApplication headlessApplication;
    private MainController mainController;

    @BeforeEach
    void setUp() {
        mainController = new MainController();

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        headlessApplication = new HeadlessApplication(new Game() {
            @Override
            public void create() { }
        }, config);

        GameSetup.batch = mock(SpriteBatch.class);
        Gdx.gl = mock(GL20.class);
    }

    //-----------firstFrame--------------
    // für mockedConstructions siehe: https://javadoc.io/static/org.mockito/mockito-core/3.11.2/org/mockito/Mockito.html#mocked_construction
    @DisplayName("firstFrame: finishedSetup: false")
    @Test
    void testFirstFrameWithSetup() throws NoSuchFieldException, IllegalAccessException {
        try (
                MockedConstruction<HUD> hudMocked = mockConstruction(HUD.class);
                MockedConstruction<TextStage> textStageMocked = mockConstruction(TextStage.class);
        ) {
            Field finishedField = MainController.class.getDeclaredField("finishedSetup");
            finishedField.setAccessible(true);

            assertNull(mainController.entityController);
            assertNull(mainController.camera);
            assertNull(mainController.levelController);
            assertNull(mainController.hud);
            assertNull(mainController.textHUD);
            assertFalse(finishedField.getBoolean(mainController));
            assertTrue(mainController.firstFrame); // firstFrame never called

            // render() calls private firstFrameSetup()
            mainController.render(0); // delta param is irrelevant

            assertNotNull(mainController.entityController, "entityController must be initialised");
            assertNotNull(mainController.camera, "camera must be initialised");
            assertNotNull(mainController.levelController, "levelController must be initialised");
            assertNotNull(mainController.hud, "hud must be initialised");
            assertNotNull(mainController.textHUD, "textHUD must be initialised");
            assertTrue(finishedField.getBoolean(mainController), "finished setup must be true");
            assertFalse(mainController.firstFrame, "first frame must be false after firstFrame()"); // first frame was called
        }
    }

    @DisplayName("firstFrame: finishedSetup true")
    @Test
    void testFirstFrameWithoutSetup() throws NoSuchFieldException, IllegalAccessException {
        try (
                MockedConstruction<HUD> hudMocked = mockConstruction(HUD.class);
                MockedConstruction<TextStage> textStageMocked = mockConstruction(TextStage.class);
        ) {
            // Set finishedSetup to true --> firstFrame(): initialisation of field should be ignored
            Field finishedField = MainController.class.getDeclaredField("finishedSetup");
            finishedField.setAccessible(true);
            Field firstField = MainController.class.getDeclaredField("firstFrame");
            finishedField.setAccessible(true);
            // setup everything
            mainController.render(0); // delta param is irrelevant
            finishedField.setBoolean(mainController, true); // needed for re-invoke
            firstField.setBoolean(mainController, true); // needed for re-invoke

            DungeonWorld dungeonWorld = mainController.levelController.getDungeon();
            assertTrue(finishedField.getBoolean(mainController));
            assertNotNull(dungeonWorld);

            // call firstFrame() again with finishedSetup false; should create new Dungeon
            mainController.render(0); // delta param is irrelevant

            assertTrue(finishedField.getBoolean(mainController), "finished setup must be true");
            assertNotEquals(dungeonWorld, mainController.levelController.getDungeon(), "New dungeon should not be the same as the old one");
            assertFalse(firstField.getBoolean(mainController), "first frame must be false after firstFrame()");
        }
    }

    //-----------setUpWorldController--------------
    @DisplayName("setup LevelController")
    @Test
    void testSetUpWorldController() {
        try (
                MockedConstruction<HUD> hudMocked = mockConstruction(HUD.class);
                MockedConstruction<TextStage> textStageMocked = mockConstruction(TextStage.class)
        ) {
         assertNull(mainController.levelController);

         // render() calls private setupWorldController()
         mainController.render(0); // delta param is irrelevant

         assertNotNull(mainController.levelController, "levelController must be initialised");
        }
    }

    //-----------setupCamera--------------
    @DisplayName("setup camera")
    @Test
    void testSetupCamera() {
        try (
                MockedConstruction<HUD> hudMocked = mockConstruction(HUD.class);
                MockedConstruction<TextStage> textStageMocked = mockConstruction(TextStage.class)
        ) {
            assertNull(mainController.camera);

            // render() calls private setupCamera()
            mainController.render(0); // delta param is irrelevant

            assertNotNull(mainController.camera, "camera must be initialised");
            assertEquals(mainController.camera.position, new Vector3(0,0,0), "camera must have initial position of x=0, y=0, z=0");
        }
    }


}
