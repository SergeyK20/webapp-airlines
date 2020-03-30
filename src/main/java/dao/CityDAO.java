package dao;

import dto.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO extends AbstractDAO<Integer,City> {

    public CityDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<City> findByField(String requestSQL) throws SQLException {
        PreparedStatement pst = null;
        List<City> list = new ArrayList<City>();
        String sqlFind = ("SELECT airlines.city.Id, airlines.city.name_city \n" +
                "From airlines.city\n"  +
                requestSQL+";");
        pst = connection.prepareStatement(sqlFind);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            City city = new City();
            city.setId(resultSet.getInt(1));
            city.setNameCity(resultSet.getString(2));
            list.add(city);
        }
        pst.close();
        return list;
    }

    @Override
    public List<City> findAll() throws SQLException {
        Statement st = null;
        List<City> list = new ArrayList<City>();
        st = connection.createStatement();
        String sqlFindAll = "SELECT city.Id, city.name_city \n" +
                "From airlines.city\n" +
                "order by city.Id ;";
        ResultSet resultSet = st.executeQuery(sqlFindAll);
        while(resultSet.next()){
            City city = new City();
            city.setId(resultSet.getInt(1));
            city.setNameCity(resultSet.getString(2));
            list.add(city);
        }
        return list;
    }

    @Override
    public City findEntityById(Integer id) throws SQLException {
        PreparedStatement  pst = null;
        City city = new City();
        String sqlFind = "SELECT city.Id, city.name_city \n" +
                "From airlines.city\n"+" where airlines.city.Id = ?;";
        pst = connection.prepareStatement(sqlFind);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        System.out.println("nenf");
        while (resultSet.next()) {
            city.setId(resultSet.getInt(1));
            city.setNameCity(resultSet.getString(2));
        }
        return city;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateCity = "DELETE FROM airlines.city WHERE Id = (?)";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setInt(1,id);
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean create(City entity) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateCity = "INSERT INTO airlines.city (name_city) Values (?)";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setString(1,entity.getNameCity());
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean update(City entity) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateCity = "update airlines.city set name_city = ? where Id= ?;";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setInt(2,entity.getId());
        pst.setString(1,entity.getNameCity());
        pst.executeUpdate();
        return true;

    }


}
