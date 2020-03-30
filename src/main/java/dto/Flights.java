package dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flights extends Entity<Integer> {
    private int idAirlines;
    private Route route;
    private LocalDate date;
    private LocalTime time;
    private Plane plane;

    public Flights(){
        super();
    }

    public Flights(int id, int idAirlines, Route route, LocalDate date, LocalTime time, Plane plane){
        super(id);
        this.idAirlines = idAirlines;
        this.route = route;
        this.date = date;
        this.time = time;
        this.plane = plane;
    }

    public int getIdAirlines(){
        return idAirlines;
    }

    public void setIdAirlines(int idAirlines){
        this.idAirlines = idAirlines;
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
        str.append(idAirlines).append(" ").
                append(route).append(" ").
                append(date.toString()).append(" ").
                append(time.toString()).append(" ").
                append(plane);
        return str.toString();
    }
}
