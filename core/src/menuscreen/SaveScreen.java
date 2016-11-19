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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import gamescreen.GameScreen;

/**
 * This class creates a screen for saving high score
 */
public class SaveScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private TextButton saveButton;
    private TextField userName;
    private TextButton backButton;
    private Table table;
    //private GameScreen gameScreen;

    /**
     * Constructor for creating save screen
     * **/
    public SaveScreen(){
        create();
    }

    /**
     * Creates GUI for saving screen
     * **/
    private void create(){
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        saveButton = new TextButton("SAVE", skin);
        userName = new TextField("",skin);
        //gameScreen = new GameScreen();
        table = new Table(skin);

        backButton = new TextButton("MAIN", skin);
        backButton.setSize(100,50);
        backButton.setPosition(0, Gdx.graphics.getHeight() - backButton.getHeight());
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });

        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                GameScreen.highScore.putString("UserName", userName.getText());
                GameScreen.highScore.flush();
            }
        });

        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());
        table.padTop(Gdx.graphics.getHeight() * 0.33f);
        table.add(userName).width(200).height(60).padBottom(30);
        table.row();
        table.add(saveButton).padBottom(30);
        table.row();
        table.add(backButton);
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
        stage.act(delta);
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
