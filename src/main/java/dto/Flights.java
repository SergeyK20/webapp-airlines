package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flights extends Entity<Integer> {
    private int id_airlines;
    private Route route;
    private LocalDate date;
    private LocalTime time;
    private Plane plane;

    public Flights(){
        super();
    }

    public Flights(int id, int id_airlines, Route route, LocalDate date, LocalTime time, Plane plane){
        super(id);
        this.id_airlines = id_airlines;
        this.route = route;
        this.date = date;
        this.time = time;
        this.plane = plane;
    }

    public int getId_airlines(){
        return id_airlines;
    }

    public void setId_airlines(int id_airlines){
        this.id_airlines = id_airlines;
    }

    public Route getRoute(){
        return route;
    }

    public void setRoute(Route route){
        this.route = route;
    }

    public Plane getPlane(){
        return plane;
    }

    public void setPlane(Plane plane){
        this.plane = plane;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalTime getTime(){
        return time;
    }

    public void setTime(LocalTime time){
        this.time = time;
    }

    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(id_airlines).append(" ").
                append(route).append(" ").
                append(date.toString()).append(" ").
                append(time.toString()).append(" ").
                append(plane);
        return str.toString();
    }
}
