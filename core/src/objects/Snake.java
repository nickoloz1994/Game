package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by nick on 11/14/2016.
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
