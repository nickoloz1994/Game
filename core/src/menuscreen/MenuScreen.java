package menuscreen;

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
import levelscreen.LevelScreen;

/**
 * This class creates GUI for main menu
 */
public class MenuScreen implements Screen {
    private static final String TAG = MenuScreen.class.getName();
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton newGameButton;
    private TextButton highScoresButton;
    private TextButton exitButton;
    private TextButton aboutButton;

    /**
     * Constructor for creating main menu
     */
    public MenuScreen(){
        create();
    }

    /**
     * Creates main menu GUI
     * **/
    private void create(){
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table();

        newGameButton = new TextButton("NEW GAME",skin);
        highScoresButton = new TextButton("HIGH SCORE", skin);
        exitButton = new TextButton("EXIT", skin);
        aboutButton = new TextButton("ABOUT", skin);


        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new LevelScreen());
                Gdx.app.log(TAG, "New Game button clicked");
            }
        });

        highScoresButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new HighScore());
                Gdx.app.log(TAG, "High Scores button clicked");
            }
        });

        aboutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new AboutScreen());
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                Gdx.app.log(TAG, "Exit button clicked");
            }
        });

        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());
        table.padTop(Gdx.graphics.getHeight() * 0.33f);
        table.add(newGameButton).padBottom(30);
        table.row();
        table.add(highScoresButton).padBottom(30);
        table.row();
        table.add(aboutButton).padBottom(30);
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
