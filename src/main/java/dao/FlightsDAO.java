package dao;

import dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightsDAO extends AbstractDAO<Integer, Flights> {

    public FlightsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Flights> findByField(String requestSQL) throws SQLException {
        PreparedStatement pst = null;
        List<Flights> list = new ArrayList<Flights>();
        String sqlFind = ("SELECT flights.id, flights.id_flights, flights.id_route, \n" +
                "\t\t\t\t (select route.id_from from route where flights.id_route = route.Id) as id_from,\n" +
                "         (select route.id_to from route where flights.id_route = route.Id) as id_to,\n" +
                "\t\t\t\t (select city.name_city from city where city.id = (select route.id_from from route where flights.id_route = route.id)) as city_from,\n" +
                "\t\t\t\t (select city.name_city from city where city.id = (select route.id_to from route where flights.id_route = route.id)) as city_to,\n" +
                "         (select route.time_travel from route where flights.id_route = route.Id) as time_travel,\n" +
                "\t\t\t\t flights.date, flights.time,\n" +
                "                 (select plane.Id from plane where flights.id_plane = plane.Id) as id_plane,\n" +
                "\t\t\t\t (select plane.name_plane from plane where flights.id_plane = plane.Id) as name_plane,\n" +
                "                 (select typea.id from typea where typea.id = (select plane.id_type from plane where plane.id = flights.id_plane)) as type_id,\n" +
                "\t\t\t\t (select typea.name_type from typea where typea.id = (select plane.id_type from plane where plane.id = flights.id_plane)) as type_name\n" +
                "    FROM flights \n" +
                    requestSQL+";");
        pst = connection.prepareStatement(sqlFind);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            Flights flights = new Flights();
            flights.setId(resultSet.getInt(1));
            flights.setIdAirlines(resultSet.getInt(2));
            City city_from = new City(resultSet.getInt(4), resultSet.getString(6));
            City city_to = new City(resultSet.getInt(5), resultSet.getString(7));
            Route route = new Route(resultSet.getInt(3), city_from, city_to, resultSet.getInt(8));
            flights.setRoute(route);
            flights.setDate(resultSet.getDate(9).toLocalDate());
            flights.setTime(resultSet.getTime(10).toLocalTime());
            TypePlane typePlane = new TypePlane(resultSet.getInt(13), resultSet.getString(14));
            Plane plane = new Plane(resultSet.getInt(11), resultSet.getString(12), typePlane);
            flights.setPlane(plane);
            list.add(flights);
        }
        pst.close();
        return list;
    }

    @Override
    public List<Flights> findAll() throws SQLException {
        Statement st = null;
        List<Flights> list = new ArrayList<Flights>();
        st = connection.createStatement();
        String sqlFindAll = "SELECT flights.id, flights.id_flights, flights.id_route, route.id_from, route.id_to, city1.name_city as City_from, city2.name_city as City_to, route.time_travel, flights.date, flights.time, flights.id_plane, plane.name_plane ,plane.id_type, typea.name_type From airlines.route inner join  airlines.city as city1 on route.id_from = city1.Id inner join airlines.city as city2 on route.id_to = city2.Id inner join airlines.flights on flights.id_route = route.Id inner join airlines.plane on plane.Id = flights.id_plane inner join airlines.typea on typea.Id = plane.id_type;";
        ResultSet resultSet = st.executeQuery(sqlFindAll);
        while (resultSet.next()) {
            Flights flights = new Flights();
            flights.setId(resultSet.getInt(1));
            flights.setIdAirlines(resultSet.getInt(2));
            City city_from = new City(resultSet.getInt(4), resultSet.getString(6));
            City city_to = new City(resultSet.getInt(5), resultSet.getString(7));
            Route route = new Route(resultSet.getInt(3), city_from, city_to, resultSet.getInt(8));
            flights.setRoute(route);
            flights.setDate(resultSet.getDate(9).toLocalDate());
            flights.setTime(resultSet.getTime(10).toLocalTime());
            TypePlane typePlane = new TypePlane(resultSet.getInt(13), resultSet.getString(14));
            Plane plane = new Plane(resultSet.getInt(11), resultSet.getString(12), typePlane);
            flights.setPlane(plane);
            list.add(flights);
        }
        st.close();
        return list;
    }

    @Override
    public Flights findEntityById(Integer id) throws SQLException {
        PreparedStatement pst = null;
        Flights flights = null;
        String sqlFind = "SELECT flights.id, flights.id_flights, flights.id_route, route.id_from, route.id_to, city1.name_city as City_from, city2.name_city as City_to, route.time_travel, flights.date, flights.time, flights.id_plane, plane.name_plane ,plane.id_type, typea.name_type From airlines.route inner join  airlines.city as city1 on route.id_from = city1.Id inner join airlines.city as city2 on route.id_to = city2.Id inner join airlines.flights on flights.id_route = route.Id inner join airlines.plane on plane.Id = flights.id_plane inner join airlines.typea on typea.Id = plane.id_type WHERE flights.id = ?";
        pst = connection.prepareStatement(sqlFind);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            flights = new Flights();
            flights.setId(resultSet.getInt(1));
            flights.setIdAirlines(resultSet.getInt(2));
            City city_from = new City(resultSet.getInt(4), resultSet.getString(6));
            City city_to = new City(resultSet.getInt(5), resultSet.getString(7));
            Route route = new Route(resultSet.getInt(3), city_from, city_to, resultSet.getInt(8));
            flights.setRoute(route);
            flights.setDate(resultSet.getDate(9).toLocalDate());
            flights.setTime(resultSet.getTime(10).toLocalTime());
            TypePlane typePlane = new TypePlane(resultSet.getInt(13), resultSet.getString(14));
            Plane plane = new Plane(resultSet.getInt(11), resultSet.getString(12), typePlane);
            flights.setPlane(plane);
        }
        return flights;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateCity = "DELETE FROM airlines.flights WHERE Id = (?)";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setInt(1, id);
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean create(Flights entity) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateRoute = "INSERT INTO airlines.flights (id_flights, id_route, date, time, id_plane) VALUES (?,?,?,?,?)";
        pst = connection.prepareStatement(sqlCreateRoute);
        pst.setInt(1, entity.getIdAirlines());
        pst.setInt(2, entity.getRoute().getId());
        pst.setDate(3, java.sql.Date.valueOf(entity.getDate()));
        pst.setTime(4, java.sql.Time.valueOf(entity.getTime()));
        pst.setInt(5, entity.getPlane().getId());
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean update(Flights entity) throws SQLException {
        PreparedStatement pst = null;
        String sqlUpdateRoute = "UPDATE airlines.flights SET id_flights = ?, id_route = ?, date = ?, time = ?, id_plane = ? where Id= ?;";
        pst = connection.prepareStatement(sqlUpdateRoute);
        pst.setInt(1, entity.getIdAirlines());
        pst.setInt(2, entity.getRoute().getId());
        pst.setDate(3, java.sql.Date.valueOf(entity.getDate()));
        pst.setTime(4, java.sql.Time.valueOf(entity.getTime()));
        pst.setInt(5, entity.getPlane().getId());
        pst.setInt(6, entity.getId());
        pst.executeUpdate();
        return true;
    }
}
