package servlets;

import connection.ConnectionPool;
import dao.CityDAO;
import dao.EnumNameField;
import dao.PlaneDAO;
import dao.TypePlaneDAO;
import dto.City;
import dto.Plane;
import dto.TypePlane;
import exceptions.BlankFieldException;
import exceptions.DepartureAndArrivalCityAreTheSameException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CityServlet extends HttpServlet {
    String transitionPage;
    EnumMethods command;


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
            processExceptions(req, res);
        }
    }

    private void processExceptions(HttpServletRequest req, HttpServletResponse res) {
        switch (command) {
            case create:
                command = EnumMethods.getPageCreateOrEdit;
                transitionPage = "cityJSP/addCity.jsp";
                processRequest(req, res);
                break;
            case insert:
                command = EnumMethods.getPageCreateOrEdit;
                transitionPage = req.getParameter("transitionPage");
                processRequest(req, res);
                break;
            case delete:
            case search:
            case sort:
                command = EnumMethods.getList;
                processRequest(req, res);
                break;
            case update:
                command = EnumMethods.getPageCreateOrEdit;
                transitionPage = "cityJSP/updateCity.jsp";
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
            case getPageCreateOrEdit:
                getPageCreateOrEdit(req, res, connection);
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
        if ((req.getAttribute("list") == null) || (req.getAttribute("list").equals(""))) {
            CityDAO city = new CityDAO(connection);
            List<City> list = city.findAll();
            req.setAttribute("list", list);
        }
        transitionPage = "cityJSP/city.jsp";
        goToThePage(req, res);
    }

    private void goToThePage(HttpServletRequest req, HttpServletResponse res) throws IllegalStateException {
        try {
            req.getRequestDispatcher(transitionPage).forward(req, res);
            transitionPage = "";
        } catch (ServletException | IOException e) {
            System.out.println("Connection problem...");
            transitionPage = "";
        }
    }

    private void getPageCreateOrEdit(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException {
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
        goToThePage(req, res);
    }

    /**
     * Передача по id.
     */
    private void getFlightById(HttpServletRequest req, HttpServletResponse res, Connection connection,String param) throws SQLException {
        if ((req.getParameter(param) != null) && (!req.getParameter(param).equals(""))) {
            CityDAO cityDAO = new CityDAO(connection);
            City city = cityDAO.findEntityById(Integer.parseInt(req.getParameter(param)));
            System.out.println(city);
            req.setAttribute("city", city);
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse res, Connection connection) throws NumberFormatException, SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("name_city").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField);
        } else {
            City city = new City();
            CityDAO cityDAO = new CityDAO(connection);
            city.setName_city(req.getParameter("name_city"));
            cityDAO.create(city);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse res, Connection connection) throws NumberFormatException, SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("name_city").equals("") || req.getParameter("id").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField);
        } else {
            City city = new City();
            CityDAO cityDAO = new CityDAO(connection);
            city.setId(Integer.valueOf(req.getParameter("id")));
            city.setName_city(req.getParameter("name_city"));
            cityDAO.update(city);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse res, Connection connection) throws NumberFormatException, SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("id") == null) {
            throw new BlankFieldException("No value selected");
        } else {
            CityDAO cityDAO = new CityDAO(connection);
            int id = Integer.parseInt(req.getParameter("id"));
            cityDAO.delete(id);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    private void insert(HttpServletRequest req, HttpServletResponse res, Connection connection) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))) {
            throw new BlankFieldException("No copy object");
        } else {
            command = EnumMethods.getPageCreateOrEdit;
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
        if (name_field_enum == EnumNameField.city_name) {
            return "order by 2 " + desc;
        }
        return "";
    }


    private void search(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException, BlankFieldException {
        if (req.getParameter("text_search").equals("")) {
            throw new BlankFieldException("No value entered");
        } else {
            String search_line = req.getParameter("text_search");
            String name_field = req.getParameter("name_field");
            String requestSQL = createSQLSearch(name_field, search_line);
            getListSearchAndSort(req, res, connection, requestSQL);
        }
    }

    private void getListSearchAndSort(HttpServletRequest req, HttpServletResponse res, Connection connection, String requestSQL) throws SQLException{
        CityDAO cityDAO = new CityDAO(connection);
        List<City> list = cityDAO.findByField(requestSQL);
        if (list.size() == 0) {
            throw new SQLException("The search has not given any results");
        } else {
            req.setAttribute("list", list);
            getList(req, res, connection);
        }
    }


    private String createSQLSearch(String name_field, String search_line) {
        EnumNameField name_field_enum = EnumNameField.valueOf(name_field);
        if (name_field_enum == EnumNameField.city_name) {
            return "where city.name_city like ('%" + search_line + "%')";
        }
        return "";
    }
}
