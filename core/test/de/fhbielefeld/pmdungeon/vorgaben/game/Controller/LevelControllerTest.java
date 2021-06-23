package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
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
    private HeadlessApplication headlessApplication;
    private LevelController levelController;
    private boolean loadedLevel;
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
    }

    //-----------loadDungeon--------------
    @DisplayName("load dungeon")
    @Test
    void loadDungeon() throws InvocationTargetException, IllegalAccessException {
        DungeonWorld dungeonWorld = mock(DungeonWorld.class);
        assertFalse(this.loadedLevel);

        levelController.loadDungeon(dungeonWorld);

        assertEquals(levelController.getDungeon(), dungeonWorld);
        // assertFalse(levelController); mach mit mockito
        // ignore DungeonWorld method
        assertTrue(this.loadedLevel);
    }

    //-----------update--------------
    @DisplayName("update stage not triggered")
    @Test
    void updateTrue() throws NoSuchFieldException, IllegalAccessException {
        // set nextLevelTrigger to true
        Field triggerField = LevelController.class.getDeclaredField("nextLevelTriggered");
        triggerField.setAccessible(true);
        // check nextStage var
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        // Check if conditions are met
        assertFalse(triggerField.getBoolean(levelController));
        assertEquals(stageField.get(levelController), LevelController.Stage.A);

        levelController.update();

        assertEquals(stageField.get(levelController), LevelController.Stage.A);
        // TODO: check for called draw method
    }

    @DisplayName("update stage if triggered")
    @Test
    void updateFalse() throws IllegalAccessException, NoSuchFieldException {
        // set nextLevelTrigger to true
        Field triggerField = LevelController.class.getDeclaredField("nextLevelTriggered");
        triggerField.setAccessible(true);
        triggerField.setBoolean(levelController, true);
        // check nextStage var
        Field stageField = LevelController.class.getDeclaredField("nextStage");
        stageField.setAccessible(true);
        // Check if conditions are met
        assertTrue(triggerField.getBoolean(levelController));
        assertEquals(stageField.get(levelController), LevelController.Stage.A);

        levelController.update();

        assertEquals(stageField.get(levelController), LevelController.Stage.B);
        // TODO: check for called draw method
    }


}
