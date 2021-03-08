package de.fhbielefeld.pmdungeon.vorgaben.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IDrawable;

public class DungeonCamera extends OrthographicCamera {

    private IDrawable follows;

    public DungeonCamera(IDrawable follows){
        super();
        this.follows=follows;
    }

    public void follow (IDrawable follows){
        this.follows=follows;
    }

    public IDrawable getFollowedObject(){
        return this.follows;
    }

    public void update(){
        if (follows!=null)
            this.position.set(this.getFollowedObject().getPosition().x, this.getFollowedObject().getPosition().y, 0);
        else
            this.position.set(0,0,0);

        super.update();
    }

}
