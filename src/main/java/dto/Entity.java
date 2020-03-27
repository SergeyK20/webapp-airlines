package dto;

public abstract class Entity<K> {
    private K id;

    public Entity(){}
    public Entity (K id){
        this.id = id;
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
}
