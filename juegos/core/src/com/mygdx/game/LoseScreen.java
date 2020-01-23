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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class LoseScreen implements Screen {
        
        final tubo game;
	OrthographicCamera camera;
     
        public int scoreMax;
        Texture imagenDerrota;
        Music musicaDerrota;
        ScoreClas score;
         ScoreClas scoreAntiguo;
         MainM m;
         int supera;
         
	public LoseScreen(final tubo gam,int nuevoRecor) {
		game = gam;
                 musicaDerrota = Gdx.audio.newMusic(Gdx.files.internal("derrota.mp3"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
                imagenDerrota = new Texture(Gdx.files.internal("final.png"));
                musicaDerrota.play();
                scoreMax = MainM.scoreActual;
                this.supera= nuevoRecor;
                if(scoreMax < nuevoRecor){           
                    NuevoFicheroPokemon(nuevoRecor);
                 }
                
               
	}
         static void  CrearElemento(String datoEmple, String valor,Element raiz, Document document){

            Element elem = document.createElement(datoEmple); 
            Text text = document.createTextNode(valor); //damos valor
            raiz.appendChild(elem); //pegamos el elemento hijo a la raiz
            elem.appendChild(text); //pegamos el valor
        }
        public void NuevoFicheroPokemon(int nuevoRecor){
         
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{

          DocumentBuilder builder = factory.newDocumentBuilder();
          DOMImplementation implementation = builder.getDOMImplementation();
          Document document = implementation.createDocument(null, "SCORE", null);
          document.setXmlVersion("1.0"); 

              Element raiz = document.createElement("score");  
              document.getDocumentElement().appendChild(raiz);
              
          CrearElemento("scores", Integer.toString(nuevoRecor), raiz, document); 

          Source source = new DOMSource(document);
          Result result =  new StreamResult(new java.io.File("C:\\Users\\Infante96\\Downloads\\juegos\\core\\Score.xml"));        
          Transformer transformer = TransformerFactory.newInstance().newTransformer();
          transformer.transform(source, result);

         }catch(ParserConfigurationException | TransformerException | DOMException e){ } 

        }
	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
                  
		game.batch.begin();
                game.batch.draw(imagenDerrota,0,0,800,480);
		game.font.draw(game.batch, "Game Over..!!! ", 100, 300);
		game.font.draw(game.batch, "Toca para  volver a empezar!", 100, 100);
                game.font.draw(game.batch, "Score : "+ this.supera, 100, 200);
		game.batch.end();
                
		if (Gdx.input.isTouched()) {
			game.setScreen(new gameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
            musicaDerrota.play();
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
            musicaDerrota.dispose();
	}

}