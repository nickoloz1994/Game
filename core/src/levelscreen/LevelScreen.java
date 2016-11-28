package levelscreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gamescreen.GameScreen;
import menuscreen.MenuScreen;
import utils.Constants;

/**
 * The class is used for creating menu screen for levels
 */
public class LevelScreen implements Screen {
    private static final String TAG = LevelScreen.class.getName();
    private Stage stage;
    private Skin skin;
    private TextButton levelOne;
    private TextButton levelTwo;
    private TextButton levelThree;
    private TextButton levelFour;
    private TextButton levelFive;
    private TextButton backButton;
    private Table table;

    public LevelScreen(){
        create();
    }

    private void create(){
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        levelOne = new TextButton("LEVEL 1", skin);
        levelTwo = new TextButton("LEVEL 2", skin);
        levelThree = new TextButton("LEVEL 3", skin);
        levelFour = new TextButton("LEVEL 4", skin);
        levelFive = new TextButton("LEVEL 5", skin);
        backButton = new TextButton("BACK", skin);
        table = new Table();

        levelOne.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Level 1 chosen");
                GameScreen screen = new GameScreen();
                screen.setTemp(Constants.MOVE_TIME);
                ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
                Gdx.input.setInputProcessor(null);
            }
        });

        levelTwo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Level 2 chosen");
                GameScreen screen = new GameScreen();
                screen.setTimer(Constants.MOVE_TIME / 2);
                screen.setTemp(Constants.MOVE_TIME / 2);
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                Gdx.input.setInputProcessor(null);
            }
        });

        levelThree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Level 3 chosen");
                GameScreen screen = new GameScreen();
                screen.setTimer(Constants.MOVE_TIME / 4);
                screen.setTemp(Constants.MOVE_TIME / 4);
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                Gdx.input.setInputProcessor(null);
            }
        });

        levelFour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Level 4 chosen");
                GameScreen screen = new GameScreen();
                screen.setTimer(Constants.MOVE_TIME / 8);
                screen.setTemp(Constants.MOVE_TIME / 8);
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                Gdx.input.setInputProcessor(null);
            }
        });

        levelFive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Level 5 chosen");
                GameScreen screen = new GameScreen();
                screen.setTimer(Constants.MOVE_TIME / 16);
                screen.setTemp(Constants.MOVE_TIME / 16);
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                Gdx.input.setInputProcessor(null);
            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Back button clicked");
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });

        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());
        table.padTop(Gdx.graphics.getHeight() * 0.20f);
        table.add(levelOne).padBottom(30);
        table.row();
        table.add(levelTwo).padBottom(30);
        table.row();
        table.add(levelThree).padBottom(30);
        table.row();
        table.add(levelFour).padBottom(30);
        table.row();
        table.add(levelFive).padBottom(30);
        table.row();
        table.add(backButton);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    /*public GameScreen getScreen(){
        return screen;
    }*/
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
