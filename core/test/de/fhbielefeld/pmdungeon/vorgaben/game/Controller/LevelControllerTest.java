package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TestCase: LevelController")
public class LevelControllerTest {
    protected HeadlessApplication headlessApplication;
    private LevelController levelController;
    private boolean loadedLevel;

    /**
     * invoke method is passed to LevelController instance
     */
    public void invoke() {
        this.loadedLevel = true;
    }

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        Method tmp = this.getClass().getMethod("invoke");
        levelController = new LevelController(tmp, this, null);
        loadedLevel = false;

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        headlessApplication = new HeadlessApplication(new Game() {
            @Override
            public void create() { }
        }, config);

        GameSetup.batch = mock(SpriteBatch.class);

        Gdx.gl = mock(GL20.class);
    }

    //-----------loadDungeon--------------
    @DisplayName("load dungeon")
    @Test
    void loadDungeon() throws InvocationTargetException, IllegalAccessException {
        DungeonWorld dungeonWorld = new DungeonWorld(0, 0);
        assertNull(this.levelController.getDungeon());
        assertFalse(this.loadedLevel);

        levelController.loadDungeon(dungeonWorld);

        assertEquals(levelController.getDungeon(), dungeonWorld);
        // ignore DungeonWorld method
        assertTrue(this.loadedLevel);
    }

    //-----------update--------------
    @DisplayName("update stage not triggered")
    @Test
    void updateFalse() throws NoSuchFieldException, IllegalAccessException {
        // check nextStage var
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        // Check condition
        assertEquals(stageField.get(levelController), LevelController.Stage.A);

        levelController.update();

        assertEquals(stageField.get(levelController), LevelController.Stage.A);
    }

    @DisplayName("update stage if triggered")
    @Test
    void updateTrue() throws IllegalAccessException, NoSuchFieldException {
        // check nextStage var
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        // Check conditions
        levelController.triggerNextStage();
        assertEquals(stageField.get(levelController), LevelController.Stage.A);

        levelController.update();

        assertEquals(stageField.get(levelController), LevelController.Stage.B);
    }

    //-----------checkForTrigger--------------
    @DisplayName("check trigger with null")
    @Test
    void checkForTriggerWithNull() {
        levelController.triggerNextStage();
        levelController.update();
        assertNotNull(levelController.getDungeon());

        // ToDo: Assert Exception
        assertFalse(levelController.checkForTrigger(null));
    }

    @DisplayName("check trigger true")
    @Test
    void checkForTriggerTrue() {
        levelController.triggerNextStage();
        levelController.update();
        assertNotNull(levelController.getDungeon());
        Tile tile = levelController.getDungeon().getNextLevelTrigger();
        Point point = new Point(tile.getX(), tile.getY());

        assertTrue(levelController.checkForTrigger(point));
    }

    @DisplayName("check trigger false")
    @Test
    void checkForTriggerFalse() {
        Point point = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);  // very very unlikely trigger point
        levelController.triggerNextStage();
        levelController.update();
        assertNotNull(levelController.getDungeon());

        assertFalse(levelController.checkForTrigger(point));
    }

    //-----------triggerNextStage--------------
    @DisplayName("trigger next stage")
    @Test
    void triggerNextStage() throws NoSuchFieldException, IllegalAccessException {
        Field stageField = LevelController.class.getDeclaredField("nextLevelTriggered");
        stageField.setAccessible(true);

        levelController.triggerNextStage();

        assertTrue(stageField.getBoolean(levelController));
    }

    //-----------getDungeon--------------
    @DisplayName("getDungeon")
    @Test
    void getDungeon() throws IllegalAccessException, InvocationTargetException {
        DungeonWorld dungeonWorld = mock(DungeonWorld.class);
        levelController.loadDungeon(dungeonWorld);

        assertEquals(levelController.getDungeon(), dungeonWorld);
    }

    @DisplayName("getDungeon null")
    @Test
    void getDungeonNull() {
        // initialised levelController has uninitialised dungeonWorld
        assertNull(levelController.getDungeon());
    }

    //-----------draw--------------
    // wird nicht getestet ruft dungeonCreator methoden auf

    //-----------nextStage--------------
    // wird nicht getestet, da privat




}
