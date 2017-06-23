package it.polito.tdp.flight.model;

public class AirportAndDistance implements Comparable<AirportAndDistance>{

	private Airport a;
	private int distance;
	
	
	public AirportAndDistance(Airport a, int distance) {
		super();
		this.a = a;
		this.distance = distance;
	}
	public Airport getA() {
		return a;
	}
	public void setA(Airport a) {
		this.a = a;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	@Override
	public int compareTo(AirportAndDistance arg0) {
		// TODO Auto-generated method stub
		return this.distance-arg0.distance;
	}
	public String toString(){
		return a.toString()+" "+distance;
	}
	
}
