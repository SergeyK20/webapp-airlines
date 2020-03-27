package dto;

import com.google.protobuf.Empty;

public class Plane extends Entity<Integer> {
    private String name_plane;
    private TypePlane typePlane;

    public Plane(){
        super();
    }

    public Plane(int id, String name_plane, TypePlane typePlane){
        super(id);
        this.name_plane = name_plane;
        this.typePlane = typePlane;
    }

    public String getName_plane(){
        return name_plane;
    }

    public void setName_plane(String name_plane){
        this.name_plane = name_plane;
    }

    public TypePlane getTypePlane() {
        return typePlane;
    }

    public void setTypePlane(TypePlane typePlane) {
        this.typePlane = typePlane;
    }

    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(name_plane).append(" ").
                append(typePlane);
        return str.toString();
    }
}
