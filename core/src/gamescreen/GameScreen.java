package gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import objects.Snake;
import utils.Constants;

/**
 * Created by nick on 11/14/2016.
 */
public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private Texture snakeHead;
    private float timer = Constants.MOVE_TIME;
    private int snakeDirection = Constants.RIGHT;
    Snake snake;

    @Override
    public void show(){
        batch = new SpriteBatch();
        snakeHead = new Texture(Gdx.files.internal("images/snakehead.png"));
        snake = new Snake();
    }

    private void checkForOutOfBounds(){
        if(snake.position.x >= Gdx.graphics.getWidth()){
            snake.position.x = 0;
        }
        if (snake.position.x < 0){
            snake.position.x = Gdx.graphics.getWidth() - Constants.SNAKE_MOVEMENT;
        }
        if (snake.position.y >= Gdx.graphics.getHeight()){
            snake.position.y = 0;
        }
        if (snake.position.y < 0){
            snake.position.y = Gdx.graphics.getHeight() - Constants.SNAKE_MOVEMENT;
        }
    }

    private void moveSnake(){
        switch (snakeDirection){
            case Constants.RIGHT:{
                snake.position.x += Constants.SNAKE_MOVEMENT;
                return;
            }
            case Constants.LEFT:{
                snake.position.x -= Constants.SNAKE_MOVEMENT;
                return;
            }
            case Constants.UP:{
                snake.position.y += Constants.SNAKE_MOVEMENT;
                return;
            }
            case Constants.DOWN:{
                snake.position.y -= Constants.SNAKE_MOVEMENT;
                return;
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        timer -= delta;
        if (timer <= 0){
            timer = Constants.MOVE_TIME;
            moveSnake();
            checkForOutOfBounds();
        }
        batch.begin();
        batch.draw(snakeHead, snake.position.x, snake.position.y);
        batch.end();
    }
}
