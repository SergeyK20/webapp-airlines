package servlets;

import connection.ConnectionPool;
import dao.CityDAO;
import dao.EnumNameField;
import dto.City;
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

public class CityServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        EnumMethods command = EnumMethods.valueOf(req.getParameter("command"));
        String transitionPage = "";
        processRequest(req, res, command, transitionPage);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        EnumMethods command = EnumMethods.valueOf(req.getParameter("command"));
        String transitionPage =  "";
        processRequest(req, res, command, transitionPage);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse res, EnumMethods command, String transitionPage) {
        try (Connection connection = ConnectionPool.getConnection()) {
            actionSelectionToGet(req, res, connection,command, transitionPage);
        } catch (SQLException | DepartureAndArrivalCityAreTheSameException | BlankFieldException e) {
            req.setAttribute("errorMessage", e.getMessage());
            processExceptions(req, res,command, transitionPage);
        }
    }

    private void processExceptions(HttpServletRequest req, HttpServletResponse res,EnumMethods command, String transitionPage) {
        switch (command) {
            case CREATE:
                command = EnumMethods.GET_PAGE_CREATE_OR_EDIT;
                transitionPage = "cityJSP/addCity.jsp";
                processRequest(req, res,command, transitionPage);
                break;
            case INSERT:
                command = EnumMethods.GET_PAGE_CREATE_OR_EDIT;
                transitionPage = req.getParameter("transitionPage");
                processRequest(req, res,command, transitionPage);
                break;
            case DELETE:
            case SEARCH:
            case SORT:
                command = EnumMethods.GET_LIST;
                processRequest(req, res,command, transitionPage);
                break;
            case UPDATE:
                command = EnumMethods.GET_PAGE_CREATE_OR_EDIT;
                transitionPage = "cityJSP/updateCity.jsp";
                processRequest(req, res,command, transitionPage);
                break;
            default:
                break;
        }
    }

    private void actionSelectionToGet(HttpServletRequest req, HttpServletResponse res, Connection connection, EnumMethods command, String transitionPage) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        switch (command) {
            case GET_LIST:
            case DROP:
                getList(req, res, connection, transitionPage);
                break;
            case GET_PAGE_CREATE_OR_EDIT:
                getPageCreateOrEdit(req, res, connection, transitionPage);
                break;
            case CREATE:
                create(req, res, connection, command, transitionPage);
                break;
            case DELETE:
                remove(req, res, connection, command, transitionPage);
                break;
            case UPDATE:
                update(req, res, connection, command, transitionPage);
                break;
            case SEARCH:
                search(req, res, connection, command, transitionPage);
                break;
            case SORT:
                sort(req, res, connection, command, transitionPage);
                break;
            case INSERT:
                insert(req, res, connection, command, transitionPage);
            default:
                break;
        }
    }

    private void getList(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException {
        if ((req.getAttribute("list") == null) || (req.getAttribute("list").equals(""))) {
            CityDAO city = new CityDAO(connection);
            List<City> list = city.findAll();
            req.setAttribute("list", list);
        }
        transitionPage = "cityJSP/city.jsp";
        goToThePage(req, res, transitionPage);
    }

    private void goToThePage(HttpServletRequest req, HttpServletResponse res, String transitionPage) throws IllegalStateException {
        try {
            req.getRequestDispatcher(transitionPage).forward(req, res);
            transitionPage = "";
        } catch (ServletException | IOException e) {
            System.out.println("Connection problem...");
            transitionPage = "";
        }
    }

    private void getPageCreateOrEdit(HttpServletRequest req, HttpServletResponse res, Connection connection, String transitionPage) throws SQLException {
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
        goToThePage(req, res, transitionPage);
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

    private void create(HttpServletRequest req, HttpServletResponse res, Connection connection,EnumMethods command, String transitionPage) throws NumberFormatException, SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("name_city").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField);
        } else {
            City city = new City();
            CityDAO cityDAO = new CityDAO(connection);
            city.setNameCity(req.getParameter("name_city"));
            cityDAO.create(city);
            command = EnumMethods.GET_LIST;
            actionSelectionToGet(req, res, connection,command, transitionPage);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse res, Connection connection,EnumMethods command, String transitionPage) throws NumberFormatException, SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("name_city").equals("") || req.getParameter("id").equals("")) {
            String messageExceptionBlankField = "Not filled in key field ";
            throw new BlankFieldException(messageExceptionBlankField);
        } else {
            City city = new City();
            CityDAO cityDAO = new CityDAO(connection);
            city.setId(Integer.valueOf(req.getParameter("id")));
            city.setNameCity(req.getParameter("name_city"));
            cityDAO.update(city);
            command = EnumMethods.GET_LIST;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse res, Connection connection,EnumMethods command, String transitionPage) throws NumberFormatException, SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("id") == null) {
            throw new BlankFieldException("No value selected");
        } else {
            CityDAO cityDAO = new CityDAO(connection);
            int id = Integer.parseInt(req.getParameter("id"));
            cityDAO.delete(id);
            command = EnumMethods.GET_LIST;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void insert(HttpServletRequest req, HttpServletResponse res, Connection connection,EnumMethods command, String transitionPage) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException {
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))) {
            throw new BlankFieldException("No copy object");
        } else {
            command = EnumMethods.GET_PAGE_CREATE_OR_EDIT;
            actionSelectionToGet(req, res, connection, command, transitionPage);
        }
    }

    private void sort(HttpServletRequest req, HttpServletResponse res, Connection connection,EnumMethods command, String transitionPage) throws SQLException {
        String fieldName = req.getParameter("field_name");
        String view = req.getParameter("view");
        String sortSQL = createSQLSort(fieldName, view);
        getListSearchAndSort(req, res, connection, sortSQL, command, transitionPage);
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
        if (nameField == EnumNameField.city_name) {
            return "order by 2 " + desc;
        }
        return "";
    }


    private void search(HttpServletRequest req, HttpServletResponse res, Connection connection,EnumMethods command, String transitionPage) throws SQLException, BlankFieldException {
        if (req.getParameter("text_search").equals("")) {
            throw new BlankFieldException("No value entered");
        } else {
            String search = req.getParameter("text_search");
            String field = req.getParameter("name_field");
            String requestSQL = createSQLSearch(field, search);
            getListSearchAndSort(req, res, connection, requestSQL,command, transitionPage);
        }
    }

    private void getListSearchAndSort(HttpServletRequest req, HttpServletResponse res, Connection connection, String requestSQL,EnumMethods command, String transitionPage) throws SQLException{
        CityDAO cityDAO = new CityDAO(connection);
        List<City> list = cityDAO.findByField(requestSQL);
        if (list.size() == 0) {
            throw new SQLException("The search has not given any results");
        } else {
            req.setAttribute("list", list);
            getList(req, res, connection,transitionPage);
        }
    }


    private String createSQLSearch(String nameField, String searchLine) {
        EnumNameField field = EnumNameField.valueOf(nameField);
        if (field == EnumNameField.city_name) {
            return "where city.name_city like ('%" + searchLine + "%')";
        }
        return "";
    }
}
