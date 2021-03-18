package de.fhbielefeld.pmdungeon.vorgaben.tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.utils.Disposable;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IUpdateable;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Holds the HUD.
 */
public class HUD{

    private final SpriteBatch hudBatch;
    private final OrthographicCamera hudCamera;
    private ArrayList<IHUDElement> hudElements;


    public HUD() {
        hudBatch = new SpriteBatch();

        hudCamera = new OrthographicCamera();
        hudCamera.position.set(0, 0, 0);
        hudCamera.update();
        hudElements=new ArrayList<>();
    }


    public void addHudElement(IHUDElement element){
        this.hudElements.add(element);
    }

    public void removeHudElement(IHUDElement element){
        this.hudElements.remove(element);
    }

    /**
     * Main loop of the hud.
     */
    public void draw() {
        hudCamera.update();
        hudBatch.setProjectionMatrix(hudCamera.combined);
        drawElements();
        resize();

    }

    private void drawElements(){
        int xOffset=0;
        int yOffset=0;
        for (IHUDElement element: hudElements){
            Texture texture=element.getTexture();
            Sprite sprite = new Sprite(texture);
            //this will resize the texture. this is setuped for the textures used in the thesis
            sprite.setSize(0.5f, ((float) texture.getHeight() / (float) texture.getWidth())/2);
            //where to draw the sprite
            sprite.setPosition(5.5f, 4.5f);
            //need to be called before drawing
            hudBatch.begin();
            //draw sprite
            sprite.draw(hudBatch);
            //need to be called after drawing
            hudBatch.end();



        }

    }

    /**
     * Resizing the camera according to the size of the window.
     */
    public void resize() {
       hudCamera.setToOrtho(false, Constants.VIRTUALHEIGHT * Constants.WIDTH / (float) Constants.HEIGHT, Constants.VIRTUALHEIGHT);
        hudBatch.setProjectionMatrix(hudCamera.combined);
    }

}
