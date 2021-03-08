package de.fhbielefeld.pmdungeon.vorgaben;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreater.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IUpdateable;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.util.ArrayList;

public class Hero implements IAnimatable, IUpdateable {

    private Point position;
    private DungeonWorld level;
    private Animation idleAnimation;
    private SpriteBatch batch;
    int counter=0;

    public Hero (DungeonWorld level, SpriteBatch batch) {
        this.level=level;
        this.batch=batch;
        ArrayList <Texture> idleTextures = new ArrayList<Texture>();
        Texture idle1= new Texture("textures/chest/chest_full_open_anim_f0.png");
        Texture idle2= new Texture("textures/chest/chest_full_open_anim_f1.png");
        Texture idle3= new Texture("textures/chest/chest_full_open_anim_f2.png");
        idleTextures.add(idle1);
        idleTextures.add(idle2);
        idleTextures.add(idle3);
        idleAnimation = new Animation(idleTextures,8);
        this.position= new Point (level.getRandomLocationInDungeon());
    }


    public void updateLevel(DungeonWorld level){
        this.level=level;
    }
    public void setPosition(Point p){
        this.position=p;
    }

    @Override
    public Animation getActiveAnimation() {
        return idleAnimation;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean deleteable() {
        return false;
    }

    @Override
    public void update() {

        if (counter>120 && level.isTileAccessible((int)position.x, (int)position.y)) {
            position.x=level.getNextLevelTrigger().getX();
            position.y=level.getNextLevelTrigger().getY();
            counter=0;
        }
        counter++;

        this.draw(0,0,batch);

    }
}
