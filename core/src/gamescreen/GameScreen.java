package gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import objects.Apple;
import objects.Snake;
import utils.Constants;

/**
 * Created by nick on 11/14/2016.
 */
public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;
    private Texture snakeHead;
    private Texture apple;
    private float timer = Constants.MOVE_TIME;
    private int snakeDirection = Constants.RIGHT;
    private boolean appleAvailable = false;
    Apple appleObject;
    Snake snake;

    @Override
    public void show(){
        batch = new SpriteBatch();
        snakeHead = new Texture(Gdx.files.internal("images/snakehead.png"));
        apple = new Texture(Gdx.files.internal("images/apple.png"));
        snake = new Snake();
        appleObject = new Apple();
    }

    private void queryInput(){
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) snakeDirection = Constants.LEFT;
        if (rightPressed) snakeDirection = Constants.RIGHT;
        if (upPressed) snakeDirection = Constants.UP;
        if (downPressed) snakeDirection = Constants.DOWN;
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

    private void checkAndPlaceApple(){
        if (!appleAvailable){
            do{
                appleObject.position.x = MathUtils.random(Gdx.graphics.getWidth()
                        /Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                appleObject.position.y = MathUtils.random(Gdx.graphics.getHeight()
                        /Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                appleAvailable = true;
            }while (appleObject.position.x == snake.position.x
                    && appleObject.position.y == snake.position.y);
        }
    }

    private void checkAppleCollision(){
        if (appleAvailable && appleObject.position.x == snake.position.x
                && appleObject.position.y == snake.position.y){
            appleAvailable = false;
        }
    }

    private void clearScreen(){
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw(){
        batch.begin();
        batch.draw(snakeHead, snake.position.x, snake.position.y);
        if (appleAvailable){
            batch.draw(apple, appleObject.position.x, appleObject.position.y);
        }
        batch.end();
    }

    @Override
    public void render(float delta) {
        queryInput();
        timer -= delta;
        if (timer <= 0){
            timer = Constants.MOVE_TIME;
            moveSnake();
            checkForOutOfBounds();
        }
        checkAppleCollision();
        checkAndPlaceApple();
        clearScreen();
        draw();
    }
}
