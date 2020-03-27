package dto;

public class Route extends Entity<Integer> {
    private City to;
    private City from;
    private int travelTimeMinutes;

    public Route(){}

    public Route(int id, City to, City from, int travelTimeMinutes){
        super(id);
        this.to = to;
        this.from = from;
        this.travelTimeMinutes = travelTimeMinutes;
    }

    public City getTo() {
        return to;
    }

    public City getFrom() {
        return from;
    }

    public int getTravelTimeMinutes() {
        return travelTimeMinutes;
    }

    public void setTo(City to) {
        this.to = to;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    public void setTravelTimeMinutes(int travelTimeMinutes) {
        this.travelTimeMinutes = travelTimeMinutes;
    }

    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(to).append(" ").
                append(from).append(" ").
                append(travelTimeMinutes);
        return str.toString();
    }
}
