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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayName("TestCase: TextStage")
public class TextStageTest {
    protected HeadlessApplication headlessApplication;
    private final String text = "Sample";
    private final String fontPath = "./assets/Asdonuts.ttf";
    private final Color color = Color.BLACK;
    private final int ints = 0;

    @BeforeEach
    void setUp() {
        // initialise the headless backend
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        this.headlessApplication = new HeadlessApplication(new Game() {
            @Override
            public void create() { }
        }, config);

        // mock OpenGL context, because headless backend does not have it, but is needed
        Gdx.gl = mock(GL20.class);
    }

    //-----------constructor--------------
    @DisplayName("constructor: normal")
    @Test
    void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        Field labelsField = TextStage.class.getDeclaredField("labels");
        labelsField.setAccessible(true);
        SpriteBatch spriteBatch = mock(SpriteBatch.class);
        TextStage textStage = new TextStage(spriteBatch);

        assertNotNull(textStage, "TextStage must exist");
        assertTrue(((List<Label>) labelsField.get(textStage)).isEmpty(), "label List should be empty");
    }

    //-----------drawText--------------
    @DisplayName("draw text")
    @Test
    void testDrawText() throws NoSuchFieldException, IllegalAccessException {
        Field labelsField = TextStage.class.getDeclaredField("labels");
        labelsField.setAccessible(true);
        TextStage textStage = new TextStage(mock(SpriteBatch.class));
        assertTrue(((List<Label>) labelsField.get(textStage)).isEmpty());

        Label label = textStage.drawText(text, fontPath, color, ints, ints, ints, ints, ints);

        assertTrue(((List<Label>) labelsField.get(textStage)).contains(label), "label should be in list");
        assertEquals(label.getText().toString(), text, "label text must match");
        assertEquals(label.getHeight(), ints, "label height must match");
        assertEquals(label.getWidth(), ints, "label width must match");
        assertEquals(label.getX(), ints, "label x position must match");
        assertEquals(label.getY(), ints, "label y position must match");
    }

    //-----------removeText--------------
    @DisplayName("remove text")
    @Test
    void testRemoveText() throws NoSuchFieldException, IllegalAccessException {
        TextStage textStage = new TextStage(mock(SpriteBatch.class));
        Field labelsField = TextStage.class.getDeclaredField("labels");
        labelsField.setAccessible(true);
        Label label1 = textStage.drawText(text, fontPath, Color.BLACK, ints, ints, ints, ints, ints);
        Label label2 = textStage.drawText(text, fontPath, Color.BLACK, ints, ints, ints, ints, ints);
        assertEquals(((List<Label>) labelsField.get(textStage)).size(), 2);

        textStage.removeText(label1);

        assertFalse(((List<Label>) labelsField.get(textStage)).contains(label1), "label should have been removed from list");
        assertTrue(((List<Label>) labelsField.get(textStage)).contains(label2), "label should remain in list");
    }

    // draw() - nicht testen, da nur LibGDX Methoden von Stage aufgerufen werden
    // clear() - nicht testen, da nur LibGDX Methoden von Stage aufgerufen werden

}
