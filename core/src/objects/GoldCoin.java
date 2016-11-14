package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by nick on 11/14/2016.
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