package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.DungeonGame;
import de.fhbielefeld.pmdungeon.vorgaben.tools.DungeonCamera;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Class for setting up the basics like window height and camera. Is also holding the gameworld instance and is handling the demo sequence.
 */
public class MainGameController extends ScreenAdapter {

    public static final float VIRTUAL_HEIGHT = 5f;

    private final DungeonGame dungeonGame;
    private DungeonCamera camera;
    private final DungeonEntityController dungeonEntityController;
    private final DungeonWorldController dungeonWorldController;


    public MainGameController(final DungeonGame dungeonGame) {
        this.dungeonGame = dungeonGame;
        this.dungeonEntityController = new DungeonEntityController(dungeonGame.getBatch());
        this.dungeonWorldController= new DungeonWorldController(dungeonGame.getBatch());
        // todo this.hud = new HeadUpDisplay(gameWorld);
        //load startlevel
        dungeonWorldController.setupDungeon(new DungeonConverter().dungeonFromJson("small_dungeon.json"));
        setupCamera();
        //your setup
        setup();
    }

    //----------------- add your stuff -----------------

    //Here you can do stuff you want to do at the beginning of the game
    public void setup(){
        //create hero
        //cam follow hero
        //place hero gameworld.getstartingposition
    }

    //----------------- stop adding -----------------


    /**
     * Main gameloop.
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        //updates all objects in the dungeon
        dungeonEntityController.update();
        //updates camera
        camera.update();

        dungeonGame.getBatch().setProjectionMatrix(camera.combined);
        dungeonGame.getBatch().begin();
        //updates the level
        dungeonWorldController.update();
        dungeonGame.getBatch().end();

    }


    /**
     * Setting up the camera.
     */
    private void setupCamera() {
        camera = new DungeonCamera(null);
        camera.position.set(0, 0, 0);
        camera.zoom +=3;
        camera.update();
    }


    /**
     * Resizing the camera according to the size of the window.
     *
     * @param width  Window width
     * @param height Window height
     */
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, VIRTUAL_HEIGHT * width / (float) height, VIRTUAL_HEIGHT);
        dungeonGame.getBatch().setProjectionMatrix(camera.combined);
    }

}
