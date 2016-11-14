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
import utils.Constants;

/**
 * Created by nick on 11/14/2016.
 */
public class LevelScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private TextButton levelOne;
    private TextButton levelTwo;
    private TextButton levelThree;
    private TextButton levelFour;
    private TextButton levelFive;
    private Table table;
    private GameScreen screen = new GameScreen();

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
        table = new Table();

        levelOne.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                screen.setTemp(Constants.MOVE_TIME);
            }
        });

        levelTwo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                screen.setTimer(Constants.MOVE_TIME / 2);
                screen.setTemp(Constants.MOVE_TIME / 2);
            }
        });

        levelThree.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                screen.setTimer(Constants.MOVE_TIME / 4);
                screen.setTemp(Constants.MOVE_TIME / 4);
            }
        });

        levelFour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                screen.setTimer(Constants.MOVE_TIME / 8);
                screen.setTemp(Constants.MOVE_TIME / 8);
            }
        });

        levelFive.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
                screen.setTimer(Constants.MOVE_TIME / 16);
                screen.setTemp(Constants.MOVE_TIME / 16);
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
        table.add(levelFive);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }
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
