package gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
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
    private Texture snakeBody;
    private Array<SnakeBody> bodyParts = new Array<SnakeBody>();
    private float timer = Constants.MOVE_TIME;
    private int snakeDirection = Constants.RIGHT;
    private boolean appleAvailable = false;
    private float snakeXBeforeUpdate = 0;
    private float snakeYBeforeUpdate = 0;
    private boolean directionSet = false;
    private boolean hasHit = false;
    Apple appleObject;
    Snake snake;

    private enum STATE {
        PLAYING,
        GAME_OVER;
    }

    private STATE state = STATE.PLAYING;

    private class SnakeBody {
        private float x, y;
        private Texture texture;

        public SnakeBody(Texture texture) {
            this.texture = texture;
        }

        public void updateBodyPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void draw(Batch batch) {
            if (!(x == snake.position.x && y == snake.position.y))
                batch.draw(texture, x, y);
        }
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        snakeHead = new Texture(Gdx.files.internal("images/snakehead.png"));
        apple = new Texture(Gdx.files.internal("images/apple.png"));
        snakeBody = new Texture(Gdx.files.internal("images/snakebody.png"));
        snake = new Snake();
        appleObject = new Apple();
    }

    private void queryInput() {
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (leftPressed) updateDirection(Constants.LEFT);
        if (rightPressed) updateDirection(Constants.RIGHT);
        if (upPressed) updateDirection(Constants.UP);
        if (downPressed) updateDirection(Constants.DOWN);
    }

    private void moveSnake() {
        snakeXBeforeUpdate = snake.position.x;
        snakeYBeforeUpdate = snake.position.y;
        switch (snakeDirection) {
            case Constants.RIGHT: {
                snake.position.x += Constants.SNAKE_MOVEMENT;
                return;
            }
            case Constants.LEFT: {
                snake.position.x -= Constants.SNAKE_MOVEMENT;
                return;
            }
            case Constants.UP: {
                snake.position.y += Constants.SNAKE_MOVEMENT;
                return;
            }
            case Constants.DOWN: {
                snake.position.y -= Constants.SNAKE_MOVEMENT;
                return;
            }
        }
    }

    private void updateIfNotOppositeDirection(int newDirection, int oppDirection) {
        if (snakeDirection != oppDirection || bodyParts.size == 0)
            snakeDirection = newDirection;
    }

    private void updateDirection(int newDirection) {
        if (!directionSet && snakeDirection != newDirection) {
            directionSet = true;
            switch (newDirection) {
                case Constants.LEFT: {
                    updateIfNotOppositeDirection(newDirection, Constants.RIGHT);
                }
                break;
                case Constants.RIGHT: {
                    updateIfNotOppositeDirection(newDirection, Constants.LEFT);
                }
                break;
                case Constants.UP: {
                    updateIfNotOppositeDirection(newDirection, Constants.DOWN);
                }
                break;
                case Constants.DOWN: {
                    updateIfNotOppositeDirection(newDirection, Constants.UP);
                }
                break;
            }
        }
    }

    private void updateSnakeBodyPosition() {
        if (!hasHit) {
            if (bodyParts.size > 0) {
                SnakeBody part = bodyParts.removeIndex(0);
                part.updateBodyPosition(snakeXBeforeUpdate, snakeYBeforeUpdate);
                bodyParts.add(part);
            }
        }
    }

    private void updateSnake(float delta) {
        timer -= delta;
        if (timer <= 0) {
            timer = Constants.MOVE_TIME;
            moveSnake();
            checkForOutOfBounds();
            updateSnakeBodyPosition();
            checkBodyCollision();
            directionSet = false;
        }
    }

    private void checkForOutOfBounds() {
        if (snake.position.x >= Gdx.graphics.getWidth()) {
            snake.position.x = 0;
        }
        if (snake.position.x < 0) {
            snake.position.x = Gdx.graphics.getWidth() - Constants.SNAKE_MOVEMENT;
        }
        if (snake.position.y >= Gdx.graphics.getHeight()) {
            snake.position.y = 0;
        }
        if (snake.position.y < 0) {
            snake.position.y = Gdx.graphics.getHeight() - Constants.SNAKE_MOVEMENT;
        }
    }

    private void checkAndPlaceApple() {
        if (!appleAvailable) {
            do {
                appleObject.position.x = MathUtils.random(Gdx.graphics.getWidth()
                        / Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                appleObject.position.y = MathUtils.random(Gdx.graphics.getHeight()
                        / Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                appleAvailable = true;
            } while (appleObject.position.x == snake.position.x
                    && appleObject.position.y == snake.position.y);
        }
    }

    private void checkAppleCollision() {
        if (appleAvailable && appleObject.position.x == snake.position.x
                && appleObject.position.y == snake.position.y) {
            SnakeBody part = new SnakeBody(snakeBody);
            part.updateBodyPosition(snake.position.x, snake.position.y);
            bodyParts.insert(0, part);
            appleAvailable = false;
        }
    }

    private void checkBodyCollision() {
        for (SnakeBody part : bodyParts) {
            if (part.x == snake.position.x && part.y == snake.position.y)
                state = STATE.GAME_OVER;
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        batch.begin();
        batch.draw(snakeHead, snake.position.x, snake.position.y);
        for (SnakeBody body : bodyParts) {
            body.draw(batch);
        }
        if (appleAvailable) {
            batch.draw(apple, appleObject.position.x, appleObject.position.y);
        }
        batch.end();
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case PLAYING: {
                queryInput();
                updateSnake(delta);
                checkAppleCollision();
                checkAndPlaceApple();
            }
            break;
            case GAME_OVER: {

            }
            break;
        }
        clearScreen();
        draw();
    }
}
