package module6;

import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

public class Test extends PApplet {
/*	float rotateAmount;
	int boxColorR = 255;
	int boxColorG = 255;
	int boxColorB = 255;
	int boxW = 480;
	public void setup () {
	  size(640,480);
	  rectMode(CENTER);

	}

	void drawText() {
	  //translate(width/2,height/2);
	  textAlign(LEFT, CENTER);
	  fill(255, 255, 255);
	  textSize(32);
	  text("RED: " + boxColorR,width/2,height/2);
	  text("GREEN: " + boxColorG,width/2,height/2+30);
	  text("BLUE: " + boxColorB,width/2,height/2+60);
	  text("Box Width: " + boxW,width/2,height/2+90); 
	}

	void drawBox() {
	  translate(width/2,height/2);
	  rotateAmount += 12;
	  if (boxColorR <= 0) {
	    boxColorG--;
	  }
	  if (boxColorG <= 0) {
	    boxColorB--;
	  }
	  boxColorR--;
	  boxW--;
	  rotateAmount += .05;
	  rotate(rotateAmount);
	  fill(boxColorR,boxColorG,boxColorB);
	  rect(0,0,boxW,boxW);
	  resetMatrix();

	}



	public void draw() {
	    //rect(width/2,height/2,640,480); //this solves the text overlapping but erases the cool effect
	    drawBox();
	    drawText();
	}*/
	
/*	float rotateAmount;
	int boxColorR = 255;
	int boxColorG = 255;
	int boxColorB = 255;
	int boxW = 480;

	//create a buffer to draw boxes to
	PGraphics buffer;

	public void setup () {
	  size(640, 480);

	  buffer = createGraphics(640, 480);
	}

	void drawText() {
	  //translate(width/2,height/2);
	  textAlign(LEFT, CENTER);
	  fill(255, 255, 255);
	  textSize(32);
	  text("RED: " + boxColorR, width/2, height/2);
	  text("GREEN: " + boxColorG, width/2, height/2+30);
	  text("BLUE: " + boxColorB, width/2, height/2+60);
	  text("Box Width: " + boxW, width/2, height/2+90);
	}

	//draw boxes to buffer
	void drawBox() {

	  buffer.beginDraw();
	  buffer.rectMode(CENTER);

	  buffer.translate(width/2, height/2);
	  rotateAmount += 12;
	  if (boxColorR <= 0) {
	    boxColorG--;
	  }
	  if (boxColorG <= 0) {
	    boxColorB--;
	  }
	  boxColorR--;
	  boxW--;
	  rotateAmount += .05;
	  buffer.rotate(rotateAmount);
	  buffer.fill(boxColorR, boxColorG, boxColorB);
	  buffer.rect(0, 0, boxW, boxW);
	  buffer.resetMatrix();

	  buffer.endDraw();
	}

	public void draw() {

	  //draw the boxes to the buffer
	  drawBox();
	  //draw the buffer to the screen
	  image(buffer, 0, 0);

	  //draw the text on top of the buffer
	  drawText();
	}*/
	
	
PGraphics pg;

public void setup() {
  size(100, 100);
  pg = createGraphics(40, 40);
}

public void draw() {
  pg.beginDraw();
  pg.background(100);
  pg.stroke(255);
  pg.line(20, 20, mouseX, mouseY);
  pg.endDraw();
  image(pg, 9, 30); 
  image(pg, 51, 30);
}
}

