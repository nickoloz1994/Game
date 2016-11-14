package menuscreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import gamescreen.GameScreen;

/**
 * Created by nick on 11/14/2016.
 */
public class MenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Game game;
    private Table table;
    private TextButton newGameButton;
    private TextButton levelButton;
    private TextButton exitButton;

    public MenuScreen(Game game){
        show();
        this.game = game;
    }

    public MenuScreen(){
        create();
    }

    public void create(){
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table();

        newGameButton = new TextButton("NEW GAME",skin);
        levelButton = new TextButton("LEVEL", skin);
        exitButton = new TextButton("EXIT", skin);

        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        levelButton.addListener(new ClickListener(){});

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());
        table.padTop(50);
        table.add(newGameButton).padBottom(30);
        table.row();
        table.add(levelButton).padBottom(30);
        table.row();
        table.add(exitButton);
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
