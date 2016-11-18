package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class is used for creating apple objects
 */
public class Apple extends AbstractGameObject{
    private Texture texture;

    public Apple(){
        init();
    }

    private void init(){
        texture = new Texture(Gdx.files.internal("images/apple.png"));
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
