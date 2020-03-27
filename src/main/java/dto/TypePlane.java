package dto;

public class TypePlane extends Entity<Integer> {
    private String name_type;

    public TypePlane(){super();}

    public TypePlane(int id, String name_type){
        super(id);
        this.name_type = name_type;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }

    public String toString(){
        return name_type;
    }
}
