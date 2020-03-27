package servlets;

public enum EnumMethods {
    drop("drop"),
    create("create"),
    update("update"),
    delete("delete"),
    search("search"),
    getList("getList"),
    getListCity("getListCity"),
    getListType("getListType"),
    getListRouteAndPlane("getListRouteAndPlane"),
    sort("sort"),
    insert("insert"),
    getPageCreateOrEdit("getPageCreateOrEdit");

    private String nameString;

    EnumMethods(String nameString){
        this.nameString = nameString;
    }

    public String getNameMethod(){
        return nameString;
    }
}
