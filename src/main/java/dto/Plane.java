package dto;


public class Plane extends Entity<Integer> {
    private String namePlane;
    private TypePlane typePlane;

    public Plane(){
        super();
    }

    public Plane(int id, String namePlane, TypePlane typePlane){
        super(id);
        this.namePlane = namePlane;
        this.typePlane = typePlane;
    }

    public String getNamePlane(){
        return namePlane;
    }

    public void setNamePlane(String namePlane){
        this.namePlane = namePlane;
    }

    public TypePlane getTypePlane() {
        return typePlane;
    }

    public void setTypePlane(TypePlane typePlane) {
        this.typePlane = typePlane;
    }

    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(namePlane).append(" ").
                append(typePlane);
        return str.toString();
    }
}
