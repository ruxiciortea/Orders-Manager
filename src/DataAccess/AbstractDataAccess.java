package DataAccess;

import Model.Client;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generic class that describes the behaviour of the communication with the database (CRUD)
 */
public class AbstractDataAccess<T> {

    protected static final Logger logger = Logger.getLogger(AbstractDataAccess.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDataAccess() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    // CRUD Operations
    /**
     * Finds the entry with the corresponding ID
     *
     * @param: id
     * @return: object found of type T with the specified ID
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = selectByIdQuery();

        T returnObject = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            returnObject = (T)createObject(resultSet);
        } catch (SQLException e) {
            logger.log(Level.WARNING, type.getName() + "Data Access: findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return returnObject;
    }

    /**
     * Finds all the entries with from the table
     *
     * @return: list object found of type T
     */
    public ArrayList<T> findAll() {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int numberElements = getNumberOfEntries();
        ArrayList<T> items = new ArrayList<T>();

        try {
            statement = dbConnection.prepareStatement(selectAllQuery());
            resultSet = statement.executeQuery();
            Field[] fields = type.getDeclaredFields();

            ArrayList<T> foundItems = (ArrayList<T>)createObjects(resultSet);
            items = foundItems;
        } catch (SQLException e) {
            logger.log(Level.WARNING, type.getName() + "Data Access: findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(dbConnection);
        }

        return items;
    }

    /**
     * Inserts the given entry into the correct table
     *
     * @param: the object to insert
     * @return: the generated id of the newly inserted object
     */
    public int insert(T t) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        String query = insertQuery();

        int insertedID = -1;

        try{
            statement = dbConnection.prepareStatement(insertQuery(), Statement.RETURN_GENERATED_KEYS);
            Field[] fields = type.getDeclaredFields();
            int currentParameter = 1;

            for(Field field : fields) {
                if(!field.getName().equals("ID")) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method getter = propertyDescriptor.getReadMethod();
                    statement.setObject(currentParameter, getter.invoke(t));
                    currentParameter++;
                }
            }

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                insertedID = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Data Access: insert " + e.getMessage());
            return -1;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(dbConnection);
        }

        return insertedID;
    }

    /**
     * Updates the entry with the specified ID with the fields from the given object
     *
     * @param: the ID of the entry to be updated
     * @param: the object containing the new values
     * @return: boolean value representing the status of the operation
     */
    public boolean update(int id, T newT) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = dbConnection.prepareStatement(updateQuery(id));

            Field[] fields = type.getDeclaredFields();
            int currentParameter = 1;

            for(Field field : fields) {
                if(!field.getName().equals("ID")) {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method getter = propertyDescriptor.getReadMethod();
                    statement.setObject(currentParameter, getter.invoke(newT));
                    currentParameter++;
                }
            }

            statement.execute();

        } catch (Exception e) {
            logger.log(Level.WARNING, "Data Access: update " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(dbConnection);
        }

        return true;
    }

    /**
     * Deletes the entry with the given ID
     *
     * @param: the ID of the entry to be deleted
     * @return: boolean value representing the status of the operation
     */
    public boolean delete(int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            if (findById(id) == null) {
                return false;
            }

            statement = dbConnection.prepareStatement(deleteQuery());
            statement.setObject(1, id);
            statement.execute();

        } catch (SQLException e) {
            logger.log(Level.WARNING, "Data Access: delete " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(dbConnection);
        }

        return true;
    }

    // Private Functions
    private String selectByIdQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE id = ?");

        return sb.toString();
    }

    private String selectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());

        return sb.toString();
    }

    private String numberOfEntriesQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" COUNT(*) ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());

        return sb.toString();
    }

    private String insertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append("(");

        Field[] fields = type.getDeclaredFields();
        String values = " VALUES (";

        for(Field field : fields) {
            if(!field.getName().equals("ID")) {
                sb.append(field.getName() + ",");
                values += "?,";
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        sb.append(values);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        return sb.toString();
    }

    private String updateQuery(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        Field[] fields = type.getDeclaredFields();

        for(Field field : fields) {
            if(!field.getName().equals("ID")) {
                sb.append(field.getName() + " = ?, ");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE");
        sb.append(" id = ");
        sb.append(id);

        return sb.toString();
    }

    private String deleteQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE id = ?");

        return sb.toString();
    }

    public int getNumberOfEntries() {
        int toReturn = 0;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement countStatement = null;
        ResultSet rs = null;
        try {
            countStatement = dbConnection.prepareStatement(numberOfEntriesQuery());
            rs = countStatement.executeQuery();
            rs.next();
            toReturn = rs.getInt("COUNT(*)");
        } catch (SQLException e) {
            toReturn = -1;
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(countStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    private Object createObject(ResultSet resultSet) {
        T toReturn = null;
        Constructor[] constructors = type.getDeclaredConstructors();
        Constructor constructor = null;

        for(int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];

            if(constructor.getGenericParameterTypes().length == 0) {
                break;
            }
        }

        try {
            resultSet.next();
            constructor.setAccessible(true);
            toReturn = (T)constructor.newInstance();

            for(Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                Object value = resultSet.getObject(fieldName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method setter = propertyDescriptor.getWriteMethod();
                setter.invoke(toReturn, value);
            }
        } catch (Exception e) {
            return null;
        }

        return toReturn;
    }

    private ArrayList<T> createObjects(ResultSet resultSet) {
        ArrayList<T> list = new ArrayList<T>();
        Constructor[] constructors = type.getDeclaredConstructors();
        Constructor constructor = null;

        for (int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];

            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            while (resultSet.next()) {
                constructor.setAccessible(true);
                T instance = (T)constructor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }

                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return list;
    }

}
