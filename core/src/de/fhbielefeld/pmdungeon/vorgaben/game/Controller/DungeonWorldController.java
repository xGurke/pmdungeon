package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * loads and renders the level. Does NOT controll Entitys.
 */
public class DungeonWorldController {

    private DungeonWorld dungeonWorld;
    private boolean nextLevelTriggered = false;
    private final SpriteBatch batch;
    private Method onLevelLoad;
    private final DungeonConverter dungeonConverter = new DungeonConverter();
    private Stage nextStage = Stage.A;
    private Object klass;
    private Object[] args;

    /**
     *
     * @param batch global spriteBatch
     * @param onLevelLoad Method that will be called if a new level get load
     * @param klass Instance of the MainControllerClass
     * @param args Arguments for onLevelLoaded
     */
    public DungeonWorldController(SpriteBatch batch, Method onLevelLoad, Object klass, Object [] args) {
        this.batch = batch;
        this.onLevelLoad=onLevelLoad;
        this.klass=klass;
        this.args=args;
    }

    /**
     * Load a new dungeon. Call onLevelLoad
     * @param dungeon DungeonWorld to load
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setupDungeon(DungeonWorld dungeon) throws InvocationTargetException, IllegalAccessException {
        this.dungeonWorld = dungeon;
        this.nextLevelTriggered = false;
        this.dungeonWorld.makeConnections();
        onLevelLoad.invoke(klass,args);
    }

    /**
     * If next level is triggered, this will load it.
     * Also calls the render method.
     */
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
     * Check if given Point is (rounded) a TriggerTile
     * @param p point check
     * @return result of check
     */
    public boolean checkIfTileIsTriggerTile(Point p){
        return (int)p.x==dungeonWorld.getNextLevelTrigger().getX() && (int)p.y==dungeonWorld.getNextLevelTrigger().getY();
    }

    /**
     * Used to set the trigger if the next level should be loaded
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
        switch (nextStage) {
            case A:
                setupDungeon(dungeonConverter.dungeonFromJson("core/assets/small_dungeon.json"));
                System.out.println("Move levle");
                nextStage = Stage.B;
                break;
            case B:
                setupDungeon(dungeonConverter.dungeonFromJson("core/assets/simple_dungeon_2.json"));
                nextStage = Stage.A;
                break;
        }
    }

    enum Stage {
        A,
        B
    }

    /**
     * Return the current level.
     * @return
     */
    public DungeonWorld getDungeon() {
        return dungeonWorld;
    }

    private boolean isNextLevelTriggered() {
        return nextLevelTriggered;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

}
