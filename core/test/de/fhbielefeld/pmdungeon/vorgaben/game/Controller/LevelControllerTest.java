package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static de.fhbielefeld.pmdungeon.vorgaben.game.Controller.LevelController.Stage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TestCase: LevelController")
public class LevelControllerTest {
    protected HeadlessApplication headlessApplication;
    private LevelController levelController;
    private boolean loadedLevel;
    private Object invokedArgs;
    private final String args = "arg1";

    /**
     * invoke method is passed to LevelController instance
     */
    public void invoke(String args) {
        this.loadedLevel = true;
        this.invokedArgs = args;
    }

    // private invoke Method used for testing IllegalAccessException
    private void invokeAccessError(){ }

    // public invoke Method used for testing InvocationTargetException
    public void invokeInvocationError() throws InvocationTargetException {
        throw new InvocationTargetException(new Throwable("Test Exception"));
    }

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        Method tmp = this.getClass().getMethod("invoke", String.class);
        levelController = new LevelController(tmp, this, new String[]{args});
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
    void testLoadDungeon() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Field nextField = LevelController.class.getDeclaredField("nextLevelTriggered");
        nextField.setAccessible(true);
        DungeonWorld dungeonWorld = new DungeonWorld(0, 0);
        assertNull(this.levelController.getDungeon());
        assertFalse(this.loadedLevel);

        levelController.loadDungeon(dungeonWorld);

        assertEquals(levelController.getDungeon(), dungeonWorld, "DungeonWorld must be set");
        assertFalse(nextField.getBoolean(levelController));
        // ignore DungeonWorld method
        // check if passed Method onLevelLoad was invoked
        assertTrue(this.loadedLevel, "passed method must be invoked");
        assertEquals(this.invokedArgs, args, "set args must be passed to invoke method");
    }

    //-----------update--------------
    @DisplayName("update: nextLevelTriggered false")
    @Test
    void testUpdateFalse() throws NoSuchFieldException, IllegalAccessException {
        Field nextField = LevelController.class.getDeclaredField("nextLevelTriggered");
        nextField.setAccessible(true);
        nextField.setBoolean(levelController, false);
        // Set dungeon world, so we can check draw call
        DungeonWorld dungeonWorld = mock(DungeonWorld.class);
        Field dungeonField = LevelController.class.getDeclaredField("dungeonWorld");
        dungeonField.setAccessible(true);
        dungeonField.set(levelController, dungeonWorld);
        // check nextStage var
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        assertEquals(stageField.get(levelController), Stage.A);

        levelController.update();

        assertEquals(stageField.get(levelController), Stage.A, "Stage must still be A: updated but nor triggered");
        // called in draw method --> draw method was called
        verify(dungeonWorld, times(1)).renderFloor(any(SpriteBatch.class));
        verify(dungeonWorld, times(1)).renderWalls(anyInt(), anyInt(), any(SpriteBatch.class));
    }

    @DisplayName("update: nextLevelTriggered true")
    @Test
    void testUpdateTrue() throws IllegalAccessException, NoSuchFieldException {
        try (
                MockedConstruction<DungeonWorld> dungeonMocked = mockConstruction(DungeonWorld.class)
        ) {
            // check nextStage var
            Field stageField = LevelController.class.getDeclaredField("nextStage");
            stageField.setAccessible(true);
            // Check conditions
            levelController.triggerNextStage();
            assertEquals(stageField.get(levelController), Stage.A);

            levelController.update();

            assertEquals(stageField.get(levelController), Stage.B, "Stage must be B: updated and triggered");
            // called in draw method --> draw method was called
            verify(levelController.getDungeon(), times(1)).renderFloor(any(SpriteBatch.class));
            verify(levelController.getDungeon(), times(1)).renderWalls(anyInt(), anyInt(), any(SpriteBatch.class));

        }
    }

    @DisplayName("update multiple times")
    @Test
    void testUpdateMulti() throws IllegalAccessException, NoSuchFieldException {
        try (
                MockedConstruction<DungeonWorld> dungeonMocked = mockConstruction(DungeonWorld.class)
        ) {
            // check nextStage var
            Field stageField = LevelController.class.getDeclaredField("nextStage");
            stageField.setAccessible(true);
            // Check conditions
            levelController.triggerNextStage();
            assertEquals(stageField.get(levelController), Stage.A);

            levelController.update();
            levelController.update();
            levelController.update();

            // multiple updates, but only first has nextLevelTriggered
            assertEquals(stageField.get(levelController), Stage.B, "stage was updated 3 times, but triggered only one time: Stage.A->Stage.B");
            // called in draw method --> draw method was called
            verify(levelController.getDungeon(), times(3)).renderFloor(any(SpriteBatch.class));
            verify(levelController.getDungeon(), times(3)).renderWalls(anyInt(), anyInt(), any(SpriteBatch.class));
        }
    }

    @DisplayName("update catch access exception")
    @Test
    void testUpdateAccessException() throws NoSuchMethodException {

        Method method = LevelControllerTest.class.getDeclaredMethod("invokeAccessError");
        LevelController levelException = new LevelController(method, this, null);

        levelException.triggerNextStage();
        assertDoesNotThrow(levelException::update, "IllegalAccessException must be caught");
    }

    @DisplayName("update catch invocation exception")
    @Test
    void testUpdateInvocationException() throws NoSuchMethodException {

        Method method = LevelControllerTest.class.getDeclaredMethod("invokeInvocationError");
        LevelController levelException = new LevelController(method, this, null);

        levelException.triggerNextStage();
        assertDoesNotThrow(levelException::update, "InvocationTargetException must be caught");
    }

    //-----------checkForTrigger--------------
    @DisplayName("checkTrigger: is trigger tile")
    @Test
    void testCheckForTriggerTrue() {
        levelController.triggerNextStage();
        levelController.update();
        assertNotNull(levelController.getDungeon());
        Tile tile = levelController.getDungeon().getNextLevelTrigger();
        Point point = new Point(tile.getX(), tile.getY());

        assertTrue(levelController.checkForTrigger(point), "Trigger tile from Dungeon must return true");
    }

    @DisplayName("checkTrigger: not trigger tile")
    @Test
    void testCheckForTriggerFalse() {
        levelController.triggerNextStage();
        levelController.update();
        assertNotNull(levelController.getDungeon());
        Tile tile = levelController.getDungeon().getNextLevelTrigger();
        Point point = new Point(tile.getX() - 1, tile.getY() - 1); // point is never triggering tile

        assertFalse(levelController.checkForTrigger(point), "Should not be the trigger tile");
    }

    //-----------triggerNextStage--------------
    @DisplayName("triggerNextStage: from false")
    @Test
    void testTriggerNextStage() throws NoSuchFieldException, IllegalAccessException {
        Field stageField = LevelController.class.getDeclaredField("nextLevelTriggered");
        stageField.setAccessible(true);
        assertFalse(stageField.getBoolean(levelController));

        levelController.triggerNextStage();

        assertTrue(stageField.getBoolean(levelController), "nextStage must be true");
    }

    @DisplayName("triggerNextStage: from true")
    @Test
    void testTriggerNextStageFromTrue() throws NoSuchFieldException, IllegalAccessException {
        Field stageField = LevelController.class.getDeclaredField("nextLevelTriggered");
        stageField.setAccessible(true);
        stageField.setBoolean(levelController, true);
        assertTrue(stageField.getBoolean(levelController));

        levelController.triggerNextStage();

        assertTrue(stageField.getBoolean(levelController), "nextStage must be true");
    }

    //-----------getDungeon--------------
    @DisplayName("getDungeon")
    @Test
    void testGetDungeon() throws IllegalAccessException, InvocationTargetException {
        DungeonWorld dungeonWorld = new DungeonWorld(0, 0);
        levelController.loadDungeon(dungeonWorld);

        assertEquals(levelController.getDungeon(), dungeonWorld, "Set dungeon must be returned");
    }

    @DisplayName("getDungeon null")
    @Test
    void testGetDungeonNull() {
        // initialised levelController has uninitialised dungeonWorld
        assertNull(levelController.getDungeon(), "Must return null (uninitialised dungeon is nul)");
    }

    //-----------draw--------------
    @DisplayName("draw")
    @Test
    void testDraw() throws InvocationTargetException, IllegalAccessException {
        DungeonWorld dungeonWorld = mock(DungeonWorld.class);
        levelController.loadDungeon(dungeonWorld);
        assertEquals(this.levelController.getDungeon(), dungeonWorld);

        levelController.draw();

        // called in draw method --> draw method was called
        verify(dungeonWorld).renderFloor(any(SpriteBatch.class));
        verify(dungeonWorld).renderWalls(anyInt(), anyInt(), any(SpriteBatch.class));
    }

    @DisplayName("draw with null")
    @Test
    void testDrawNull() {
        assertNull(levelController.getDungeon());

        levelController.draw();

        // Check that the SpriteBatch draw() function, which is used to draw/render, is never called
        verify(GameSetup.batch, never()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
        assertNull(levelController.getDungeon());
    }

    //-----------nextStage--------------
    @DisplayName("nextStage: A->B")
    @Test
    void testNextStageAToB() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        Method nextStageMethod = LevelController.class.getDeclaredMethod("nextStage");
        nextStageMethod.setAccessible(true);
        levelController.triggerNextStage();
        assertEquals(stageField.get(levelController), Stage.A);

        nextStageMethod.invoke(levelController);

        assertEquals(stageField.get(levelController), Stage.B, "nextStage from A should be B");
    }

    @DisplayName("nextStage: B->C")
    @Test
    void testNextStageBToC() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        Method nextStageMethod = LevelController.class.getDeclaredMethod("nextStage");
        nextStageMethod.setAccessible(true);
        // Vorbedingugnen
        stageField.set(levelController, Stage.B);
        levelController.triggerNextStage();
        assertEquals(stageField.get(levelController), Stage.B);

        nextStageMethod.invoke(levelController);

        assertEquals(stageField.get(levelController), Stage.C, "nextStage from B should be C");
    }

    @DisplayName("nextStage: C->D")
    @Test
    void testNextStageCToD() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        Method nextStageMethod = LevelController.class.getDeclaredMethod("nextStage");
        nextStageMethod.setAccessible(true);
        // Vorbedingugnen
        stageField.set(levelController, Stage.C);
        levelController.triggerNextStage();
        assertEquals(stageField.get(levelController), Stage.C);

        nextStageMethod.invoke(levelController);

        assertEquals(stageField.get(levelController), Stage.D, "nextStage from C should be D");
    }

    @DisplayName("nextStage: D->A")
    @Test
    void testNextStageDToA() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        Method nextStageMethod = LevelController.class.getDeclaredMethod("nextStage");
        nextStageMethod.setAccessible(true);
        // Vorbedingungen
        stageField.set(levelController, Stage.D);
        levelController.triggerNextStage();
        assertEquals(stageField.get(levelController), Stage.D);

        nextStageMethod.invoke(levelController);

        assertEquals(stageField.get(levelController), Stage.A, "nextStage from D should be A");
    }

}
