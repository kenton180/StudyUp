package map;

public class Location {

	private double lat;
	private double lon;

	public Location(double lat, double lon) {
		this.setLat(lat);
		this.lon = lon;
		// TODO Auto-generated constructor stub
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
