package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

public class tubo extends Game {

  	SpriteBatch batch;
	BitmapFont font;

          @Override
	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
              try {
                  this.setScreen(new MainM(this));
              } catch (SAXException ex) {
                  Logger.getLogger(tubo.class.getName()).log(Level.SEVERE, null, ex);
              } catch (IOException ex) {
                  Logger.getLogger(tubo.class.getName()).log(Level.SEVERE, null, ex);
              }
	}

          @Override
	public void render() {
		super.render(); // important!
	}

          @Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}