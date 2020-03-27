package dto;

public class City extends Entity<Integer> {
    private String name_city;

    public City (){
        super();
    }

    public City(int id, String name_city){
        super(id);
        this.name_city = name_city;
    }

    public String getName_city(){
        return name_city;
    }

    public void setName_city(String name_city){
        this.name_city = name_city;
    }

    public String toString(){
        return name_city;
    }
}
