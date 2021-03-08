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
        Texture idle1= new Texture("textures/characters/playercharacters/knight_m_idle_anim_f0.png");
        Texture idle2= new Texture("textures/characters/playercharacters/knight_m_idle_anim_f1.png");
        Texture idle3= new Texture("textures/characters/playercharacters/knight_m_idle_anim_f2.png");
        Texture idle4= new Texture("textures/characters/playercharacters/knight_m_idle_anim_f3.png");
        idleTextures.add(idle1);
        idleTextures.add(idle2);
        idleTextures.add(idle3);
        idleTextures.add(idle4);
        idleTextures.add(idle1);
        idleAnimation = new Animation(idleTextures,8);
        this.position=new Point(5,4);



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

        if (counter==120 && level.isTileAccessible(position.x, position.y)) position.x++;
        counter++;

        this.draw(0,0,batch);

    }
}
