package dao;
import dto.City;
import dto.TypePlane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypePlaneDAO extends AbstractDAO<Integer, TypePlane> {

    public TypePlaneDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<TypePlane> findByField(String requestSQL) throws SQLException {
        PreparedStatement pst = null;
        List<TypePlane> list = new ArrayList<TypePlane>();
        String sqlFind = ("SELECT airlines.typea.Id, airlines.typea.name_type \n" +
                "From airlines.typea\n"  +
                requestSQL+";");
        pst = connection.prepareStatement(sqlFind);
        ResultSet resultSet = pst.executeQuery();
        while(resultSet.next()){
            TypePlane typePlane = new TypePlane();
            typePlane.setId(resultSet.getInt(1));
            typePlane.setName_type(resultSet.getString(2));
            list.add(typePlane);
        }
        pst.close();
        return list;
    }

    @Override
    public List<TypePlane> findAll() {
        Statement st = null;
        List<TypePlane> list = new ArrayList<TypePlane>();
        try {
            st = connection.createStatement();
            String sqlFindAll = "SELECT typea.Id, typea.name_type\n" +
                    "                    From airlines.typea;";
            ResultSet resultSet = st.executeQuery(sqlFindAll);
            while(resultSet.next()){
                TypePlane type = new TypePlane();
                type.setId(resultSet.getInt(1));
                type.setName_type(resultSet.getString(2));
                list.add(type);
            }
            st.close();
        } catch(SQLException e){
            e.getStackTrace();
        }
        return list;
    }

    @Override
    public TypePlane findEntityById(Integer id) throws SQLException {
        PreparedStatement  pst = null;
        TypePlane typePlane = new TypePlane();
        String sqlFind = "SELECT airlines.typea.Id, airlines.typea.name_type \n" +
                "From airlines.typea\n"+" where airlines.typea.Id = ?;";
        pst = connection.prepareStatement(sqlFind);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            typePlane.setId(resultSet.getInt(1));
            typePlane.setName_type(resultSet.getString(2));
        }
        return typePlane;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement pst = null;
        String sqlCreateCity = "DELETE FROM airlines.typea WHERE Id = (?)";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setInt(1,id);
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean create(TypePlane entity) throws SQLException {
        if(entity.getName_type().equals("")){
            throw new SQLException();
        }
        PreparedStatement pst = null;
        String sqlCreateCity = "INSERT INTO airlines.typea (name_type) Values (?)";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setString(1,entity.getName_type());
        pst.executeUpdate();
        return true;
    }

    @Override
    public boolean update(TypePlane entity) throws SQLException {
        if(entity.getName_type().equals("")){
            throw new SQLException();
        }
        PreparedStatement pst = null;
        String sqlCreateCity = "update airlines.typea set name_type = ? where Id= ?;";
        pst = connection.prepareStatement(sqlCreateCity);
        pst.setInt(2,entity.getId());
        pst.setString(1,entity.getName_type());
        pst.executeUpdate();
        return true;
    }
}
