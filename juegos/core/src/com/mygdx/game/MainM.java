/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class MainM implements Screen {
        
        final tubo game;
	OrthographicCamera camera;
        public int scoreMax;
        Texture inicioImage;
        Music musicaInicio;
        ScoreClas c;
       static int  scoreActual;

     public void MainM() {
       
    }
        
    public ScoreClas getC() {
        return c;
    }

    public void setC(ScoreClas c) {
        this.c = c;
    }

    public static int getScoreActual() {
        return scoreActual;
    }

    public void setScoreActual(int scoreActual) {
        this.scoreActual = scoreActual;
    }
  
	public MainM(final tubo gam) throws SAXException, IOException {
		game = gam;
                musicaInicio = Gdx.audio.newMusic(Gdx.files.internal("inicio.mp3"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                inicioImage = new Texture(Gdx.files.internal("inicio.jpg"));
                musicaInicio.play();
                musicaInicio.setLooping(true);
                c = new ScoreClas();
             
                XMLReader  procesadorXML = XMLReaderFactory.createXMLReader();
                BD entra = new BD();  
                entra.setScore(c);
                procesadorXML.setContentHandler(entra);
                InputSource fileXML = new InputSource("C:\\Users\\Infante96\\Downloads\\juegos\\core\\Score.xml");
                //Esta linea la primera vez tiene que ejecuto el codigo si no hay nada en el  xml se comenta
                procesadorXML.parse(fileXML);
                scoreActual = c.getScoreMax();
	}
	@Override
	public void render(float delta) {
            

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
                game.batch.draw(inicioImage,0,0,800,480);
		game.font.draw(game.batch, "Bienvenidos a FLAPPY BEAR...!!! ", 100,300);
		game.font.draw(game.batch, "Toca para empezar!", 100, 100);
                game.font.draw(game.batch, "RECORD : "+c.getScoreMax(), 100, 200);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new gameScreen(game));
			dispose();
                        musicaInicio.dispose();
                        
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
            musicaInicio.dispose();
	}
}