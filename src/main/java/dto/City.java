package dto;

public class City extends Entity<Integer> {
    private String nameCity;

    public City (){
        super();
    }

    public City(int id, String nameCity){
        super(id);
        this.nameCity = nameCity;
    }

    public String getNameCity(){
        return nameCity;
    }

    public void setNameCity(String nameCity){
        this.nameCity = nameCity;
    }

    public String toString(){
        return nameCity;
    }
}
