package servlets;

public enum EnumMethods {
    DROP("drop"),
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    SEARCH("search"),
    GET_LIST("getList"),
    GET_LIST_CITY("getListCity"),
    GET_LIST_TYPE("getListType"),
    GET_LIST_ROUTE_AND_PLANE("getListRouteAndPlane"),
    SORT("sort"),
    INSERT("insert"),
    GET_PAGE_CREATE_OR_EDIT("getPageCreateOrEdit");

    private String nameString;

    EnumMethods(String nameString){
        this.nameString = nameString;
    }

    public String getNameMethod(){
        return nameString;
    }
}
