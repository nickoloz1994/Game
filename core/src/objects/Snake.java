package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * The class is used for creating snake object
 */
public class Snake extends AbstractGameObject {
    private Texture texture;

    public Snake(){
        init();
    }

    private void init(){
        texture = new Texture(Gdx.files.internal("images/snakehead.png"));
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}
