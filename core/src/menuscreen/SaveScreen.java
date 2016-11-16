package menuscreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import gamescreen.GameScreen;

/**
 * Created by nick on 11/16/2016.
 */
public class SaveScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private TextButton saveButton;
    private TextField userName;
    private TextButton backButton;
    private GameScreen gameScreen;

    public SaveScreen(){
        create();
    }

    private void create(){
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        saveButton = new TextButton("SAVE", skin);
        userName = new TextField("",skin);
        gameScreen = new GameScreen();

        backButton = new TextButton("MAIN", skin);
        backButton.setSize(100,50);
        backButton.setPosition(0, Gdx.graphics.getHeight() - backButton.getHeight());
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
            }
        });

        userName.setSize(250,100);
        userName.setPosition(Gdx.graphics.getWidth()/2 - userName.getWidth()/2,
                Gdx.graphics.getHeight()/2 + userName.getHeight()/4);

        saveButton.setSize(250,100);
        saveButton.setPosition(Gdx.graphics.getWidth()/2 - saveButton.getWidth()/2,
                Gdx.graphics.getHeight()/2 - saveButton.getHeight());

        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                gameScreen.highScore.putString("UserName", userName.getText());
                gameScreen.highScore.flush();
            }
        });

        stage.addActor(userName);
        stage.addActor(saveButton);
        stage.addActor(backButton);

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
