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
    private static final String TAG = SaveScreen.class.getName();
    private Stage stage;
    private Skin skin;
    private TextButton saveButton;
    private TextField userName;
    private TextButton mainButton;
    private Table table;

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
        table = new Table(skin);

        mainButton = new TextButton("MAIN", skin);
        mainButton.setSize(100,50);
        mainButton.setPosition(0, Gdx.graphics.getHeight() - mainButton.getHeight());
        mainButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                Gdx.app.log(TAG, "Main Menu button pressed");
            }
        });

        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
                Gdx.app.log(TAG, "New entry saved");
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
        table.add(mainButton);
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
