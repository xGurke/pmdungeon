package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;
import de.fhbielefeld.pmdungeon.vorgaben.tools.DungeonCamera;
import de.fhbielefeld.pmdungeon.vorgaben.tools.HUD;

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
     * HUD
     */
    private HUD hud;

    /**
     * Creates new MainGameController
     *
     */
    public MainGameController() {
        this.dungeonEntityController = new DungeonEntityController();
        this.hud = new HUD();
        setupCamera();
        setupWorldController();
        setup();
        //load first level
        try {
            dungeonWorldController.loadDungeon(new DungeonConverter().dungeonFromJson("core/assets/small_dungeon.json"));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //----------------- add your code -----------------

    //Here you can do stuff you want to do at the beginning of the game
    public void setup() {
    }

    //things you want to do at the begin of every frame
    public void beginFrame() {


    }

    //things you want to do at the end of every frame
    public void endFrame() {

    }

    /**
     * This methode will be called by the DungeonWorldController every time a new level is loaded.
     * Useful to place new monster and items and remove old ones from the entityController
     */
    public void onLevelLoad() {
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
        GameSetup.batch.begin();

        //updates the level
        dungeonWorldController.update();

        //need to be called after stuff has been drawn
        GameSetup.batch.end();

        //updates all objects in the dungeon
        dungeonEntityController.update();

        //updates projectionsmatrix
        GameSetup.batch.setProjectionMatrix(camera.combined);

        //updates camera position
        camera.update();

        //updates and draw hud
        hud.draw();
        endFrame();

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
            this.dungeonWorldController = new DungeonWorldController(functionToPass, this, arguments);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    /**
     * Setting up the camera.
     */
    private void setupCamera() {
        camera = new DungeonCamera(null, Constants.VIRTUALHEIGHT * Constants.WIDTH / (float) Constants.HEIGHT, Constants.VIRTUALHEIGHT);
        camera.position.set(0, 0, 0);
        camera.zoom += 1;
        camera.update();
    }
}
