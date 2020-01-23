/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *
 * @author Infante96
 */
public class BD extends  DefaultHandler {	 
            
            ScoreClas e;
            String fichero = "";
            int score;
            
            public void setScore(ScoreClas c) {
                this.e = c;
            }
            public BD() {
	        super();
	    }	    
	    public void startDocument() {
	       // System.out.println("Comienzo del Documento XML");
               
	    }	    
	    public void endDocument() {
	       // System.out.println("Final del Documento XML");
	   
            }	 
	    public void startElement(String uri, String nombre,String nombreC, Attributes atts) {
	      //  System.out.printf("\t Principio Elemento: %s %n",nombre);
                fichero = nombre;     
                if(fichero.equals("scores")){
                
                }

	    } 	
	    public void endElement(String uri, String nombre,String nombreC) {
	       // System.out.printf("\tFin Elemento: %s %n", nombre);
                 fichero = nombre;
                
	    }	
	    public void characters(char[] ch, int inicio, int longitud) throws SAXException {
		   String car = new String(ch, inicio, longitud);
               //quitar saltos de línea	
		   car = car.replaceAll("[\t\n]","");	   
		   //System.out.printf ("\t Caracteres: %s %n", car);
                   
                    if(fichero.equals("scores")){
                        System.out.println("Entaaa");
                       e.setScoreMax(Integer.parseInt(car));
                   }
                    
	    }
       
}


