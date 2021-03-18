package de.fhbielefeld.pmdungeon.vorgaben.tools;


import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IHUDElement;


import java.util.ArrayList;


/**
 * Holds the HUD.
 */
public class HUD {
    private final SpriteBatch hudBatch;
    private final OrthographicCamera hudCamera;
    private ArrayList<IHUDElement> hudElements;

    public HUD() {
        hudBatch = new SpriteBatch();
        hudCamera = new OrthographicCamera();
        hudCamera.position.set(0, 0, 0);
        hudCamera.update();
        hudElements = new ArrayList<>();
    }

    /**
     * Add an element to draw on the hud
     *
     * @param element element to add
     */
    public void addHudElement(IHUDElement element) {
        this.hudElements.add(element);
    }

    /**
     * Remove element
     *
     * @param element element to remove
     */
    public void removeHudElement(IHUDElement element) {
        if (hudElements.contains(element))
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

    /**
     * Draws all the elements
     */
    private void drawElements() {
        for (IHUDElement element : hudElements) {
            Texture texture = element.getTexture();
            Sprite sprite = new Sprite(texture);
            //scaling
            sprite.setSize(element.getWidth(), element.getHeight() / texture.getWidth());
            sprite.setPosition(element.getPosition().x, element.getPosition().y);
            hudBatch.begin();
            sprite.draw(hudBatch);
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
