package DBDAO;

import Beans.Coupon;
import Beans.Customer;
import DB.ConnectionPool;
import DB.Util.DBManager;
import DB.Util.DBTools;
import DB.Util.ObjectExtractionUtil;
import Exceptions.CrudOperation;
import Exceptions.EntityCrudException;
import Exceptions.EntityType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDBDAO implements CustomerDAO {
    private static CustomerDBDAO instance;
    private final ConnectionPool connectionPool;

    private CustomerDBDAO() {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while getting connection pool instance"); // todo wheres-the-foot !? (wtf !?)
        }
    }

    public static CustomerDBDAO getInstance() {
        if (instance == null) {
            synchronized (CustomerDBDAO.class) {
                if (instance == null) {
                    instance = new CustomerDBDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public Integer createCustomer(Customer customer) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement = "INSERT INTO customers (first_name, last_name, email, password) VALUES(?, ?, ?, ?)";
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    sqlStatement, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.executeUpdate();
            final ResultSet generatedKeysResult = preparedStatement.getGeneratedKeys(); // todo adapt generic method to return generated-keys

            if (!generatedKeysResult.next()) {
                throw new RuntimeException("No results");
            }

            return generatedKeysResult.getInt(1);
        } catch (final Exception e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.CREATE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    /**
     * Returns an instance of Customer from MySQL database by customer ID number.
     *
     * @param customerId Customer ID number
     * @return Customer instance from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public Customer readCustomer(Integer customerId) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        ResultSet result;
        try {
            result = DBTools.runQueryForResult(DBManager.READ_CUSTOMER_BY_ID, params);
            assert result != null;
            result.next(); //todo needed?
            return ObjectExtractionUtil.resultSetToCustomer(result, readCouponsByCustomerId(customerId));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        }
    }
//    @Override
//    public Customer readCustomer(Integer customerId) throws EntityCrudException {
//        Connection connection = null;
//        try {
//            connection = connectionPool.getConnection();
//            final String sqlStatement = "SELECT * FROM customers WHERE id = ?";
//            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
//            preparedStatement.setInt(1, customerId);
//            final ResultSet result = preparedStatement.executeQuery();
//
//            if (!result.next()) {
//                return null;
//            }
//
//            return ObjectExtractionUtil.resultSetToCustomer(result, readCouponsByCustomerId(customerId));
//        } catch (Exception e) {
//            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
//    }

    /**
     * Returns a List of all Customers in MySQL database
     *
     * @return List of all Customers in MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Customer> readAllCustomers() throws EntityCrudException {
        ResultSet result;
        try {
            result = DBTools.runQueryForResult(DBManager.READ_ALL_CUSTOMERS);
            List<Customer> customers = new ArrayList<>();
            while (result.next()) { // todo consider asserting
                customers.add(ObjectExtractionUtil.resultSetToCustomer(result));
            }
            return customers;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
        }
    }

//    @Override
//    public List<Customer> readAllCustomers() throws EntityCrudException {
//        Connection connection = null;
//        try {
//            connection = connectionPool.getConnection();
//            final String sqlStatement = "SELECT * FROM customers";
//            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
//            final ResultSet result = preparedStatement.executeQuery();
//
//            final List<Customer> customers = new ArrayList<>();
//            while (result.next()) {
//                customers.add(ObjectExtractionUtil.resultSetToCustomer(result));
//            }
//
//            return customers;
//        } catch (Exception e) {
//            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.READ);
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
//    }

    /**
     * Returns a List of all Coupons a Customer owns by customer ID number from MySQL database
     * @param customerId Customer ID number
     * @return List of all Coupons by customer ID from MySQL database
     * @throws EntityCrudException Thrown if Read from MySQL was unsuccessful
     */
    @Override
    public List<Coupon> readCouponsByCustomerId(Integer customerId) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        ResultSet result;
        try {
            result = DBTools.runQueryForResult(DBManager.READ_COUPONS_BY_CUSTOMER_ID, params);
            List<Coupon> coupons = new ArrayList<>();
            while (result.next()) { // todo consider asserting
                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
            }
            return coupons;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
        }
    }

//    @Override
//    public List<Coupon> readCouponsByCustomerId(Integer customerId) throws EntityCrudException {
//        Connection connection = null;
//        try {
//            connection = connectionPool.getConnection();
//            final String sqlStatement = "SELECT * FROM customer_to_coupon WHERE customer_id = ?";
//            final PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
//            preparedStatement.setInt(1, customerId);
//            final ResultSet result = preparedStatement.executeQuery();
//
//            final List<Coupon> coupons = new ArrayList<>();
//            while (result.next()) {
//                coupons.add(ObjectExtractionUtil.resultSetToCoupon(result));
//            }
//
//            return coupons;
//        } catch (final Exception e) {
//            throw new EntityCrudException(EntityType.COUPON, CrudOperation.READ);
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
//    }

    /**
     * Updates Customer record in MySQL database.
     *
     * @param customer Customer instance to update by
     * @throws EntityCrudException Thrown if update in MySQL was unsuccessful
     */
    @Override
    public void updateCustomer(Customer customer) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword()); // todo should it be in the database???
        try {
            System.out.println("Updated Customer: " + DBTools.runQuery(DBManager.UPDATE_CUSTOMER, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.UPDATE);
        }
    }

//    @Override
//    public void updateCustomer(Customer customer) throws EntityCrudException {
//        Connection connection = null;
//        try {
//            connection = connectionPool.getConnection();
//            final String sqlStatement = "UPDATE customers SET first_name = ?,last_name = ?, email = ?, password = ?";
//            PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
//            preparedStatement.setString(1, customer.getFirstName());
//            preparedStatement.setString(2, customer.getLastName());
//            preparedStatement.setString(3, customer.getEmail());
//            preparedStatement.setString(4, customer.getPassword());
//            preparedStatement.executeUpdate();
//        } catch (Exception e) {
//            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.UPDATE);
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
//    }

    /**
     * Deletes Customer record from MySQL database by customer ID number.
     *
     * @param customerId ID number of the Customer to be deleted
     * @throws EntityCrudException Thrown if delete from MySQL was unsuccessful
     */
    @Override
    public void deleteCustomer(Integer customerId) throws EntityCrudException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerId);
        try {
            System.out.println("Deleted Customer: " + DBTools.runQuery(DBManager.DELETE_CUSTOMER_BY_ID, params));
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.DELETE);
        }
    }

//    @Override
//    public void deleteCustomer(Integer customerId) throws EntityCrudException {
//        Connection connection = null;
//        try {
//            connection = connectionPool.getConnection();
//            final String sqlStatement = "DELETE FROM customers WHERE id = ?";
//            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
//            preparedStatement.setInt(1, customerId);
//            preparedStatement.executeUpdate();
//        } catch (Exception e) {
//            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.DELETE);
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
//    }

    /**
     * Checks whether the Customer corresponding to the email argument exists in MySQL database
     * by counting the customers that meet the criteria.
     *
     * @param email Customer email
     * @return true -> customer exists, false -> customer does not exist
     * @throws EntityCrudException Thrown if count in MySQL was unsuccessful
     */
    @Override
    public boolean isCustomerExist(String email) throws EntityCrudException {
        int counter;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);
        try {
            ResultSet result = DBTools.runQueryForResult(DBManager.COUNT_CUSTOMERS_BY_EMAIL, params);
            assert result != null;
            result.next(); //todo needed?
            counter = result.getInt(1);
            return counter != 0;
        } catch (SQLException e) {
            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.COUNT);
        }
    }

//    @Override
//    public boolean isCustomerExist(String email) throws EntityCrudException {
//        Connection connection = null;
//        int counter;
//        try {
//            connection = connectionPool.getConnection();
//            final String sqlStatement = "SELECT count(*) FROM companies WHERE email = ?";
//            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
//            preparedStatement.setString(1, email);
//            final ResultSet result = preparedStatement.executeQuery();
//            result.next();
//            counter = result.getInt(1);
//            return counter != 0;
//        } catch (Exception e) {
//            throw new EntityCrudException(EntityType.CUSTOMER, CrudOperation.COUNT);
//        } finally {
//            connectionPool.returnConnection(connection);
//        }
//    }

    @Override
    public void deleteCouponPurchaseHistory(Integer customerId) throws EntityCrudException {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            final String sqlStatement =
                    "DELETE FROM customer_to_coupon WHERE customer_id";
            final PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sqlStatement);
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new EntityCrudException(EntityType.COUPON, CrudOperation.DELETE);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}


