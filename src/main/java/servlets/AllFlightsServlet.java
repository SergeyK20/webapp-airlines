package servlets;

import connection.ConnectionPool;
import dao.EnumNameField;
import dao.FlightsDAO;
import dao.PlaneDAO;
import dao.RouteDAO;
import dto.Flights;
import dto.Plane;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AllFlightsServlet extends HttpServlet {
    /**
     * Переменная указывает на страницу перехода.
     */
    String transitionPage;

    /**
     * Переменная указыващая на команду для действия сервлета.
     */
    EnumMethods command;

    /**
     * При обращении к серверу со стороны страницы airlines.jsp, addAirlines.jsp, updateAirlines.jsp
     * переходит при запросе GET в данный метод
     *
     * @param req запрос со стороны клиента
     * @param res ответ клиенту
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        // присваиваем переменной command апраметр command
        command = EnumMethods.valueOf(req.getParameter("command"));
        processRequest(req, res);
    }

    /**
     * При обращении к серверу со стороны страницы airlines.jsp, addAirlines.jsp, updateAirlines.jsp
     * переходит при запросе POST в данный метод
     *
     * @param req запрос со стороны клиента
     * @param res ответ клиенту
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse res) {
        // присваиваем переменной command апраметр command
        command = EnumMethods.valueOf(req.getParameter("command"));
        processRequest(req, res);
    }

    /**
     * Метод который позволяет перейти в один из методов изменения таблицы.
     * При обнаружении ошибки заходит в поле catch и задает атрибут "errorMessage", который является сообщение, выводим на странице
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse res) {
        try (Connection connection = ConnectionPool.getConnection()) {
            actionSelectionToGet(req, res, connection);
        } catch (IllegalStateException e){
            System.out.println("incomprehensible mistake");
        }
        catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            // задаем атрибут ошибки(сообщене которое будет выводится
            req.setAttribute("errorMessage", e.getMessage());
            processExceptions(req, res);
        } catch (DepartureAndArrivalCityAreTheSameException | BlankFieldException e) {
            req.setAttribute("errorMessage", e.getMessage());
            processExceptions(req, res);
        }
    }

    /**
     * После обнаружения ошибки указывает путь к странице, к которой нужно вернуться с сообщение об ошибке.
     * Переменная command указывает, на то какие араметры должны передаться на страницу вместе с сообщением об ошибке
     */
    private void processExceptions(HttpServletRequest req, HttpServletResponse res) {
        switch (command) {
            case create:
                transitionPage = "airlinesJSP/addAirlines.jsp";
                command = EnumMethods.getListRouteAndPlane;
                processRequest(req, res);
                break;
            case insert:
                transitionPage = req.getParameter("transitionPage");
                command = EnumMethods.getListRouteAndPlane;
                processRequest(req, res);
                break;
            case delete:
            case search:
            case sort:
                command = EnumMethods.getList;
                processRequest(req, res);
                break;
            case update:
                transitionPage = "airlinesJSP/updateAirlines.jsp";
                command = EnumMethods.getListRouteAndPlane;
                processRequest(req, res);
                break;
            default:
                break;
        }
    }

    /**
     * По переменной command  перенаправляет на определенный метод, для дальнейших действий.
     *
     * @param connection переменная позвляющая соединиться с БД.
     * @throws BlankFieldException                        ошибка пустого или незаполенного поля.
     * @throws SQLException                               ошибка работы с БД.
     * @throws DepartureAndArrivalCityAreTheSameException ошибка равенства городов отправдения и прибытия.
     */
    private void actionSelectionToGet(HttpServletRequest req, HttpServletResponse res, Connection connection) throws BlankFieldException, SQLException, DepartureAndArrivalCityAreTheSameException,IllegalStateException {
        switch (command) {
            case getList:
            case drop:
                getList(req, res, connection);
                break;
            case getListRouteAndPlane:
                getListRouteAndPlane(req, res, connection);
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
                break;
            default:
                break;
        }
    }


    /**
     * Делает запрос на возващение всех авиарейсов, помещает его в атрибут и перенаправляет на страницу airlines.jsp.
     */
    private void getList(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException,IllegalStateException {
        FlightsDAO air = new FlightsDAO(connection);
        List<Flights> list = air.findAll();
        req.setAttribute("list", list);
        transitionPage = "airlinesJSP/airlines.jsp";
        goToThePage(req, res);
    }

    private void goToThePage(HttpServletRequest req, HttpServletResponse res) throws IllegalStateException{
        try {
            req.getRequestDispatcher(transitionPage).forward(req, res);
            transitionPage = "";
        } catch (ServletException | IOException e) {
            System.out.println("Connection problem...");
            transitionPage = "";
        }
    }


    /**
     * Делает запрос на возващение всех рейсов и самолетов, помещает их в атрибуты и перенаправляет на страницу addAirlines.jsp или updateAirlines.jsp.
     */
    private void getListRouteAndPlane(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException {
        String param = "";
        //если значение параметра не null, то присваиваем его переменной transitionPage
        if (req.getParameter("transitionPage") != null) {
            transitionPage = req.getParameter("transitionPage");
        }
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))){
            param = "id";
        } else param = "id_copy";
        //проверка на то какой параметр должны исполять. Если Id не пустой, то его, иначе Id_copy.
        getFlightById(req, res, connection, param);
        RouteDAO routeDAO = new RouteDAO(connection);
        List<Route> list = routeDAO.findAll();
        req.setAttribute("list", list);
        PlaneDAO planeDAO = new PlaneDAO(connection);
        List<Plane> list1 = planeDAO.findAll();
        req.setAttribute("list1", list1);
        goToThePage(req, res);
    }

    /**
     * Передача по id.
     */
    private void getFlightById(HttpServletRequest req, HttpServletResponse res, Connection connection,String param) throws SQLException {
        if ((req.getParameter(param) != null) && (!req.getParameter(param).equals(""))) {
            FlightsDAO flightsDAO = new FlightsDAO(connection);
            Flights flights = flightsDAO.findEntityById(Integer.parseInt(req.getParameter(param)));
            req.setAttribute("flight", flights);
        }
    }

    /**
     * Метод создания нового рейса.
     */
    private void create(HttpServletRequest req, HttpServletResponse res, Connection connection) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException, NumberFormatException {
        if ((req.getParameter("id_flight").equals("")) || (req.getParameter("date").equals("")) || (req.getParameter("time").equals(""))) {
            StringBuilder messageException = new StringBuilder("Not filled in key field ");
            if (req.getParameter("id_flight").equals("")) {
                messageException.append(" id flight").append("\n");
            }
            if (req.getParameter("date").equals("")) {
                messageException.append(" date").append("\n");
            }
            if (req.getParameter("time").equals("")) {
                messageException.append(" time").append("\n");
            }
            throw new BlankFieldException(messageException.toString());
        } else {
            Plane plane = new Plane();
            Route route = new Route();
            Flights flight = new Flights();
            FlightsDAO flightsDAO = new FlightsDAO(connection);
            flight.setId_airlines(Integer.parseInt(req.getParameter("id_flight")));
            route.setId(Integer.parseInt(req.getParameter("route")));
            flight.setDate(LocalDate.parse(req.getParameter("date")));
            flight.setTime(LocalTime.parse(req.getParameter("time")));
            plane.setId(Integer.parseInt(req.getParameter("plane")));
            flight.setRoute(route);
            flight.setPlane(plane);
            flightsDAO.create(flight);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    /**
     * Метод изменения рейса.
     */
    private void update(HttpServletRequest req, HttpServletResponse res, Connection connection) throws DepartureAndArrivalCityAreTheSameException, SQLException, BlankFieldException {
        if ((req.getParameter("id").equals("")) || (req.getParameter("id_flight").equals("")) || (req.getParameter("date").equals("")) || (req.getParameter("time").equals(""))) {
            StringBuilder messageException = new StringBuilder("Not filled in key field ");
            if (req.getParameter("id_flight").equals("")) {
                messageException.append(" id flight").append("\n");
            }
            if (req.getParameter("date").equals("")) {
                messageException.append(" date").append("\n");
            }
            if (req.getParameter("time").equals("")) {
                messageException.append(" time").append("\n");
            }
            throw new BlankFieldException(messageException.toString());
        } else {
            Plane plane = new Plane();
            Route route = new Route();
            Flights flight = new Flights();
            flight.setId(Integer.parseInt(req.getParameter("id")));
            FlightsDAO flightsDAO = new FlightsDAO(connection);
            flight.setId_airlines(Integer.parseInt(req.getParameter("id_flight")));
            route.setId(Integer.parseInt(req.getParameter("route")));
            flight.setDate(LocalDate.parse(req.getParameter("date")));
            flight.setTime(LocalTime.parse(req.getParameter("time")));
            plane.setId(Integer.parseInt(req.getParameter("plane")));
            flight.setRoute(route);
            flight.setPlane(plane);
            flightsDAO.update(flight);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    /**
     * Метод удаления рейса.
     */
    private void remove(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("id") == null) {
            throw new BlankFieldException("No value selected");
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            FlightsDAO flightsDAO = new FlightsDAO(connection);
            flightsDAO.delete(id);
            command = EnumMethods.getList;
            actionSelectionToGet(req, res, connection);
        }
    }

    /**
     * Метод поиска группы рейса по определенному фильтру.
     *
     * @param req Принимаем от клиента два парметра: text_search - фильтр поиска; name_field - поле фильтра поиска.
     */
    private void search(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if (req.getParameter("text_search").equals("")) {
            throw new BlankFieldException("No value entered");
        } else {
            String search_line = req.getParameter("text_search");
            String name_field = req.getParameter("name_field");
            String requestSQL = createSQLSearch(name_field, search_line);
            getListSearchAndSort(req, res, connection, requestSQL);
        }
    }

    /**
     * Метод делающий запрос DAO по поиску необходимых рейсов, помещая их в атрибут, переводит на страницу airlines.jsp.
     */
    private void getListSearchAndSort(HttpServletRequest req, HttpServletResponse res, Connection connection, String requestSQL) throws SQLException, BlankFieldException {
        FlightsDAO flightsDAO = new FlightsDAO(connection);
        List<Flights> list = flightsDAO.findByField(requestSQL);
        if (list.size() == 0) {
            throw new BlankFieldException("The search has not given any results");
        } else {
            req.setAttribute("list", list);
            transitionPage = "airlinesJSP/airlines.jsp";
            goToThePage(req,res);
        }
    }

    /**
     * Метод определяющий по полю и строке поиска какой использовать запрос.
     *
     * @param name_field  - имя поля
     * @param search_line - строка поиска
     * @return - получившийся запрос
     */
    private String createSQLSearch(String name_field, String search_line) {
        EnumNameField name_field_enum = EnumNameField.valueOf(name_field);
        switch (name_field_enum) {
            case id_flights:
                return "where flights.id_flights like ('%" + search_line + "%')";
            case from_name:
                return "where flights.id_route in ( select route.id FROM  route where route.id_to in (select Id from city where name_city like ('%" + search_line + "%')))";
            case to_name:
                return "where flights.id_route in ( select route.id FROM  route where route.id_from in (select Id from city where name_city like ('%" + search_line + "%')))";
            case plane_name:
                return "where flights.id_plane in(select plane.id FROM plane where plane.name_plane like ('%" + search_line + "%'))";
            case type_name:
                return "where flights.id_plane in(select plane.id FROM plane where plane.id_type in(select Id from typea where typea.name_type like('%" + search_line + "%')))";
            case date_value:
                return "where flights.date like ('%" + search_line + "%')";
            case time_value:
                return "where flights.time like ('%" + search_line + "%')";
            case time_travel:
                return "where flights.id_route in ( select route.id FROM  route where route.time_travel like ('%" + search_line + "%')";
            default:
                return "";
        }
    }

    /**
     * Метод вставки.
     */
    private void sort(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
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
            case id_flights:
                return "order by 2 " + desc;
            case from_name:
                return "order by 7 " + desc;
            case to_name:
                return "order by 6 " + desc;
            case plane_name:
                return "order by 12 " + desc;
            case type_name:
                return "order by 14 " + desc;
            case date_value:
                return "order by 9 " + desc;
            case time_value:
                return "order by 10 " + desc;
            case time_travel:
                return "order by 8 " + desc;
            default:
                return "";
        }
    }

    /**
     * Метод вставки.
     */
    private void insert(HttpServletRequest req, HttpServletResponse res, Connection connection) throws SQLException, BlankFieldException, DepartureAndArrivalCityAreTheSameException {
        if ((req.getParameter("id_copy") == null) || (req.getParameter("id_copy").equals(""))) {
            throw new BlankFieldException("No copy object");
        } else {
            command = EnumMethods.getListRouteAndPlane;
            actionSelectionToGet(req, res, connection);
        }
    }
}
