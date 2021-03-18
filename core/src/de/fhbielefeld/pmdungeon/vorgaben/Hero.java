package de.fhbielefeld.pmdungeon.vorgaben;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    int counter=0;

    public Hero () {
        this.level=level;
        ArrayList <Texture> idleTextures = new ArrayList<Texture>();
        Texture idle1= new Texture("textures/characters/playercharacters/knight_m_run_anim_f0.png");
        Texture idle2= new Texture("textures/characters/playercharacters/knight_m_run_anim_f1.png");
        Texture idle3= new Texture("textures/characters/playercharacters/knight_m_run_anim_f2.png");
        Texture idle4= new Texture("textures/characters/playercharacters/knight_m_run_anim_f3.png");
        idleTextures.add(idle1);
        idleTextures.add(idle2);
        idleTextures.add(idle3);
        idleTextures.add(idle4);
        idleAnimation = new Animation(idleTextures,8);

    }


    public void updateLevel(DungeonWorld level){
        this.level=level;
        this.position=new Point(level.getRandomLocationInDungeon());
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



        if(level==null) return;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && level.isTileAccessible((int)(position.x),(int)(position.y+0.1))) position.y+=0.1;
        if (Gdx.input.isKeyPressed(Input.Keys.A) && level.isTileAccessible((int)(position.x-0.1),(int)(position.y))) position.x-=0.1;
        if (Gdx.input.isKeyPressed(Input.Keys.S) && level.isTileAccessible((int)(position.x),(int)(position.y-0.1))) position.y-=0.1;
        if (Gdx.input.isKeyPressed(Input.Keys.D) && level.isTileAccessible((int)(position.x+0.1),(int)(position.y))) position.x+=0.1;



        this.draw();

    }
}
