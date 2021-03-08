package de.fhbielefeld.pmdungeon.vorgaben.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;

/**
 * The viewport for the game.
 */
public class DungeonCamera extends OrthographicCamera {

    private IDrawable follows;

    /**
     * Creates a new camera.
     * @param follows the entity the camera should follow, null for defaul coordinates.
     */
    public DungeonCamera(IDrawable follows){
        super();
        this.follows=follows;
    }

    /**
     * set the entity to follow
     * @param follows entity to follow
     */
    public void follow (IDrawable follows){
        this.follows=follows;
    }

    /**
     * @return the entity the camera currently follows
     */
    public IDrawable getFollowedObject(){
        return this.follows;
    }

    /**
     * update camera position
     */
    public void update(){
        if (follows!=null)
            this.position.set(this.getFollowedObject().getPosition().x, this.getFollowedObject().getPosition().y, 0);
        else
            this.position.set(0,0,0);

        super.update();
    }

}
