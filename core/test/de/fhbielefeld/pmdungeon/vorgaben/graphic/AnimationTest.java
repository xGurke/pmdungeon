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
    @DisplayName("normal constructor")
    @Test
    void normalConstructor() throws NoSuchFieldException, IllegalAccessException {
        List<Texture> frames = new ArrayList<>();
        frames.add(mock(Texture.class));
        int fTime = 2;

        this.animation = new Animation(frames, fTime);

        // Get private fields and check them
        Field animationField = Animation.class.getDeclaredField("animationFrames");
        Field framesField = Animation.class.getDeclaredField("frames");
        Field frameTimeField = Animation.class.getDeclaredField("frameTime");
        animationField.setAccessible(true);
        framesField.setAccessible(true);
        frameTimeField.setAccessible(true);
        assertEquals(animationField.get(this.animation), frames);
        assertEquals(framesField.get(this.animation), frames.size());
        assertEquals(frameTimeField.get(this.animation), fTime);
    }

    @DisplayName("empty list constructor")
    @Test
    void emptyListConstructor() {
        List<Texture> frames = new ArrayList<>();
        int fTime = 2;

        assertThrows(IllegalArgumentException.class, () -> {
           this.animation = new Animation(frames, fTime);
        });
    }

    @DisplayName("negative frameTime constructor")
    @Test
    void negativeTimeConstructor() {
        List<Texture> frames = new ArrayList<>();
        frames.add(mock(Texture.class));
        int fTime = -2;

        assertThrows(IllegalArgumentException.class, () -> {
            this.animation = new Animation(frames, fTime);
        });
    }

    //-----------getNextAnimation--------------
    @DisplayName("getNextAnimation needs to get next")
    @Test
    // TODO: macht das überhaupt Sinn mit currntFrameIndex 1?
    void getNextTextureNeeded() throws NoSuchFieldException, IllegalAccessException {
        Texture texture1 = mock(Texture.class);
        Texture texture2 = mock(Texture.class);
        ArrayList<Texture> frames = new ArrayList<>();
        frames.add(texture1);
        frames.add(texture2);
        int fTime = 0; // texture needs to change
        this.animation = new Animation(frames, fTime);
        // think that first texture was already loaded
        Field animationField = Animation.class.getDeclaredField("currentFrameIndex");
        animationField.setAccessible(true);
        animationField.setInt(this.animation, 1);

        Texture next = this.animation.getNextAnimationTexture();

        assertEquals(next, texture2); // first texture loaded
    }

    @DisplayName("getNextAnimation not time to switch")
    @Test
    void getNextTextureNotNeeded() throws NoSuchFieldException, IllegalAccessException {
        Texture texture1 = mock(Texture.class);
        Texture texture2 = mock(Texture.class);
        ArrayList<Texture> frames = new ArrayList<>();
        frames.add(texture1);
        frames.add(texture2);
        int fTime = 10; // texture does not need to change
        this.animation = new Animation(frames, fTime);

        Texture next = this.animation.getNextAnimationTexture();

        assertEquals(next, texture1);
    }

}
