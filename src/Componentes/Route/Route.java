package Componentes.Route;

import Componentes.Cities.CitiesClass;

public class Route {
    private int id;
    private String origin;
    private String destination;
    private float distance;
    private String estimatedTravelTime;

    public Route(int id, String origin, String destination, float distance, CitiesClass city, String estimatedTravelTime) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.estimatedTravelTime = estimatedTravelTime;
    }



    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    
    public float getDistance() { return distance; }
    public void setDistance(float distance) { this.distance = distance; }
    
    public String getEstimatedTravelTime() { return estimatedTravelTime; }
    public void setEstimatedTravelTime(String estimatedTravelTime) { this.estimatedTravelTime = estimatedTravelTime; }

    @Override
    public String toString() {
        return  origin + " - " + destination ;
    }
    
    
   
}
