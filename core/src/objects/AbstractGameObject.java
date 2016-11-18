package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Abstract class for game objects
 */
public abstract class AbstractGameObject {
    public Vector2 position;

    public AbstractGameObject(){
        position = new Vector2();
    }

    public abstract Texture getTexture();
}

