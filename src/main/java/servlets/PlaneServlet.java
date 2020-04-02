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

    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        EnumMethods command = EnumMethods.valueOf(req.getParameter("command"));
        String transitionPage = "";
        processRequest(req, res, command, transitionPage);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        EnumMethods command = EnumMethods.valueOf(req.getParameter("command"));
        String transitionPage = "";
        processRequest(req, res, command, transitionPage);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res, EnumMethods command, String transitionPage) {
        try (Connection connection = ConnectionPool.getConnection()) {
            actionSelectionToGet(req, res, connection, command, transitionPage);
        } catch (SQLException | DepartureAndArrivalCityAreTheSameException | BlankFieldException e) {
            req.setAttribute("errorMessage", e.getMessage());
            processExceptions(req,res, command, transitionPage);
        }
    }

    private void processExceptions(HttpServletRequest req, HttpServletResponse res, EnumMethods command, String transitionPage)  {
        switch (command) {
            case create:
                transitionPage = "planeJSP/addPlane.jsp";
                command = EnumMethods.getListType;
                processRequest(req, res, command, transitionPage);
                break;
            case insert:
                transitionPage = req.getParameter("transitionPage");
                command = EnumMethods.getListType;
                processRequest(req, res, command, transitionPage);
                break;
            case delete:
            case search:
            case sort:
                command = EnumMethods.getList;
                processRequest(req, res, command, transitionPage);
                break;
            case update:
                transitionPage = "planeJSP/updatePlane.jsp";
                command = EnumMethods.getListType;
                processRequest(req, res, command, transitionPage);
                break;
            default:
                break;
        }
    }



    private void actionSelectionToGet(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        switch (command) {
            case getList:
            case drop:
                getList(req, res, connection, transitionPage);
                break;
            case getListType:
                getListType(req, res, connection, transitionPage);
                break;
            case create:
                create(req, res, connection, command, transitionPage);
                break;
            case delete:
                remove(req, res, connection, command, transitionPage);
                break;
            case update:
                update(req, res, connection, command, transitionPage);
                break;
            case search:
                search(req, res, connection, transitionPage);
                break;
            case sort:
                sort(req, res, connection, transitionPage);
                break;
            case insert:
                insert(req, res, connection, command, transitionPage);
            default:
                break;
        }
    }

    private void getList(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException {
        if((req.getAttribute("list") == null) || (req.getAttribute("list").equals(""))) {
            PlaneDAO type = new PlaneDAO(connection);
            List<Plane> list = type.findAll();
            req.setAttribute("list", list);
        }
        transitionPage = "planeJSP/plane.jsp";
        goToThePage(req, res, transitionPage);
    }

    private void goToThePage(HttpServletRequest req, HttpServletResponse res, String transitionPage) throws IllegalStateException{
        try {
            System.out.println(transitionPage);
            req.getRequestDispatcher(transitionPage).forward(req, res);
            transitionPage = "";
        } catch (ServletException | IOException e) {
            System.out.println("Connection problem...");
            transitionPage = "";
        }
    }

    private void getListType(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException {
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
        goToThePage(req, res, transitionPage);
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


    private void create(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException {
        if (req.getParameter("name_plane").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField );
        } else {
            Plane plane = new Plane();
            PlaneDAO planeDAO = new PlaneDAO(connection);
            TypePlane typePlane = new TypePlane();
            typePlane.setId(Integer.parseInt(req.getParameter("type_id")));
            plane.setTypePlane(typePlane);
            plane.setNamePlane(req.getParameter("name_plane"));
            planeDAO.create(plane);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse res, Connection connection,  EnumMethods command, String transitionPage) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException {
        if (req.getParameter("name_plane").equals("") || req.getParameter("id").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField);
        } else {
            Plane plane = new Plane();
            System.out.println("update");
            PlaneDAO planeDAO = new PlaneDAO(connection);
            plane.setId(Integer.parseInt(req.getParameter("id")));
            TypePlane typePlane = new TypePlane();
            typePlane.setId(Integer.parseInt(req.getParameter("type_id")));
            plane.setTypePlane(typePlane);
            plane.setNamePlane(req.getParameter("name_plane"));
            planeDAO.update(plane);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("id") == null) {
            throw new BlankFieldException("No value selected");
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            PlaneDAO planeDAO = new PlaneDAO(connection);
            planeDAO.delete(id);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void insert(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))) {
            throw new BlankFieldException("No copy object");
        } else {
            command = EnumMethods.getListType;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void sort(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException {
        String fieldName = req.getParameter("field_name");
        String view = req.getParameter("view");
        String sortSQL = createSQLSort(fieldName, view);
        getListSearchAndSort(req, res, connection, sortSQL, transitionPage);
    }

    /**
     * Метод определяющий по полю и виду сортировки, какой использовать запрос.
     *
     * @param fieldName  - имя поля
     * @param view - вид сортировки
     * @return - получившийся запрос
     */
    private String createSQLSort(String fieldName, String view) {
        String desc = "";
        if (view.equals("sort descending")) {
            desc = "desc";
        }
        EnumNameField nameField = EnumNameField.valueOf(fieldName);
        switch (nameField) {
            case PLANE_NAME:
                return "order by 2 " + desc;
            case TYPE_NAME:
                return "order by 4 " + desc;
            default:
                return "";
        }
    }


    private void search(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException, BlankFieldException {
        if (req.getParameter("text_search").equals("")) {
            throw new BlankFieldException("No value entered");
        } else {
            String search = req.getParameter("text_search");
            String field = req.getParameter("name_field");
            String requestSQL = createSQLSearch(field, search);
            System.out.println(requestSQL);
            getListSearchAndSort(req, res, connection, requestSQL, transitionPage);
        }
    }

    private void getListSearchAndSort(HttpServletRequest req, HttpServletResponse res, Connection connection, String requestSQL, String transitionPage) throws SQLException{
        PlaneDAO planeDAO = new PlaneDAO(connection);
        List<Plane> list = planeDAO.findByField(requestSQL);
        if (list.size() == 0) {
            throw new SQLException("The search has not given any results");
        } else {
            req.setAttribute("list", list);
            getList(req, res, connection, transitionPage);
        }
    }

    //переделать запросы
    private String createSQLSearch(String nameField, String searchLine) {
        EnumNameField nameFieldEnum = EnumNameField.valueOf(nameField);
        switch (nameFieldEnum) {
            case PLANE_NAME:
                return "where plane.name_plane like ('%" + searchLine + "%')";
            case TYPE_NAME:
                return "where plane.id_type in (select typea.id from airlines.typea where typea.name_type like ('%" + searchLine + "%'))";
            default:
                return "";
        }
    }
}
