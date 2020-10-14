package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for(int i=0;i<gpspoints.length-1;i++) {
			distance+=GPSUtils.distance(gpspoints[i],gpspoints[i+1]);
		}
		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;
		
		for(int i=0;i<gpspoints.length-1;i++) {
			double elevationAB=gpspoints[i+1].getElevation()-gpspoints[i].getElevation();
			if(elevationAB>0) {
			elevation+=elevationAB;
			}
		}
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		
		int time=gpspoints[gpspoints.length-1].getTime()-gpspoints[0].getTime();
			
		return time;
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		double[] gsnittV = new double[gpspoints.length-1];
		for(int i=0;i<gpspoints.length-1;i++) {
			gsnittV[i]=GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
		}
		return gsnittV;
	}
	
	public double maxSpeed() {

		double[] gsnittfart=speeds();
		double max=GPSUtils.findMax(gsnittfart);
		return max;
	}

	public double averageSpeed() {

		double average = 0;
		
		double distance=totalDistance();
		int time=totalTime();
		average=distance*3.6/time;
		return average;
		
	}


		public static double MS = 2.236936;

		public double kcal(double weight, int secs, double speed) {

	
		double speedmph = speed * MS;
		int MET;

		if(speedmph<10) {MET=4;}
		else if(speedmph>10&&speedmph<12) {MET=6;}
		else if(speedmph>12&&speedmph<14) {MET=8;}
		else if(speedmph>14&&speedmph<16) {MET=10;}
		else if(speedmph>18&&speedmph<20) {MET=12;}
		else {MET=16;}
		
		return MET*weight*secs/3600;
		}
		

	public double totalKcal(double weight) {
		
		double totalkcal = 0;

		int time=totalTime();
		double speed=averageSpeed();
		totalkcal=kcal(weight,time,speed);
		
		
		return totalkcal;
		
		
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {
		
		String tid=GPSUtils.formatTime(totalTime());
		String avstand=GPSUtils.formatDouble(totalDistance());
		String oppover=GPSUtils.formatDouble(totalElevation());
		String gFart=GPSUtils.formatDouble(averageSpeed());
		String mFart=GPSUtils.formatDouble(maxSpeed());
		String energi=GPSUtils.formatDouble(totalKcal(WEIGHT));
		
		System.out.println("==============================================");
		
		System.out.println("Total Time     :"+tid);
		System.out.println("Total distance :"+avstand+" km");
		System.out.println("Total elevation:"+oppover+" m");
		System.out.println("Max speed      :"+gFart+" km/t");
		System.out.println("Average speed  :"+mFart+" km/t");
		System.out.println("Energy         :"+energi+" kcal");
		
		System.out.println("==============================================");
	}
	public String returnStatistics() {
		String tid=GPSUtils.formatTime(totalTime());
		String avstand=GPSUtils.formatDouble(totalDistance());
		String oppover=GPSUtils.formatDouble(totalElevation());
		String gFart=GPSUtils.formatDouble(averageSpeed());
		String mFart=GPSUtils.formatDouble(maxSpeed());
		String energi=GPSUtils.formatDouble(totalKcal(WEIGHT));
		String tidStr="Total Time     :"+tid;
		String disStr="Total distance :"+avstand+" km";
		String eleStr="Total elevation:"+oppover+" m";
		String mxvStr="Max speed      :"+gFart+" km/t";
		String avgStr="Average speed  :"+mFart+" km/t";
		String eStr="Energy         :"+energi+" kcal ";
		String str=tidStr+disStr+eleStr+mxvStr+avgStr+eStr;
		return str;
	}

}
