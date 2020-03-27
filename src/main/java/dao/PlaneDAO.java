package dao;

import dto.City;
import dto.Plane;
import dto.Route;
import dto.TypePlane;
import exceptions.DepartureAndArrivalCityAreTheSameException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaneDAO extends AbstractDAO<Integer,Plane>{

    public PlaneDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Plane> findByField(String requestSQL) throws SQLException {
        PreparedStatement pst = null;
        List<Plane> list = new ArrayList<Plane>();
        String sqlFind = ("select airlines.plane.id,  airlines.plane.name_plane, airlines.plane.id_type, \n" +
                "\t\t\t\t\t\t\t\t(select airlines.typea.name_type from airlines.typea where airlines.plane.id_type = typea.id) as type_name " +
                "from airlines.plane " +
                requestSQL+";");
        pst = connection.prepareStatement(sqlFind);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            Plane plane = new Plane();
            plane.setId(resultSet.getInt(1));
            plane.setName_plane(resultSet.getString(2));
            TypePlane type = new TypePlane(resultSet.getInt(3),resultSet.getString(4));
            plane.setTypePlane(type);
            list.add(plane);
        }
        for(Plane element: list){
            System.out.println(element);
        }
        pst.close();
        return list;
    }

    @Override
    public List<Plane> findAll() throws SQLException {
        Statement st = null;
        List<Plane> list = new ArrayList<Plane>();
        st = connection.createStatement();
        String sqlFindAll = "SELECT plane.Id, plane.name_plane, plane.id_type, typea.name_type \n" +
                "From airlines.plane\n" +
                "inner join airlines.typea on plane.id_type = typea.Id;";
        ResultSet resultSet = st.executeQuery(sqlFindAll);
        while(resultSet.next()){
            Plane plane = new Plane();
            plane.setId(resultSet.getInt(1));
            plane.setName_plane(resultSet.getString(2));
            TypePlane type = new TypePlane(resultSet.getInt(3),resultSet.getString(4));
            plane.setTypePlane(type);
            list.add(plane);
        }
        return list;
    }

    @Override
    public Plane findEntityById(Integer id) throws SQLException {
        PreparedStatement  pst = null;
        Plane  plane = new Plane();
        String sqlFind = "SELECT airlines.plane.Id, airlines.plane.name_plane, airlines.plane.id_type, airlines.typea.name_type " +
                "                From airlines.plane " +
                "                inner join airlines.typea on plane.id_type = typea.Id where airlines.plane.Id = ?;";
        pst = connection.prepareStatement(sqlFind);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            plane.setId(resultSet.getInt(1));
            plane.setName_plane(resultSet.getString(2));
            TypePlane type = new TypePlane(resultSet.getInt(3),resultSet.getString(4));
            plane.setTypePlane(type);
        }
        return plane;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateCity = "DELETE FROM airlines.plane WHERE Id = (?)";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setInt(1,id);
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean create(Plane entity) throws SQLException {
        if(entity.getName_plane().equals("")){
            throw new SQLException();
        } else {
            PreparedStatement pst = null;
            String sqlCreateRoute = "INSERT INTO airlines.plane (name_plane, id_type) Values (?,?)";
            pst = connection.prepareStatement(sqlCreateRoute);
            pst.setString(1, entity.getName_plane());
            pst.setInt(2, entity.getTypePlane().getId());
            pst.executeUpdate();
        }
        return true;
    }

    @Override
    public boolean update(Plane entity) throws SQLException {
        if (entity.getName_plane().equals("")) {
            throw new SQLException();
        } else {
            PreparedStatement pst = null;
            String sqlUpdateRoute = "update airlines.plane set name_plane = ?, id_type = ? where Id= ?;";
            pst = connection.prepareStatement(sqlUpdateRoute);
            pst.setString(1, entity.getName_plane());
            pst.setInt(2, entity.getTypePlane().getId());
            pst.setInt(3, entity.getId());
            pst.executeUpdate();
        }
        return true;
    }


}
