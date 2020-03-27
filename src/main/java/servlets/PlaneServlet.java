package servlets;

import connection.ConnectionPool;
import dao.EnumNameField;
import dao.PlaneDAO;
import dao.TypePlaneDAO;
import dto.Plane;
import dto.TypePlane;
import exceptions.BlankFieldException;
import exceptions.DepartureAndArrivalCityAreTheSameException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaneServlet extends HttpServlet {
    String transitionPage;
    EnumMethods command;
    TypePlane typePlane;

    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        command = EnumMethods.valueOf(req.getParameter("command"));
        processRequest(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        command = EnumMethods.valueOf(req.getParameter("command"));
        processRequest(req, res);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res) {
        try (Connection connection = ConnectionPool.getConnection()) {
            actionSelectionToGet(req, res, connection);
        } catch (SQLException | DepartureAndArrivalCityAreTheSameException | BlankFieldException e) {
            req.setAttribute("errorMessage", e.getMessage());
            processExceptions(req,res);
        }
    }

    private void processExceptions(HttpServletRequest req, HttpServletResponse res)  {
        switch (command) {
            case create:
                transitionPage = "planeJSP/addPlane.jsp";
                command = EnumMethods.getListType;
                processRequest(req, res);
                break;
            case insert:
                transitionPage = req.getParameter("transitionPage");
                command = EnumMethods.getListType;
                processRequest(req, res);
                break;
            case delete:
            case search:
            case sort:
                command = EnumMethods.getList;
                processRequest(req, res);
                break;
            case update:
                transitionPage = "planeJSP/updatePlane.jsp";
                command = EnumMethods.getListType;
                processRequest(req, res);
                break;
            default:
                break;
        }
    }



    private void actionSelectionToGet(HttpServletRequest req, HttpServletResponse res, Connection connection) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        switch (command) {
            case getList:
            case drop:
                getList(req, res, connection);
                break;
            case getListType:
                getListType(req, res, connection);
                break;
            case create:
                create(req, res, connection);
                break;
            case delete:
                remove(req, res, connection);
                break;
            case update:
                update(req, res, connection);
                break;
            case search:
                search(req, res, connection);
                break;
            case sort:
                sort(req, res, connection);
                break;
            case insert:
                insert(req, res, connection);
            default:
                break;
        }
    }

    private void getList(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException {
        if((req.getAttribute("list") == null) || (req.getAttribute("list").equals(""))) {
            PlaneDAO type = new PlaneDAO(connection);
            List<Plane> list = type.findAll();
            req.setAttribute("list", list);
        }
        transitionPage = "planeJSP/plane.jsp";
        goToThePage(req, res);
    }

    private void goToThePage(HttpServletRequest req, HttpServletResponse res) throws IllegalStateException{
        try {
            System.out.println(transitionPage);
            req.getRequestDispatcher(transitionPage).forward(req, res);
            transitionPage = "";
        } catch (ServletException | IOException e) {
            System.out.println("Connection problem...");
            transitionPage = "";
        }
    }

    private void getListType(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException {
        String param = "";
        if(req.getParameter("transitionPage") != null) {
            transitionPage = req.getParameter("transitionPage");
        }
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))){
            param = "id";
        } else {
            param = "id_copy";
        }
        getFlightById(req, res, connection, param);
        TypePlaneDAO typePlaneDAO = new TypePlaneDAO(connection);
        List<TypePlane> list = typePlaneDAO.findAll();
        req.setAttribute("list", list);
        goToThePage(req, res);
    }

    /**
     * Передача по id.
     */
    private void getFlightById(HttpServletRequest req, HttpServletResponse res, Connection connection,String param) throws SQLException {
        if ((req.getParameter(param) != null) && (!req.getParameter(param).equals(""))) {
            PlaneDAO planeDAO = new PlaneDAO(connection);
            Plane plane = planeDAO.findEntityById(Integer.parseInt(req.getParameter(param)));
            req.setAttribute("plane", plane);
        }
    }


    private void create(HttpServletRequest req, HttpServletResponse res, Connection connection) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException {
        if (req.getParameter("name_plane").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField );
        } else {
            Plane plane = new Plane();
            PlaneDAO planeDAO = new PlaneDAO(connection);
            typePlane = new TypePlane();
            typePlane.setId(Integer.parseInt(req.getParameter("type_id")));
            plane.setTypePlane(typePlane);
            plane.setName_plane(req.getParameter("name_plane"));
            planeDAO.create(plane);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse res, Connection connection) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException {
        if (req.getParameter("name_plane").equals("") || req.getParameter("id").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField);
        } else {
            Plane plane = new Plane();
            System.out.println("update");
            PlaneDAO planeDAO = new PlaneDAO(connection);
            plane.setId(Integer.parseInt(req.getParameter("id")));
            typePlane = new TypePlane();
            typePlane.setId(Integer.parseInt(req.getParameter("type_id")));
            plane.setTypePlane(typePlane);
            plane.setName_plane(req.getParameter("name_plane"));
            planeDAO.update(plane);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("id") == null) {
            throw new BlankFieldException("No value selected");
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            PlaneDAO planeDAO = new PlaneDAO(connection);
            planeDAO.delete(id);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    private void insert(HttpServletRequest req, HttpServletResponse res, Connection connection) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))) {
            throw new BlankFieldException("No copy object");
        } else {
            command = EnumMethods.getListType;
            actionSelectionToGet(req, res, connection);
        }
    }

    private void sort(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException {
        String field_name = req.getParameter("field_name");
        String view = req.getParameter("view");
        String sortSQL = createSQLSort(field_name, view);
        getListSearchAndSort(req, res, connection, sortSQL);
    }

    /**
     * Метод определяющий по полю и виду сортировки, какой использовать запрос.
     *
     * @param field_name  - имя поля
     * @param view - вид сортировки
     * @return - получившийся запрос
     */
    private String createSQLSort(String field_name, String view) {
        String desc = "";
        if (view.equals("sort descending")) {
            desc = "desc";
        }
        EnumNameField name_field_enum = EnumNameField.valueOf(field_name);
        switch (name_field_enum) {
            case plane_name:
                return "order by 2 " + desc;
            case type_name:
                return "order by 4 " + desc;
            default:
                return "";
        }
    }


    private void search(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException, BlankFieldException {
        if (req.getParameter("text_search").equals("")) {
            throw new BlankFieldException("No value entered");
        } else {
            String search_line = req.getParameter("text_search");
            String name_field = req.getParameter("name_field");
            String requestSQL = createSQLSearch(name_field, search_line);
            System.out.println(requestSQL);
            getListSearchAndSort(req, res, connection, requestSQL);
        }
    }

    private void getListSearchAndSort(HttpServletRequest req, HttpServletResponse res, Connection connection, String requestSQL) throws SQLException{
        PlaneDAO planeDAO = new PlaneDAO(connection);
        List<Plane> list = planeDAO.findByField(requestSQL);
        if (list.size() == 0) {
            throw new SQLException("The search has not given any results");
        } else {
            req.setAttribute("list", list);
            getList(req, res, connection);
        }
    }

    //переделать запросы
    private String createSQLSearch(String name_field, String search_line) {
        EnumNameField name_field_enum = EnumNameField.valueOf(name_field);
        switch (name_field_enum) {
            case plane_name:
                return "where plane.name_plane like ('%" + search_line + "%')";
            case type_name:
                return "where plane.id_type in (select typea.id from airlines.typea where typea.name_type like ('%" + search_line + "%'))";
            default:
                return "";
        }
    }
}
