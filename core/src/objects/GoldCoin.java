package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * This class is used for creating gold coin objects
 */
public class GoldCoin extends AbstractGameObject {
    private Texture texture;

    public GoldCoin() {
        init();
    }

    private void init() {
        texture = new Texture(Gdx.files.internal("images/gold.png"));
    }

    @Override
    public Texture getTexture() {
        return texture;
    }
}