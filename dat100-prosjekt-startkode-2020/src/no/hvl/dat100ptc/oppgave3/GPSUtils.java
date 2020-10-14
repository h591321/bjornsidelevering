package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double[] latPoints=new double[gpspoints.length];
		
		for(int i=0;i<gpspoints.length;i++) {
			double latPoint=gpspoints[i].getLatitude();
			latPoints[i]=latPoint;
		}
		return latPoints;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longPoints=new double[gpspoints.length];
		
		for(int i=0;i<gpspoints.length;i++) {
			double longPoint=gpspoints[i].getLongitude();
			longPoints[i]=longPoint;
		}
		return longPoints;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		
		double phi1=Math.toRadians(gpspoint1.getLatitude());
		double phi2=Math.toRadians(gpspoint2.getLatitude());
		double dPhi=Math.toRadians(gpspoint2.getLatitude()-gpspoint1.getLatitude());
		double dLam=Math.toRadians(gpspoint2.getLongitude()-gpspoint1.getLongitude());
		double a=Math.pow(Math.sin(dPhi/2), 2)+Math.cos(phi1)*Math.cos(phi2)*Math.pow(Math.sin(dLam/2), 2);
		double c=2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
		double d=c*R;
		
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {


		double distance=distance(gpspoint1,gpspoint2);
		int secs=gpspoint2.getTime()-gpspoint1.getTime();
		double speed=distance*3.6/secs;
		
		return speed;

	}

	public static String formatTime(int secs) {

		String strHr, strMin, strSek;
		String timestr;
		String TIMESEP = ":";
		
        int sek = secs % 60;
        int hrs = secs / 60;
        int min = hrs % 60;
        hrs = hrs / 60;

        strHr=String.format("%02d", hrs);
        strMin=String.format("%02d", min);
        strSek=String.format("%02d", sek);
		
		timestr="  "+strHr+TIMESEP+strMin+TIMESEP+strSek;
		return timestr;
		
	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
		
		String str=String.format("%"+TEXTWIDTH+".2f", d);
		
		return str;
		
		
		
	}
}
