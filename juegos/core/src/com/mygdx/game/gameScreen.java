/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

/**
 *
 * @author Infante96
 */
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class gameScreen implements Screen {
    
  	final tubo game;

	Texture dropImageup;
        Texture dropImagendown;
        Texture imagenFondo;
	Texture bucketImage;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	Rectangle bucket;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGathered;
        public Music musicaFondo;
         
        final float GRAVITY = -28f;
        float yVelocity = 0;

	public gameScreen(final tubo gam) {
            
		this.game = gam;
                musicaFondo = Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.mp3"));

		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImagendown = new Texture(Gdx.files.internal("e.png"));
                dropImageup = new Texture(Gdx.files.internal("eup.png"));
                imagenFondo =  new Texture(Gdx.files.internal("fondoInicio.jpg"));
		bucketImage = new Texture(Gdx.files.internal("panda.png"));
                musicaFondo.setLooping(true);
                musicaFondo.play();
	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                
		bucket = new Rectangle();
		bucket.x = 100;
		bucket.y = 400; 
				
		bucket.width = 64;
		bucket.height = 62;
             
		// create the raindrops array and spawn the first raindrop
		raindrops = new Array();
		spawnRaindrop();

	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = 800;
		//posicion de la gota en salida
                raindrop.y = MathUtils.random(-300, -100);
		raindrop.width = 92;
		raindrop.height = 349;
		raindrops.add(raindrop);
                
                Rectangle raindrop2 = new Rectangle();
		raindrop2.x = 800;
		//posicion de la gota en salida
                raindrop2.y = raindrop.y + 520;
		raindrop2.width = 92;
		raindrop2.height = 349;
		raindrops.add(raindrop2);
		lastDropTime = TimeUtils.nanoTime();
	}
        
        public void RangoXY(){
        
            if(bucket.y < 0) bucket.y = 0;
            
            if(bucket.y > 480 - 64) bucket.y = 480 - 64;
        
        }
        
        public void saltoPajaro(){
            
      
            if (Gdx.input.isTouched()) {
                    Vector3 touchPos = new Vector3();
                    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(touchPos);
                    bucket.y = touchPos.y - 64 / 2;
            }


             if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                    bucket.y += 600 *  Gdx.graphics.getDeltaTime();
                    yVelocity = 300;
            }   
  
        }
	@Override
	public void render(float delta) {
		
 
            camera.update();

            // tell the SpriteBatch to render in the
            // coordinate system specified by the camera.
            game.batch.setProjectionMatrix(camera.combined);

            // begin a new batch and draw the bucket and
            // all drops
            game.batch.begin();
            game.batch.draw(imagenFondo,0,0,800,480); 
            game.font.draw(game.batch, "Score: " + dropsGathered, 0, 480);
            game.batch.draw(bucketImage, bucket.x, bucket.y);
            int contador = 1;
            for (Rectangle raindrop : raindrops) {
                //gotas horizontal
                if(contador % 2 == 0){
                  game.batch.draw(dropImageup, raindrop.x, raindrop.y);  
                } else {
                  game.batch.draw(dropImagendown, raindrop.x, raindrop.y);  
                }

                contador++;

            }

            game.batch.end();

            int tiempoTuberia = 0;

            if(dropsGathered > 10 && dropsGathered <= 15){
                tiempoTuberia = 800000000;
            } else if(dropsGathered > 15){
                tiempoTuberia = 500000000;
            } else {
                tiempoTuberia = 1000000000;
            }


            if (TimeUtils.nanoTime() - lastDropTime > tiempoTuberia)
                    spawnRaindrop();


            saltoPajaro();
            RangoXY();


            yVelocity = yVelocity + GRAVITY;
            float y = bucket.getY();

            float yChange = yVelocity * delta;
            bucket.setPosition(0, y + yChange);

            Boolean ahoraCuento = false;



            Iterator<Rectangle> iter = raindrops.iterator();
            while (iter.hasNext()) {
                Rectangle raindrop = iter.next();
                    if(dropsGathered < 10){
                        raindrop.x -= 500 * Gdx.graphics.getDeltaTime(); 
                    } 
                    if (dropsGathered < 20 && dropsGathered >= 10){
                        raindrop.x -= 600 * Gdx.graphics.getDeltaTime(); 
                    } 
                   if (dropsGathered >= 20){
                        raindrop.x -= 700 * Gdx.graphics.getDeltaTime(); 
                    }
                    //cuando choc
                    if(raindrop.x + 64 < 0){
                        iter.remove();
                        if(ahoraCuento == false){
                          dropsGathered++;
                          ahoraCuento = true;

                        }else {
                            ahoraCuento = false;
                        }

                    }
                    if(bucket.y <= 0){
                       game.setScreen(new LoseScreen(game,dropsGathered));
                       musicaFondo.dispose();
                        dispose(); 
                    }

                    if(raindrop.overlaps(bucket)){
                        game.setScreen(new LoseScreen(game,dropsGathered));
                        musicaFondo.dispose();
                        dispose();
                    }
            }
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		musicaFondo.play();
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
		dropImagendown.dispose();
		bucketImage.dispose();
//		dropSound.dispose();
		musicaFondo.dispose();
	}

}
