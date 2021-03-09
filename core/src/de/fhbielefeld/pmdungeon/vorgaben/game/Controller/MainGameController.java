package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import de.fhbielefeld.pmdungeon.vorgaben.Hero;
import de.fhbielefeld.pmdungeon.vorgaben.Monster;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.DungeonCamera;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Controlls the game.
 * Setup for all important objects.
 * Contains gameloop.
 */
public class MainGameController extends ScreenAdapter {

    /**
     * Does some setup. Contains the global SpriteBatch.
     */
    private GameSetup gameSetup;

    /**
     * Controls all entity's
     */
    private DungeonEntityController dungeonEntityController;
    /**
     * The viewport for the dungeon
     */
    private DungeonCamera camera;
    /**
     * Controls the level
     */
    private DungeonWorldController dungeonWorldController;

    /**
     * Creates new MainGameController
     *
     * @param gameSetup setup class
     */
    public MainGameController(final GameSetup gameSetup) {
        this.gameSetup = gameSetup;
        this.dungeonEntityController = new DungeonEntityController();
        // todo this.hud = new HeadUpDisplay(gameWorld);

        setupCamera();

        //your setup
        setup();

        setupWorldController();
        //load first level
        try {
            dungeonWorldController.setupDungeon(new DungeonConverter().dungeonFromJson("core/assets/small_dungeon.json"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //----------------- add your stuff -----------------

    //Here you can do stuff you want to do at the beginning of the game
    Hero h;
    Monster m;

    public void setup() {
        System.out.println("Game started");
        h = new Hero(gameSetup.getBatch());
        m = new Monster(gameSetup.getBatch());

        camera.follow(m);
        dungeonEntityController.addEntity(h);
        dungeonEntityController.addEntity(m);
    }

    //things you want to do at the begin of every frame
    public void beginFrame() {


    }

    //things you want to do at the end of every frame
    public void endFrame() {
        //trigger next stage
        if (dungeonWorldController.checkForTrigger(h.getPosition())) dungeonWorldController.triggerNextStage();
    }

    /**
     * This methode will be called by the DungeonWorldController every time a new level is loaded.
     * Useful to place new monster and items and remove old ones from the entityController
     */

    public void onLevelLoad() {
        h.updateLevel(dungeonWorldController.getDungeon());
        m.updateLevel(dungeonWorldController.getDungeon());

    }

    //----------------- stop adding -----------------


    /**
     * Main gameloop.
     * Redraws the dungeon and calls all the update methods.
     *
     * @param delta Time since last loop. (since the PM-Dungeon is frame based, this isn't very usefull)
     */
    @Override
    public void render(float delta) {
        //clears the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        beginFrame();

        //need to be called before stuff can be drawn
        gameSetup.getBatch().begin();

        //updates the level
        dungeonWorldController.update();

        //need to be called after stuff has been drawn
        gameSetup.getBatch().end();

        //updates all objects in the dungeon
        dungeonEntityController.update();

        //updates camera
        gameSetup.getBatch().setProjectionMatrix(camera.combined);

        //updates camera position
        camera.update();

        endFrame();

    }


    /**
     * Setting up the camera.
     */
    private void setupCamera() {
        camera = new DungeonCamera(null);
        camera.position.set(0, 0, 0);
        camera.zoom += 3;
        camera.update();
    }

    /**
     * Setting up the WorldController.
     */
    private void setupWorldController() {
        try {
            //this method will be called every time a new level gets load
            Method functionToPass = MainGameController.class.getMethod("onLevelLoad");
            //if you need parameter four your method, add them here
            Object[] arguments = new Object[0];
            this.dungeonWorldController = new DungeonWorldController(gameSetup.getBatch(), functionToPass, this, arguments);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resizing the camera according to the size of the window.
     *
     * @param width  Window width
     * @param height Window height
     */
    @Override
    public void resize(int width, int height) {
        //was static but only used here
        float VIRTUAL_HEIGHT = 5f;
        camera.setToOrtho(false, VIRTUAL_HEIGHT * width / (float) height, VIRTUAL_HEIGHT);
        gameSetup.getBatch().setProjectionMatrix(camera.combined);
    }

}
