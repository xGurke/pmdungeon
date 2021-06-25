package de.fhbielefeld.pmdungeon.vorgaben.graphic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@DisplayName("TestCase: TextStage")
public class TextStageTest {
    protected HeadlessApplication headlessApplication;

    @BeforeEach
    void setUp() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        this.headlessApplication = new HeadlessApplication(new Game() {
            @Override
            public void create() { }
        }, config);

        Gdx.gl = mock(GL20.class);
    }

    //-----------constructor--------------
    @DisplayName("normal constructor")
    @Test
    void constructor() throws NoSuchFieldException, IllegalAccessException {
        SpriteBatch pls = mock(SpriteBatch.class);
        TextStage textStage = new TextStage(pls);

        Field labelsField = TextStage.class.getDeclaredField("labels");
        labelsField.setAccessible(true);
        assertTrue(((List<Label>) labelsField.get(textStage)).isEmpty());

        // TODO: maybe ScreenViewport und Stage dimensions testen
    }

    //-----------drawText--------------
    @DisplayName("draw text")
    @Test
    void drawTect() throws NoSuchFieldException, IllegalAccessException {
        SpriteBatch pls = mock(SpriteBatch.class);
        TextStage textStage = new TextStage(pls);
        Field labelsField = TextStage.class.getDeclaredField("labels");
        labelsField.setAccessible(true);
        assertTrue(((List<Label>) labelsField.get(textStage)).isEmpty());

        Label label = textStage.drawText("Sample", "./assets/Asdonuts.ttf", Color.BLACK, 0,0,0,0,0);

        assertTrue(((List<Label>) labelsField.get(textStage)).contains(label));
    }

    //-----------removeText--------------
    @DisplayName("remove text")
    @Test
    void removeText() throws NoSuchFieldException, IllegalAccessException {
        SpriteBatch pls = mock(SpriteBatch.class);
        TextStage textStage = new TextStage(pls);
        Field labelsField = TextStage.class.getDeclaredField("labels");
        labelsField.setAccessible(true);
        Label label = textStage.drawText("Sample", "./assets/Asdonuts.ttf", Color.BLACK, 0,0,0,0,0);
        assertTrue(((List<Label>) labelsField.get(textStage)).contains(label));

        textStage.removeText(label);

        assertFalse(((List<Label>) labelsField.get(textStage)).contains(label));
    }

}
