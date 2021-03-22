package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import de.fhbielefeld.pmdungeon.vorgaben.DungeonIntegrator;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;
import de.fhbielefeld.pmdungeon.vorgaben.tools.DungeonCamera;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.HUD;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Controlls the game.
 * Setup for all important objects.
 * Contains gameloop.
 */
public class MainController extends ScreenAdapter {

    /**
     * Does some setup. Contains the global SpriteBatch.
     */
    private GameSetup gameSetup;
    /**
     * Controls all entity's
     */
    private EntityController entityController;
    /**
     * The viewport for the dungeon
     */
    private DungeonCamera camera;
    /**
     * Controls the level
     */
    private LevelController levelController;
    /**
     * HUD
     */
    private HUD hud;

    /**
     * This is the Main Instance of the students implementation
      */
    private DungeonIntegrator dungeonintegrator;

    /**
     * Marks if the firstFrame is already calculated or not (true= not caluulated)
     */
    private boolean firstFrame=true;


    /**
     * Creates new MainGameController
     *
     */
    public MainController(DungeonIntegrator dungeonintegrator) {
        this.dungeonintegrator = dungeonintegrator;
    }

    /**
     * Setup for the MainController
     */
    private void firstFrame(){
        this.entityController = new EntityController();
        this.hud = new HUD();
        setupCamera();
        setupWorldController();
        dungeonintegrator.setup();
        //load first level
        try {
            levelController.loadDungeon(new DungeonConverter().dungeonFromJson(Constants.STARTLEVEL));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        firstFrame=false;
    }

    /**
     * Main gameloop.
     * Redraws the dungeon and calls all the update methods.
     *
     * @param delta Time since last loop. (since the PM-Dungeon is frame based, this isn't very usefull)
     */
    @Override
    public void render(float delta) {
        if(firstFrame) this.firstFrame();

        dungeonintegrator.beginFrame();

        //clears the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        //need to be called before stuff can be drawn
        GameSetup.batch.begin();

        //updates the level
        levelController.update();

        //need to be called after stuff has been drawn
        GameSetup.batch.end();

        //updates all objects in the dungeon
        entityController.update();

        //updates projectionsmatrix
        GameSetup.batch.setProjectionMatrix(camera.combined);

        //updates camera position
        camera.update();

        //updates and draw hud
        hud.draw();
        dungeonintegrator.endFrame();

    }
    /**
     * Setting up the WorldController.
     */
    private void setupWorldController() {
        try {
            //this method will be called every time a new level gets load
            Method functionToPass = dungeonintegrator.getClass().getMethod("onLevelLoad");
            System.out.println("DEBUG: "+functionToPass);
            //if you need parameter four your method, add them here
            Object[] arguments = new Object[0];
            this.levelController = new LevelController(functionToPass, dungeonintegrator, arguments);
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



//getter for own implementation

    public DungeonCamera getCamera(){
        return this.camera;
    }

    public LevelController getLevelController(){
        return this.levelController;
    }

    public EntityController getEntityController(){
        return this.entityController;
    }

    public HUD getHUD(){
        return this.hud;
    }
}
