package module6;

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
import processing.core.PGraphics;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {
	
	// We will use member variables, instead of local variables, to store the data
	// that the setUp and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = false;
//	private static final boolean offline = true;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	PGraphics buffer;
	PGraphics buffer2;
	PGraphics buffer3;
	private int mapOffsetX = 20;
	private int mapOffsetY = 20;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;
	
	// NEW IN MODULE 5
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	public void setup() {		
		// (1) Initializing canvas and map tiles
		buffer = createGraphics(860, 660);
		buffer2 = createGraphics(860, 660);
		buffer3 = createGraphics(860, 660);
		size(900, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, mapOffsetX, mapOffsetY, 860, 660, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
//			map = new UnfoldingMap(this, 200, 50, 650, 600, new Google.GoogleMapProvider());
			map = new UnfoldingMap(this, mapOffsetX, mapOffsetY, 860, 660, new Microsoft.HybridProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		map.zoomLevel(1);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// FOR TESTING: Set earthquakesURL to be one of the testing files by uncommenting
		// one of the lines below.  This will work whether you are online or offline
		//earthquakesURL = "test1.atom";
//		earthquakesURL = "test2.atom";
		
		// Uncomment this line to take the quiz
//		earthquakesURL = "quiz2.atom";
		
		
		// (2) Reading in earthquake data and geometric properties
	    //     STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		//     STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) {
		  cityMarkers.add(new CityMarker(city));
		}
	    
		//     STEP 3: read in earthquake RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
		  //check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }

	    // could be used for debugging
	    printQuakes();
	    sortAndPrint(20);
	 		
	    // (3) Add markers to map
	    //     NOTE: Country markers are not added to the map.  They are used
	    //           for their geometric properties
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	    
	    
	}  // End setup
	
	private void showTileInBuffer() {
		for(Marker marker : cityMarkers) {
			CityMarker cm = (CityMarker)marker;
			if(cm.isSelected() && !cm.isHidden()) {
				cm.showTitle2(buffer, mouseX-mapOffsetX, mouseY-mapOffsetY);
				tint(255, 200);
				image(buffer, mapOffsetX,mapOffsetY);
				noTint();
			}
//			if(cm.getClicked()) {
//				cm.showStatis2(buffer3, mouseX-mapOffsetX, mouseY-mapOffsetY);
//				tint(255, 200);
//				image(buffer3, mapOffsetX,mapOffsetY); 	
//				noTint();
//			}
		}
		
		for(Marker marker : quakeMarkers) {
			EarthquakeMarker em = (EarthquakeMarker)marker;
			if(em.isSelected() && !em.isHidden()) {
				em.showTitle2(buffer, mouseX-mapOffsetX, mouseY-mapOffsetY);
				image(buffer, mapOffsetX,mapOffsetY); 	
			}
			
//			if(!em.isOnLand && em.getClicked()) {
//				for (Marker cmTmp : cityMarkers) {
//					CityMarker cm = (CityMarker)cmTmp;
//					if (cm.getDistanceTo(em.getLocation()) <= em.threatCircle()) {
//						em.showLine(buffer, map.getScreenPosition(em.getLocation()).x-mapOffsetX,
//								map.getScreenPosition(em.getLocation()).y-mapOffsetY,
//								map.getScreenPosition(cm.getLocation()).x-mapOffsetX,
//								map.getScreenPosition(cm.getLocation()).y-mapOffsetY);
//						image(buffer, mapOffsetX,mapOffsetY); 	
//					}
//				}
//			}
		}
	}
	
	private void showStatisInbuffer() {
		for(Marker marker : cityMarkers) {
			CityMarker cm = (CityMarker)marker;
//			if(cm.isSelected() && !cm.isHidden()) {
//				cm.showTitle2(buffer, mouseX-mapOffsetX, mouseY-mapOffsetY);
//				tint(255, 200);
//				image(buffer, mapOffsetX,mapOffsetY);
//				noTint();
//			}
			if(cm.getClicked()) {
				cm.showStatis2(buffer3, mouseX-mapOffsetX, mouseY-mapOffsetY);
				tint(255, 200);
				image(buffer3, mapOffsetX,mapOffsetY); 	
				noTint();
			}
		}
		
//		for(Marker marker : quakeMarkers) {
//			EarthquakeMarker em = (EarthquakeMarker)marker;
//			if(em.isSelected() && !em.isHidden()) {
//				em.showTitle2(buffer, mouseX-mapOffsetX, mouseY-mapOffsetY);
//				image(buffer, mapOffsetX,mapOffsetY); 	
//			}
//			
//			if(!em.isOnLand && em.getClicked()) {
//				for (Marker cmTmp : cityMarkers) {
//					CityMarker cm = (CityMarker)cmTmp;
//					if (cm.getDistanceTo(em.getLocation()) <= em.threatCircle()) {
//						em.showLine(buffer, map.getScreenPosition(em.getLocation()).x-mapOffsetX,
//								map.getScreenPosition(em.getLocation()).y-mapOffsetY,
//								map.getScreenPosition(cm.getLocation()).x-mapOffsetX,
//								map.getScreenPosition(cm.getLocation()).y-mapOffsetY);
//						image(buffer, mapOffsetX,mapOffsetY); 	
//					}
//				}
//			}
//		}
	}
	
	private void showOceanQuakeLineInbuffer() {
//		for(Marker marker : cityMarkers) {
//			CityMarker cm = (CityMarker)marker;
//			if(cm.isSelected() && !cm.isHidden()) {
//				cm.showTitle2(buffer, mouseX-mapOffsetX, mouseY-mapOffsetY);
//				tint(255, 200);
//				image(buffer, mapOffsetX,mapOffsetY);
//				noTint();
//			}
//			if(cm.getClicked()) {
//				cm.showStatis2(buffer3, mouseX-mapOffsetX, mouseY-mapOffsetY);
//				tint(255, 200);
//				image(buffer3, mapOffsetX,mapOffsetY); 	
//				noTint();
//			}
//		}
		
		for(Marker marker : quakeMarkers) {
			EarthquakeMarker em = (EarthquakeMarker)marker;
//			if(em.isSelected() && !em.isHidden()) {
//				em.showTitle2(buffer, mouseX-mapOffsetX, mouseY-mapOffsetY);
//				image(buffer, mapOffsetX,mapOffsetY); 	
//			}
			
			if(!em.isOnLand && em.getClicked()) {
				for (Marker cmTmp : cityMarkers) {
					CityMarker cm = (CityMarker)cmTmp;
					if (cm.getDistanceTo(em.getLocation()) <= em.threatCircle()) {
						em.showLine(buffer, map.getScreenPosition(em.getLocation()).x-mapOffsetX,
								map.getScreenPosition(em.getLocation()).y-mapOffsetY,
								map.getScreenPosition(cm.getLocation()).x-mapOffsetX,
								map.getScreenPosition(cm.getLocation()).y-mapOffsetY);
						image(buffer, mapOffsetX,mapOffsetY); 	
					}
				}
			}
		}
	}
	
	private void showKeyInBuffer() {
		addKey2(buffer2);
		tint(255, 200);
		image(buffer2, mapOffsetX,mapOffsetY);
		noTint();
	}
	
	
	public void draw() {
		background(198,198,198);
		map.draw();
		showTileInBuffer();
		showStatisInbuffer();
		showOceanQuakeLineInbuffer();
		showKeyInBuffer();
	}
	
	
	// TODO: Add the method:
	//   private void sortAndPrint(int numToPrint)
	// and then call that method from setUp
	private void sortAndPrint(int numToPrint) {
		Object[] ret = quakeMarkers.toArray();
//		Collections.sort(arraylist, Collections.reverseOrder());
		Arrays.sort(ret, Collections.reverseOrder());
		int loopNum = 0;
		
		if(numToPrint > ret.length) {
			loopNum = ret.length;
		}else {
			loopNum = numToPrint;
		}
		
		for(int k=0;k<loopNum;k++) {
			System.out.println(((EarthquakeMarker) ret[k]).getTitle());
		}
	}
	
	private void BiggestEarthquake(int numToPrint) {
		List<Marker> quakeMarkers2 = new ArrayList<Marker>();
		for(Marker m : quakeMarkers) {
			quakeMarkers2.add(m);
		}
		Collections.sort(quakeMarkers2, Collections.reverseOrder());
		int loopNum = 0;
		if(numToPrint > quakeMarkers2.size()) {
			loopNum = quakeMarkers2.size();
		}else {
			loopNum = numToPrint;
		}
		for(int k=0;k<loopNum;k++) {
			quakeMarkers2.get(k).setHidden(false);
		}
	}
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(quakeMarkers);
		selectMarkerIfHover(cityMarkers);
		//loop();
	}
	
	// If there is a marker selected 
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	/** The event handler for mouse clicks
	 * It will display an earthquake and its threat circle of cities
	 * Or if a city is clicked, it will display all the earthquakes 
	 * where the city is in the threat circle
	 */
	@Override
	public void mouseClicked()
	{
		if (lastClicked != null) {
			lastClicked.setClicked(false);
			unhideMarkers();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkEarthquakesForClick();
			if (lastClicked == null) {
				checkCitiesForClick();
			}
		}
	}
	
	
	public void keyPressed()
	{
		if (key == 'a') {
			for(Marker marker : quakeMarkers) {
				marker.setHidden(false);
			}
				
			for(Marker marker : cityMarkers) {
				marker.setHidden(false);
			}
		}
		else if (key == 'b') 
		{
			for(Marker marker : quakeMarkers) {
				marker.setHidden(true);
			}
				
			for(Marker marker : cityMarkers) {
				marker.setHidden(false);
			}
		}
		else if (key == 'c') 
		{
			for(Marker marker : quakeMarkers) {
				EarthquakeMarker em = (EarthquakeMarker)marker;
				if(em.isOnLand) {
					marker.setHidden(false);
				}else {
					marker.setHidden(true);
				}
			}
				
			for(Marker marker : cityMarkers) {
				marker.setHidden(true);
			}
		}
		else if (key == 'd') 
		{
			for(Marker marker : quakeMarkers) {
				EarthquakeMarker em = (EarthquakeMarker)marker;
				if(em.isOnLand) {
					marker.setHidden(true);
				}else {
					marker.setHidden(false);
				}
			}
				
			for(Marker marker : cityMarkers) {
				marker.setHidden(true);
			}
		}
		else if (key == 'e') 
		{
			for(Marker marker : quakeMarkers) {
				marker.setHidden(true);
			}
				
			for(Marker marker : cityMarkers) {
				marker.setHidden(true);
			}
			
			BiggestEarthquake(10);
		}
	}
	
	// Helper method that will check if a city marker was clicked on
	// and respond appropriately
	private void checkCitiesForClick()
	{
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker marker : cityMarkers) {
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker)marker;
				lastClicked.setClicked(true);
				// Hide all the other earthquakes and hide
				for (Marker mhide : cityMarkers) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				int threatEarthquakeNum = 0;
				float magSum = 0.0f;
				float maxMag = 0.0f;
				for (Marker mhide : quakeMarkers) {
					EarthquakeMarker quakeMarker = (EarthquakeMarker)mhide;
					if (quakeMarker.getDistanceTo(marker.getLocation()) 
							> quakeMarker.threatCircle()) {
						quakeMarker.setHidden(true);
					}else {
						threatEarthquakeNum += 1;
						magSum += quakeMarker.getMagnitude();
						if (quakeMarker.getMagnitude() > maxMag) {
							maxMag = quakeMarker.getMagnitude();
						}
					}
				}
				((CityMarker)lastClicked).threatEarthquakeNum = threatEarthquakeNum;
				((CityMarker)lastClicked).threatEarthquakeAvgMag = magSum/threatEarthquakeNum;
				((CityMarker)lastClicked).threatEarthquakeMaxMag = maxMag;
				return;
			}
		}		
	}
	
	// Helper method that will check if an earthquake marker was clicked on
	// and respond appropriately
	private void checkEarthquakesForClick()
	{
		if (lastClicked != null) return;
		// Loop over the earthquake markers to see if one of them is selected
		for (Marker m : quakeMarkers) {
			EarthquakeMarker marker = (EarthquakeMarker)m;
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = marker;
				lastClicked.setClicked(true);
				// Hide all the other earthquakes and hide
				for (Marker mhide : quakeMarkers) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				for (Marker mhide : cityMarkers) {
					if (mhide.getDistanceTo(marker.getLocation()) 
							> marker.threatCircle()) {
						mhide.setHidden(true);
					}
				}
				return;
			}
		}
	}
	
	// loop over and unhide all markers
	private void unhideMarkers() {
		for(Marker marker : quakeMarkers) {
			marker.setHidden(false);
		}
			
		for(Marker marker : cityMarkers) {
			marker.setHidden(false);
		}
	}
	
	// helper method to draw key in GUI
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		fill(255, 250, 240);

		int xbase = 85;
		int ybase = 50;
		rect(xbase, ybase, 150, 250);
		
		fill(0);
		textAlign(LEFT, CENTER);
		textSize(12);
		text("Earthquake Key", xbase+25, ybase+25);
		
		fill(150, 30, 30);
		int tri_xbase = xbase + 35;
		int tri_ybase = ybase + 50;
		triangle(tri_xbase, tri_ybase-CityMarker.TRI_SIZE, tri_xbase-CityMarker.TRI_SIZE, 
				tri_ybase+CityMarker.TRI_SIZE, tri_xbase+CityMarker.TRI_SIZE, 
				tri_ybase+CityMarker.TRI_SIZE);

		fill(0, 0, 0);
		textAlign(LEFT, CENTER);
		text("City Marker", tri_xbase + 15, tri_ybase);
		
		text("Land Quake", xbase+50, ybase+70);
		text("Ocean Quake", xbase+50, ybase+90);
		text("Size ~ Magnitude", xbase+25, ybase+110);
		
		fill(255, 255, 255);
		ellipse(xbase+35, 
				ybase+70, 
				10, 
				10);
		rect(xbase+35-5, ybase+90-5, 10, 10);
		
		fill(color(255, 255, 0));
		ellipse(xbase+35, ybase+140, 12, 12);
		fill(color(0, 0, 255));
		ellipse(xbase+35, ybase+160, 12, 12);
		fill(color(255, 0, 0));
		ellipse(xbase+35, ybase+180, 12, 12);
		
		textAlign(LEFT, CENTER);
		fill(0, 0, 0);
		text("Shallow", xbase+50, ybase+140);
		text("Intermediate", xbase+50, ybase+160);
		text("Deep", xbase+50, ybase+180);

		text("Past hour", xbase+50, ybase+200);
		
		fill(255, 255, 255);
		int centerx = xbase+35;
		int centery = ybase+200;
		ellipse(centerx, centery, 12, 12);

		strokeWeight(2);
		line(centerx-8, centery-8, centerx+8, centery+8);
		line(centerx-8, centery+8, centerx+8, centery-8);
		
		
	}

	private void addKey2(PGraphics pg) {	
		// Remember you can use Processing's graphics methods here
		pg.beginDraw();
		
		pg.fill(51, 153, 255);
//		pg.fill(0, 153, 155);
//		pg.fill(30);
		int xbase = 25;
		int ybase = 50;
		pg.rect(xbase, ybase, 240, 325);
		
		pg.fill(150, 30, 30);
		int tri_xbase = xbase + 35;
		int tri_ybase = ybase + 50;
		pg.triangle(tri_xbase, tri_ybase-CityMarker.TRI_SIZE, tri_xbase-CityMarker.TRI_SIZE, 
				tri_ybase+CityMarker.TRI_SIZE, tri_xbase+CityMarker.TRI_SIZE, 
				tri_ybase+CityMarker.TRI_SIZE);

		pg.fill(0);
		pg.textAlign(LEFT, CENTER);
		pg.textSize(14);
		pg.text("Earthquake Key", xbase+25, ybase+25);
		pg.textAlign(LEFT, CENTER);
		pg.text("City Marker", tri_xbase + 15, tri_ybase);
		pg.text("Land Quake", xbase+50, ybase+70);
		pg.text("Ocean Quake", xbase+50, ybase+90);
		pg.text("Size ~ Magnitude", xbase+25, ybase+110);
		pg.textAlign(LEFT, CENTER);
		pg.text("Shallow", xbase+50, ybase+140);
		pg.text("Intermediate", xbase+50, ybase+160);
		pg.text("Deep", xbase+50, ybase+180);
		pg.text("Past hour", xbase+50, ybase+200);	
		pg.textSize(12);
		pg.text("Press 'a' show all marker", xbase+10, ybase+230);
		pg.text("Press 'b' show all city marker", xbase+10, ybase+250);
		pg.text("Press 'c' show all onland eqMarker", xbase+10, ybase+270);
		pg.text("Press 'd' show all notOnLand eqMarker", xbase+10, ybase+290);
		pg.text("Press 'e' show 10 biggest eqMarker", xbase+10, ybase+310);
		
		pg.fill(255, 255, 255);
		pg.ellipse(xbase+35, 
				ybase+70, 
				10, 
				10);
		pg.rect(xbase+35-5, ybase+90-5, 10, 10);
		
		pg.fill(color(255, 255, 0));
		pg.ellipse(xbase+35, ybase+140, 12, 12);
		pg.fill(color(0, 0, 255));
		pg.ellipse(xbase+35, ybase+160, 12, 12);
		pg.fill(color(255, 0, 0));
		pg.ellipse(xbase+35, ybase+180, 12, 12);
		

		
		pg.fill(255, 255, 255);
		int centerx = xbase+35;
		int centery = ybase+200;
		pg.ellipse(centerx, centery, 12, 12);

		pg.strokeWeight(2);
		pg.line(centerx-8, centery-8, centerx+8, centery+8);
		pg.line(centerx-8, centery+8, centerx+8, centery-8);
		
		pg.endDraw();
	}
	
	
	// Checks whether this quake occurred on land.  If it did, it sets the 
	// "country" property of its PointFeature to the country where it occurred
	// and returns true.  Notice that the helper method isInCountry will
	// set this "country" property already.  Otherwise it returns false.
	private boolean isLand(PointFeature earthquake) {
		
		// IMPLEMENT THIS: loop over all countries to check if location is in any of them
		// If it is, add 1 to the entry in countryQuakes corresponding to this country.
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		
		// not inside any country
		return false;
	}
	
	// prints countries with number of earthquakes
	// You will want to loop through the country markers or country features
	// (either will work) and then for each country, loop through
	// the quakes to count how many occurred in that country.
	// Recall that the country markers have a "name" property, 
	// And LandQuakeMarkers have a "country" property set.
	private void printQuakes() {
		int totalWaterQuakes = quakeMarkers.size();
		for (Marker country : countryMarkers) {
			String countryName = country.getStringProperty("name");
			int numQuakes = 0;
			for (Marker marker : quakeMarkers)
			{
				EarthquakeMarker eqMarker = (EarthquakeMarker)marker;
				if (eqMarker.isOnLand()) {
					if (countryName.equals(eqMarker.getStringProperty("country"))) {
						numQuakes++;
					}
				}
			}
			if (numQuakes > 0) {
				totalWaterQuakes -= numQuakes;
				System.out.println(countryName + ": " + numQuakes);
			}
		}
		
		System.out.println("OCEAN QUAKES: " + totalWaterQuakes);
		
/*		for (Marker marker : quakeMarkers) {
			EarthquakeMarker eqMarker = (EarthquakeMarker)marker;
			System.out.println(eqMarker.getProperties());
		}*/
	}
	
	
	
	// helper method to test whether a given earthquake is in a given country
	// This will also add the country property to the properties of the earthquake feature if 
	// it's in one of the countries.
	// You should not have to modify this code
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {
				
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {
					
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
						
					// return if is inside one
					return true;
				}
			}
		}
			
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			
			return true;
		}
		return false;
	}

}

