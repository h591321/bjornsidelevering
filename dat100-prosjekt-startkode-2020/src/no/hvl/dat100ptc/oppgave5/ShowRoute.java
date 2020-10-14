package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE/ (Math.abs(maxlon - minlon)); 

		return xstep;
	}
 
	// antall y-pixels per breddegrad
	public double ystep() {
	
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double ystep = MAPYSIZE / (Math.abs(maxlat - minlat)); 

		return ystep;
	}

	public void showRouteMap(int ybase) {
		double[] latTab=GPSUtils.getLatitudes(gpspoints);
		double[] lonTab=GPSUtils.getLongitudes(gpspoints);

		double ymin=GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double xmin=GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		//x= a + (X - A) * (b - a) / (B - A)
		//x=a+(X-A)*ystep/xstep

		for(int i=0;i<gpspoints.length-1;i++) {
			
			
			double ypunktD=latTab[i];
			int yPunkt=(int)(ybase-50-(ypunktD-ymin)*ystep());
			
			double y2punktD=latTab[i+1];
			int y2Punkt=(int)(ybase-50-(y2punktD-ymin)*ystep());
			
			double xpunktD=lonTab[i];
			int xPunkt=(int)(329370+(xpunktD-xmin)*xstep());
			
			double x2punktD=lonTab[i+1];
			int x2Punkt=(int)(329370+(x2punktD-xmin)*xstep());
			
			
			
			setColor(0,255,0);	
			int sid=fillCircle(xPunkt,yPunkt, 3);
			drawLine(xPunkt,yPunkt,x2Punkt,y2Punkt);
			
			setColor(0,0,255);
			moveCircle(sid,x2Punkt,y2Punkt);
			
			//hjelpe linjer
	    	//drawLine(200,200,xPunkt,200);
	    	//drawLine(200,200,200,yPunkt);
			
			
		}
		
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;
		setColor(0,0,0);
		int length=16+10;
		setFont("Courier",12);
		
		drawString(gpscomputer.returnStatistics().substring(0, length+1),25,25);
		drawString(gpscomputer.returnStatistics().substring(length, length*2+4),25,25+TEXTDISTANCE);
		drawString(gpscomputer.returnStatistics().substring(length*2+3, length*3+6),25,25+TEXTDISTANCE*2);
		drawString(gpscomputer.returnStatistics().substring(length*3+5,length*4+11),25,25+TEXTDISTANCE*3);
		drawString(gpscomputer.returnStatistics().substring(length*4+10, length*5+16),25,25+TEXTDISTANCE*4);
		drawString(gpscomputer.returnStatistics().substring(length*5+15),25,25+TEXTDISTANCE*5);

	}
	//emma
}
