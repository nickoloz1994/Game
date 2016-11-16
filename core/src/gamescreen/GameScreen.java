package gamescreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
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
import objects.Apple;
import objects.GoldCoin;
import objects.Snake;
import utils.Constants;


/**
 * Created by nick on 11/14/2016.
 */
public class GameScreen extends ScreenAdapter {

    private SpriteBatch batch;

    private Texture snakeBody;

    private BitmapFont bitmapFont;
    private GlyphLayout over = new GlyphLayout();
    private GlyphLayout liveScore = new GlyphLayout();

    private int score = 0;
    private int counter = -1;

    private Viewport viewport;
    private Camera camera;

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

    //private ArrayList<Player> highScores = new ArrayList<Player>(3);

    private enum STATE {
        PLAYING,
        GAME_OVER
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
        if (bodyParts.size > 0) {
            SnakeBody part = bodyParts.removeIndex(0);
            part.updateBodyPosition(snakeXBeforeUpdate, snakeYBeforeUpdate);
            bodyParts.add(part);
        }
    }

    public void setTimer(float x){
        timer = x;
    }

    public void setTemp(float x){
        temp = x;
    }

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
                counter++;
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
            collidedWithApple = true;
            collidedWithCoin = false;
            addToScore();
            appleAvailable = false;

        }
    }

    private void checkAndPlaceCoin(){
        if (counter == 5){
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

    private void checkCoinCollision(){
        if (coinAvailable && goldCoin.position.x == snake.position.x
                && goldCoin.position.y == snake.position.y){
            SnakeBody part = new SnakeBody(snakeBody);
            part.updateBodyPosition(snake.position.x, snake.position.y);
            bodyParts.insert(0, part);
            collidedWithCoin = true;
            collidedWithApple = false;
            addToScore();
            coinAvailable = false;
        }
    }

    private void addToScore(){
        if (collidedWithApple){
            score += Constants.POINTS_PER_APPLE;
        }
        if (collidedWithCoin){
            score += Constants.POINTS_PER_COIN;
        }
    }

    private void drawScore(){
        if (state == STATE.PLAYING){
            String scoreToString = Integer.toString(score);
            liveScore.setText(bitmapFont, scoreToString);
            bitmapFont.draw(batch, scoreToString, Gdx.graphics.getWidth()-liveScore.width,
                    Gdx.graphics.getHeight() - liveScore.height);
        }
    }

    private void checkBodyCollision() {
        for (SnakeBody part : bodyParts) {
            if (part.x == snake.position.x && part.y == snake.position.y)
                state = STATE.GAME_OVER;
            //player.score = Integer.toString(score);
            //checkScore(player, highScores);
        }
    }

    /*private void checkScore(Player player,ArrayList<Player> players){
        for (int i = 0; i < 3; i++){
            if (Integer.parseInt(players.get(0).score) < Integer.parseInt(player.score)){
                players.remove(2);
                players.add(0, player);
            }
            else if (Integer.parseInt(players.get(1).score) < Integer.parseInt(player.score)){
                players.remove(2);
                players.add(1, player);
            }
            else if (Integer.parseInt(players.get(2).score) < Integer.parseInt(player.score)){
                players.remove(2);
                players.add(2,player);
            }
        }
    }*/

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
        batch.begin();
        batch.draw(snake.getTexture(), snake.position.x, snake.position.y);
        for (SnakeBody body : bodyParts) {
            body.draw(batch);
        }
        if (appleAvailable){
            batch.draw(appleObject.getTexture(), appleObject.position.x, appleObject.position.y);
        }
        if (coinAvailable){
            batch.draw(goldCoin.getTexture(), goldCoin.position.x, goldCoin.position.y);
        }
        if (state == STATE.GAME_OVER) {
            over.setText(bitmapFont, Constants.GAME_OVER_TEXT);
            bitmapFont.draw(batch, Constants.GAME_OVER_TEXT, (viewport.getWorldWidth() - over.width)/2,
                    (viewport.getWorldHeight() - over.height)/2);
        }
        drawScore();
        batch.end();
    }

    private void checkForRestart(){
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            doRestart();
    }

    private void doRestart(){
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
        ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
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
