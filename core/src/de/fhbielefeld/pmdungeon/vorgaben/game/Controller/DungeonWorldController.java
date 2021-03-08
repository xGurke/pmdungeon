package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.dungeonconverter.DungeonConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DungeonWorldController {

    private DungeonWorld dungeonWorld;
    private boolean nextLevelTriggered = false;
    private final SpriteBatch batch;
    private Method onLevelLoad;
    private final DungeonConverter dungeonConverter = new DungeonConverter();
    private Stage stage = Stage.A;
    private Object klass;
    private Object[] args;

    public DungeonWorldController(SpriteBatch batch, Method onLevelLoad, Object klass, Object [] args) {
        this.batch = batch;
        this.onLevelLoad=onLevelLoad;
        this.klass=klass;
        this.args=args;
    }

    /**
     * Setting up a new dungeon. Gets also called when the level changes and the
     * dungeon gets replaced.
     *
     * @param dungeon new Dungeon
     */
    public void setupDungeon(DungeonWorld dungeon) throws InvocationTargetException, IllegalAccessException {
        this.dungeonWorld = dungeon;
        this.nextLevelTriggered = false;
        this.dungeonWorld.makeConnections();
        onLevelLoad.invoke(klass,args);
    }

    public void update(){
        //load next stage if triggered
        if (isNextLevelTriggered()) {
            try {
                nextStage();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        render();

    }

    /**
     * used to set trigger
     */
    public void triggerNextStage(){
        this.nextLevelTriggered=true;
    }

    /**
     * Renders the dungeon itself.
     */
    public void render() {
        dungeonWorld.renderFloor(batch);
        dungeonWorld.renderWalls(dungeonWorld.getHeight() - 1, 0, batch);
        //dungeon.renderWalls(dungeon.getHeight() - 1, (int) getHero().getPositionY(), batch);
        //dungeon.renderWalls((int) getHero().getPositionY(), 0, batch);
    }


    /**
     * If next stage is triggered, change the dungeon.
     */
    private void nextStage() throws InvocationTargetException, IllegalAccessException {
        switch (stage) {
            case A:
                setupDungeon(dungeonConverter.dungeonFromJson("small_dungeon.json"));
                stage = Stage.B;
                break;
            case B:
                setupDungeon(dungeonConverter.dungeonFromJson("simple_dungeon.json"));
                stage = Stage.A;
                break;
        }
    }

    enum Stage {
        A,
        B
    }







    public DungeonWorld getDungeon() {
        return dungeonWorld;
    }

    public boolean isNextLevelTriggered() {
        return nextLevelTriggered;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
