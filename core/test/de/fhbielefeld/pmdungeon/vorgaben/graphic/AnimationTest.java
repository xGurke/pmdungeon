package de.fhbielefeld.pmdungeon.vorgaben.graphic;

import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@DisplayName("TestCase: Point")
public class AnimationTest {
    private Animation animation;

    @BeforeEach
    void setUp() {
        animation = null;
    }

    //-----------constructor--------------
    @DisplayName("constructor: normal")
    @Test
    void testConstructorNormal() throws NoSuchFieldException, IllegalAccessException {
        List<Texture> frames = new ArrayList<>();
        frames.add(mock(Texture.class));
        int fTime = 1;
        this.animation = new Animation(frames, fTime);
        // Get private fields and check them
        Field animationField = Animation.class.getDeclaredField("animationFrames");
        Field framesField = Animation.class.getDeclaredField("frames");
        Field frameTimeField = Animation.class.getDeclaredField("frameTime");
        animationField.setAccessible(true);
        framesField.setAccessible(true);
        frameTimeField.setAccessible(true);

        assertEquals(animationField.get(this.animation), frames, "frames must be equal");
        assertEquals(framesField.get(this.animation), frames.size(), "amount of frames must be equal");
        assertEquals(frameTimeField.get(this.animation), fTime, "frameTime must be equal");
    }

    @DisplayName("constructor: empty list ")
    @Test
    void testConstructorEmptyList() {
        List<Texture> frames = new ArrayList<>();
        int fTime = 1;

        assertThrows(IllegalArgumentException.class, () -> {
           this.animation = new Animation(frames, fTime);
        }, "Empty List must throw IllegalArgumentException");
    }

    @DisplayName("constructor: negative frameTime")
    @Test
    void testConstructorNegativeTime() {
        List<Texture> frames = new ArrayList<>();
        frames.add(mock(Texture.class));
        int fTime = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            this.animation = new Animation(frames, fTime);
        }, "Negative frameTime must throw IllegalArgumentException");
    }

    //-----------getNextAnimation--------------
    @DisplayName("getNextAnimation: no next needed")
    @Test
    void testGetNextTextureNotNeeded() {
        Texture texture1 = mock(Texture.class);
        Texture texture2 = mock(Texture.class);
        ArrayList<Texture> frames = new ArrayList<>();
        frames.add(texture1);
        frames.add(texture2);
        int fTime = 1000; // texture does not need to change for a long time
        this.animation = new Animation(frames, fTime);

        assertEquals(animation.getNextAnimationTexture(), texture1, "texture must be first texture in list: ->texture1");
        assertEquals(animation.getNextAnimationTexture(), texture1, "texture should not change: texture1->texture1");
    }

    @DisplayName("getNextAnimation: next")
    @Test
    void testGetNextTextureNeeded() {
        Texture texture1 = mock(Texture.class);
        Texture texture2 = mock(Texture.class);
        ArrayList<Texture> frames = new ArrayList<>();
        frames.add(texture1);
        frames.add(texture2);
        int fTime = 0; // texture needs to change immediately
        this.animation = new Animation(frames, fTime);

        assertEquals(animation.getNextAnimationTexture(), texture1, "texture must be first texture in list: ->texture1");
        assertEquals(animation.getNextAnimationTexture(), texture2, "texture must be next texture from list: texture1->texture2");
    }

}
