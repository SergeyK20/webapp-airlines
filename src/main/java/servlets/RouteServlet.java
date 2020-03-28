package servlets;

import connection.ConnectionPool;
import dao.CityDAO;
import dao.EnumNameField;
import dao.FlightsDAO;
import dao.RouteDAO;
import dto.City;
import dto.Flights;
import dto.Route;
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

public class RouteServlet extends HttpServlet {

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
        } catch (SQLException | DepartureAndArrivalCityAreTheSameException | BlankFieldException | NumberFormatException e) {
            req.setAttribute("errorMessage", e.getMessage());
            processExceptions(req, res, command, transitionPage);
        }
    }

    private void processExceptions(HttpServletRequest req, HttpServletResponse res, EnumMethods command, String transitionPage) {
        switch (command) {
            case create:
                transitionPage = "routeJSP/addRoute.jsp";
                command = EnumMethods.getListCity;
                processRequest(req, res, command, transitionPage);
                break;
            case insert:
                transitionPage = req.getParameter("transitionPage");
                command = EnumMethods.getListCity;
                processRequest(req, res, command, transitionPage);
                break;
            case delete:
            case search:
            case sort:
                command = EnumMethods.getList;
                processRequest(req, res, command, transitionPage);
                break;
            case update:
                transitionPage = "routeJSP/updateRoute.jsp";
                command = EnumMethods.getListCity;
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
            case getListCity:
                getListCity(req, res, connection,transitionPage);
                break;
            case create:
                create(req, res, connection, command, transitionPage);
                break;
            case delete:
                remove(req, res, connection,command, transitionPage);
                break;
            case update:
                update(req, res, connection, command, transitionPage);
                break;
            case search:
                search(req, res, connection, transitionPage);
                break;
            case sort:
                sort(req, res, connection,transitionPage);
                break;
            case insert:
                insert(req, res, connection,command, transitionPage);
            default:
                break;
        }
    }

    private void getList(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException, IllegalStateException {
        if ((req.getAttribute("list") == null) || (req.getAttribute("list").equals(""))) {
            RouteDAO routeDAO = new RouteDAO(connection);
            List<Route> list = routeDAO.findAll();
            req.setAttribute("list", list);
        }
        transitionPage = "routeJSP/route.jsp";
        goToThePage(req, res, transitionPage);
    }

    private void goToThePage(HttpServletRequest req, HttpServletResponse res, String transitionPage) throws IllegalStateException {
        try {
            req.getRequestDispatcher(transitionPage).forward(req, res);
        } catch (ServletException | IOException e) {
            System.out.println("Connection problem...");
        }
    }

    private void getListCity(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException {
        String param = "";
        if (req.getParameter("transitionPage") != null) {
            transitionPage = req.getParameter("transitionPage");
        }
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))) {
            param = "id";
        } else {
            param = "id_copy";
        }
        getFlightById(req, res, connection, param);
        CityDAO cityDAO = new CityDAO(connection);
        List<City> list = cityDAO.findAll();
        req.setAttribute("list", list);
        goToThePage(req, res, transitionPage);
    }

    /**
     * Передача по id.
     */
    private void getFlightById(HttpServletRequest req, HttpServletResponse res, Connection connection, String param) throws SQLException {
        if ((req.getParameter(param) != null) && (!req.getParameter(param).equals(""))) {
            RouteDAO routeDAO = new RouteDAO(connection);
            Route route = routeDAO.findEntityById(Integer.parseInt(req.getParameter(param)));
            System.out.println(route);
            req.setAttribute("route", route);
        }
    }


    private void create(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException {
        if (req.getParameter("travel_minutes").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField + "flight time");
        } else {
            Route route = new Route();
            RouteDAO routeDAO = new RouteDAO(connection);
            City cityFrom = new City();
            City cityTo = new City();
            cityFrom.setId(Integer.parseInt(req.getParameter("city_from")));
            route.setFrom(cityFrom);
            cityTo.setId(Integer.parseInt(req.getParameter("city_to")));
            route.setTo(cityTo);
            route.setTravelTimeMinutes(Integer.parseInt(req.getParameter("travel_minutes")));
            routeDAO.create(route);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException {
        if (req.getParameter("travel_minutes").equals("") || req.getParameter("id").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField);
        } else {
            Route route = new Route();
            RouteDAO routeDAO = new RouteDAO(connection);
            City cityFrom = new City();
            City cityTo = new City();
            route.setId(Integer.parseInt(req.getParameter("id")));
            cityFrom.setId(Integer.parseInt(req.getParameter("city_from")));
            route.setFrom(cityFrom);
            cityTo.setId(Integer.parseInt(req.getParameter("city_to")));
            route.setTo(cityTo);
            route.setTravelTimeMinutes(Integer.parseInt(req.getParameter("travel_minutes")));
            routeDAO.update(route);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("id") == null) {
            throw new BlankFieldException("No value selected");
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            RouteDAO routeDAO = new RouteDAO(connection);
            routeDAO.delete(id);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection, command,transitionPage);
        }
    }

    private void insert(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))) {
            throw new BlankFieldException("No copy object");
        } else {
            command = EnumMethods.getListCity;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void sort(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException {
        String field_name = req.getParameter("field_name");
        String view = req.getParameter("view");
        String sortSQL = createSQLSort(field_name, view);
        getListSearchAndSort(req, res, connection, sortSQL, transitionPage);
    }

    /**
     * Метод определяющий по полю и виду сортировки, какой использовать запрос.
     *
     * @param field_name - имя поля
     * @param view       - вид сортировки
     * @return - получившийся запрос
     */
    private String createSQLSort(String field_name, String view) {
        String desc = "";
        if (view.equals("sort descending")) {
            desc = "desc";
        }
        EnumNameField name_field_enum = EnumNameField.valueOf(field_name);
        switch (name_field_enum) {
            case from_name:
                return "order by city_from " + desc;
            case to_name:
                return "order by 5 " + desc;
            case time_travel:
                return "order by 6 " + desc;
            default:
                return "";
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws BlankFieldException, SQLException {
        if (req.getParameter("text_search").equals("")) {
            throw new BlankFieldException("No value entered");
        } else {
            String search_line = req.getParameter("text_search");
            String name_field = req.getParameter("name_field");
            String requestSQL = createSQLSearch(name_field, search_line);
            getListSearchAndSort(req, res, connection, requestSQL, transitionPage);
        }
    }

    private void getListSearchAndSort(HttpServletRequest req, HttpServletResponse res, Connection connection, String requestSQL, String transitionPage) throws SQLException {
        RouteDAO routeDAO = new RouteDAO(connection);
        List<Route> list = routeDAO.findByField(requestSQL);
        if (list.size() == 0) {
            throw new SQLException("The search has not given any results");
        } else {
            req.setAttribute("list", list);
            getList(req, res, connection, transitionPage);
        }
    }

    //переделать запросы
    private String createSQLSearch(String name_field, String search_line) {
        EnumNameField name_field_enum = EnumNameField.valueOf(name_field);
        switch (name_field_enum) {
            case from_name:
                return "where route.id_from in (select city.id from city where city.name_city like ('%" + search_line + "%'))";
            case to_name:
                return "where route.id_to in (select city.id from city where city.name_city like ('%" + search_line + "%'))";
            case time_travel:
                return "where route.time_travel like  ('%" + search_line + "%')";
            default:
                return "";
        }
    }
}
