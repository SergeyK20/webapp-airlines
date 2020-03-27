package dao;

import exceptions.DepartureAndArrivalCityAreTheSameException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;

/**
 * Данный абстрактный класс является шаблоном классов DAO
 * для каждой сущности (таблицы). В конструкторе данного клсса
 * подключается connection для взаимодействия с базой данных.
 *
 * @param <K> generic для ключа
 * @param <T> generic для остальных полей таблицы
 */
public abstract class AbstractDAO <K,T> {
    /**
     * Поле необожимое для подключения к бд.
     */
    protected Connection connection;

    /**
     * Полю в классе присваивается значение.
     *
     * @param connection значение берется из ConnectionPool
     */
    public AbstractDAO (Connection connection){
        this.connection = connection;
    }

    public abstract List<T> findByField(String requestSQL) throws SQLException;
    public abstract List<T> findAll() throws SQLException;
    public abstract T findEntityById(K id) throws SQLException;
    public abstract boolean delete(K id) throws SQLException;
    public abstract boolean create(T entity) throws SQLIntegrityConstraintViolationException, SQLException, DepartureAndArrivalCityAreTheSameException;
    public abstract boolean update(T entity) throws SQLException, DepartureAndArrivalCityAreTheSameException;
    public void close(Statement st){
        try{
            if(st !=null){
                st.close();
            }
        } catch (SQLException e){
            System.out.println("Невозможно закрыть Statement...");
        }

    }
}
