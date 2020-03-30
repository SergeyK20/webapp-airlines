package dto;

public class TypePlane extends Entity<Integer> {
    private String nameType;

    public TypePlane(){super();}

    public TypePlane(int id, String nameType){
        super(id);
        this.nameType = nameType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String toString(){
        return nameType;
    }
}
