package dao;

import dto.*;
import exceptions.DepartureAndArrivalCityAreTheSameException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO extends AbstractDAO<Integer,Route> {

    public RouteDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Route> findByField(String requestSQL) throws SQLException {
        PreparedStatement pst = null;
        List<Route> list = new ArrayList<Route>();
        String sqlFind = ("select airlines.route.id,  airlines.route.id_from, \n" +
                "\t\t\t\t\t\t\t\t(select airlines.city.name_city from airlines.city where airlines.route.id_from = city.id) as city_from,\n" +
"                                airlines.route.id_to,\n" +
"                                (select city.name_city from airlines.city as city where airlines.route.id_to = city.id) as city_to,\n" +
"                                 airlines.route.time_travel\n" +
                "from airlines.route " +
                requestSQL+";");
        pst = connection.prepareStatement(sqlFind);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            Route route = new Route();
            route.setId(resultSet.getInt(1));
            City city_from = new City(resultSet.getInt(2),resultSet.getString(3));
            City city_to = new City(resultSet.getInt(4),resultSet.getString(5));
            route.setFrom(city_from);
            route.setTo(city_to);
            route.setTravelTimeMinutes(resultSet.getInt(6));
            list.add(route);
        }
        pst.close();
        return list;
    }

    @Override
    public List<Route> findAll() throws SQLException {
        Statement st = null;
        List<Route> list = new ArrayList<Route>();
            st = connection.createStatement();
            String sqlFindAll = "SELECT route.Id, route.id_from, route.id_to, city1.name_city as City_from, city2.name_city as City_to, route.time_travel From airlines.route inner join airlines.city as city1 on route.id_from = city1.Id inner join airlines.city as city2 on route.id_to = city2.Id Order By route.Id;";
            ResultSet resultSet = st.executeQuery(sqlFindAll);
            while(resultSet.next()){
                Route route = new Route();
                route.setId(resultSet.getInt(1));
                City city_from = new City(resultSet.getInt(2),resultSet.getString(4));
                City city_to = new City(resultSet.getInt(3),resultSet.getString(5));
                route.setFrom(city_from);
                route.setTo(city_to);
                route.setTravelTimeMinutes(resultSet.getInt(6));
                list.add(route);
            }
            st.close();
        return list;
    }

    @Override
    public Route findEntityById(Integer id) throws SQLException {
        PreparedStatement  pst = null;
        Route route = new Route();
        String sqlFind = "SELECT route.Id, route.id_from, route.id_to, city1.name_city as City_from, city2.name_city as City_to, route.time_travel From airlines.route inner join airlines.city as city1 on route.id_from = city1.Id inner join airlines.city as city2 on route.id_to = city2.Id WHERE ROUTE.Id = ?;";
        pst = connection.prepareStatement(sqlFind);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            route.setId(resultSet.getInt(1));
            City city_from = new City(resultSet.getInt(2),resultSet.getString(4));
            City city_to = new City(resultSet.getInt(3),resultSet.getString(5));
            route.setFrom(city_from);
            route.setTo(city_to);
            route.setTravelTimeMinutes(resultSet.getInt(6));
            System.out.println(route);
        }
        return route;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateCity = "DELETE FROM airlines.route WHERE Id = (?)";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setInt(1,id);
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean create(Route entity) throws SQLException, DepartureAndArrivalCityAreTheSameException{
        if(entity.getFrom().getId() == entity.getTo().getId()){

            throw new DepartureAndArrivalCityAreTheSameException();
        } else {
            PreparedStatement pst = null;
            String sqlCreateRoute = "INSERT INTO airlines.route (id_from, id_to,time_travel) Values (?,?,?)";
            pst = connection.prepareStatement(sqlCreateRoute);
            pst.setInt(1, entity.getFrom().getId());
            pst.setInt(2, entity.getTo().getId());
            pst.setInt(3, entity.getTravelTimeMinutes());
            pst.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean update(Route entity) throws DepartureAndArrivalCityAreTheSameException, SQLException {
        if(entity.getFrom().getId() == entity.getTo().getId()){
            throw new DepartureAndArrivalCityAreTheSameException();
        } else {
            PreparedStatement pst = null;
            String sqlUpdateRoute = "update airlines.route set id_from = ?, id_to = ?, time_travel = ? where Id= ?;";
            pst = connection.prepareStatement(sqlUpdateRoute);
            pst.setInt(1, entity.getFrom().getId());
            pst.setInt(2, entity.getTo().getId());
            pst.setInt(3, entity.getTravelTimeMinutes());
            pst.setInt(4,entity.getId());
            pst.executeUpdate();
        }
        return true;
    }


}
