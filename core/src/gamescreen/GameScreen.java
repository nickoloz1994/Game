package gamescreen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import menuscreen.MenuScreen;
import menuscreen.SaveScreen;
import objects.Apple;
import objects.GoldCoin;
import objects.Player;
import objects.Snake;
import utils.Constants;


/**
 * Snake Game
 *
 * @author Nika Ptskialadze
 */
public class GameScreen extends ScreenAdapter {

    //For drawing objects on the screen
    private SpriteBatch batch;

    //For drawing snake's body parts
    private Texture snakeBody;

    private BitmapFont bitmapFont;

    //For displaying live score and final score
    private GlyphLayout over = new GlyphLayout();
    private GlyphLayout liveScore = new GlyphLayout();

    //Variable for storing the value of score
    private int score = 0;

    //Variable for counting apples
    private int counter = -1;

    private Viewport viewport;
    private Camera camera;

    //Array for storing snake's body parts
    private Array<SnakeBody> bodyParts = new Array<SnakeBody>();

    private float timer = Constants.MOVE_TIME;
    private float temp = Constants.MOVE_TIME;

    private int snakeDirection = Constants.RIGHT;

    private boolean appleAvailable = false;
    private boolean coinAvailable = false;
    private boolean collidedWithApple = false;
    private boolean collidedWithCoin = false;

    private float snakeXBeforeUpdate = 0;
    private float snakeYBeforeUpdate = 0;

    private boolean directionSet = false;

    private Apple appleObject;
    private Snake snake;
    private GoldCoin goldCoin;

    //For storing high score
    public Preferences highScore = Gdx.app.getPreferences("HighScore");

    //Game states
    private enum STATE {
        PLAYING,
        GAME_OVER
    }

    private STATE state = STATE.PLAYING;

    //Inner class for snake's body parts
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

    /**
     * Initializes all the objects
     * **/
    @Override
    public void show() {
        batch = new SpriteBatch();
        snakeBody = new Texture(Gdx.files.internal("images/snakebody.png"));
        snake = new Snake();
        appleObject = new Apple();
        goldCoin = new GoldCoin();
        bitmapFont = new BitmapFont();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, 0);
        camera.update();
    }

    /**
     * Checks which direction button was pressed and updates
     * direction of the snake accordingly
     * **/
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

    /**
     * Moves snake according to the direction
     * **/
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

    /**
     * Updates snake's direction if a new direction is not
     * the opposite of current direction
     * <p>
     *     @param newDirection new selected direction by player.
     *     @param oppDirection opposite direction of current direction
     * </p>
     * **/
    private void updateIfNotOppositeDirection(int newDirection, int oppDirection) {
        if (snakeDirection != oppDirection || bodyParts.size == 0)
            snakeDirection = newDirection;
    }

    /**
     * Updates snake's direction to new direction if they are not equal
     * <p>
     *     @param newDirection new direction selected by player
     * </p>
     * **/
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

    /**
     * Updates position of snake's body parts
     * **/
    private void updateSnakeBodyPosition() {
        if (bodyParts.size > 0) {
            SnakeBody part = bodyParts.removeIndex(0);
            part.updateBodyPosition(snakeXBeforeUpdate, snakeYBeforeUpdate);
            bodyParts.add(part);
        }
    }

    /**
     * Sets the value of timer to given value
     * <p>
     *     @param x new value set to timer
     * </p>
     * **/
    public void setTimer(float x) {
        timer = x;
    }

    /**
     * Sets temporary value
     * <p>
     *     @param x value set to temporary value
     * </p>
     * **/
    public void setTemp(float x) {
        temp = x;
    }

    /**
     * Updates snake, moves it, checks if it is out of bounds,
     * updates position of body parts, checks for collisions
     * with its own body
     * <p>
     *     @param delta time since the last update
     * </p>**/
    private void updateSnake(float delta) {
        timer -= delta;
        if (timer <= 0) {
            timer = temp;
            moveSnake();
            checkForOutOfBounds();
            updateSnakeBodyPosition();
            checkBodyCollision();
            directionSet = false;
        }
    }

    /**
     * Checks if snake is out of bounds and brings it on the
     * screen from the opposite bound
     * **/
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

    /**
     * Checks availability of apple and sets its position coordinates
     * **/
    private void checkAndPlaceApple() {
        if (!appleAvailable) {
            do {
                appleObject.position.x = MathUtils.random(Gdx.graphics.getWidth()
                        / Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                appleObject.position.y = MathUtils.random(Gdx.graphics.getHeight()
                        / Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                appleAvailable = true;
                counter++;
            } while (appleObject.position.x == snake.position.x
                    && appleObject.position.y == snake.position.y);
        }
    }

    /**
     * Checks for collisions with apple. If collision detected,
     * increases score and body length
     * **/
    private void checkAppleCollision() {
        if (appleAvailable && appleObject.position.x == snake.position.x
                && appleObject.position.y == snake.position.y) {
            SnakeBody part = new SnakeBody(snakeBody);
            part.updateBodyPosition(snake.position.x, snake.position.y);
            bodyParts.insert(0, part);
            collidedWithApple = true;
            collidedWithCoin = false;
            addToScore();
            appleAvailable = false;

        }
    }

    /**
     * Checks the number of apples placed before.
     * If 5 apples have been placed before,
     * sets position coordinates for bonus coin
     * and resets the counter for apples
     * **/
    private void checkAndPlaceCoin() {
        if (counter == 5) {
            counter = 0;
            do {
                goldCoin.position.x = MathUtils.random(Gdx.graphics.getWidth()
                        / Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                goldCoin.position.y = MathUtils.random(Gdx.graphics.getHeight()
                        / Constants.SNAKE_MOVEMENT - 1) * Constants.SNAKE_MOVEMENT;
                coinAvailable = true;
            } while (goldCoin.position.x == snake.position.x
                    && goldCoin.position.y == snake.position.y);
        }
    }

    /**
     * Checks for collisions with bonus coin.
     * If collision detected increases score
     * and body length of snake
     * **/
    private void checkCoinCollision() {
        if (coinAvailable && goldCoin.position.x == snake.position.x
                && goldCoin.position.y == snake.position.y) {
            SnakeBody part = new SnakeBody(snakeBody);
            part.updateBodyPosition(snake.position.x, snake.position.y);
            bodyParts.insert(0, part);
            collidedWithCoin = true;
            collidedWithApple = false;
            addToScore();
            coinAvailable = false;
        }
    }

    /**
     * Increases score with corresponding value
     * **/
    private void addToScore() {
        if (collidedWithApple) {
            score += Constants.POINTS_PER_APPLE;
        }
        if (collidedWithCoin) {
            score += Constants.POINTS_PER_COIN;
        }
    }

    /**
     * Draws score in the top right corner during the game
     * **/
    private void drawScore() {
        if (state == STATE.PLAYING) {
            String scoreToString = Integer.toString(score);
            liveScore.setText(bitmapFont, scoreToString);
            bitmapFont.draw(batch, scoreToString, Gdx.graphics.getWidth() - liveScore.width,
                    Gdx.graphics.getHeight() - liveScore.height);
        }
    }

    /**
     * Checks for collisions with body parts.
     * If collision detected, changes game state
     * to GAME OVER, assigns player a final score
     * and if it is a new high score, stores it
     * in high scores
     * **/
    private void checkBodyCollision() {
        for (SnakeBody part : bodyParts) {
            if (part.x == snake.position.x && part.y == snake.position.y) {
                state = STATE.GAME_OVER;
                Player player = new Player();
                player.score = score;
                if (player.score > highScore.getInteger("HighScore")) {
                    highScore.putString("HighScore", Integer.toString(score));
                    highScore.flush();
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new SaveScreen());
                }
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Draws objects on the game screen
     * **/
    private void draw() {
        batch.begin();
        batch.draw(snake.getTexture(), snake.position.x, snake.position.y);
        for (SnakeBody body : bodyParts) {
            body.draw(batch);
        }
        if (appleAvailable) {
            batch.draw(appleObject.getTexture(), appleObject.position.x, appleObject.position.y);
        }
        if (coinAvailable) {
            batch.draw(goldCoin.getTexture(), goldCoin.position.x, goldCoin.position.y);
        }
        if (state == STATE.GAME_OVER) {
            over.setText(bitmapFont, Constants.GAME_OVER_TEXT);
            bitmapFont.draw(batch, Constants.GAME_OVER_TEXT, (viewport.getWorldWidth() - over.width) / 2,
                    (viewport.getWorldHeight() - over.height) / 2);
        }
        drawScore();
        batch.end();
    }

    private void checkForRestart() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            doRestart();
    }

    /**
     * Resets everything and takes player to main menu
     * **/
    private void doRestart() {
        state = STATE.PLAYING;
        bodyParts.clear();
        snakeDirection = Constants.RIGHT;
        directionSet = false;
        timer = Constants.MOVE_TIME;
        snake.position.x = 0;
        snake.position.y = 0;
        snakeXBeforeUpdate = 0;
        snakeYBeforeUpdate = 0;
        score = 0;
        appleAvailable = false;
        ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case PLAYING: {
                queryInput();
                updateSnake(delta);
                checkAppleCollision();
                checkCoinCollision();
                checkAndPlaceCoin();
                checkAndPlaceApple();
            }
            break;
            case GAME_OVER: {
                checkForRestart();
            }
            break;
        }
        clearScreen();
        draw();
    }
}