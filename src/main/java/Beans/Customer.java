package Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private Integer id;
    private String firstName, lastName, email, password;
    private List<Coupon> coupons;

    /**
     * Initiates an instance of Customer.
     *
     * @param id        Customer ID number
     * @param firstName Customer's first name
     * @param lastName  Customer's last name
     * @param email     Customer's email
     * @param password  Customer's login password
     */
    public Customer(Integer id, String firstName, String lastName, String email, String password) {
        this.id = id;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        this.coupons = new ArrayList<>();
    }

    /**
     * Returns Customer ID number
     *
     * @return Customer ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Returns Customer Coupon list
     *
     * @return Customer Coupon list
     */
    public List<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * Sets Customer Coupon list
     *
     * @param coupons Customer Coupon list
     */
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * Returns Customer first name
     *
     * @return Customer first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets Customer first name
     *
     * @param firstName Customer first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns Customer last name
     *
     * @return Customer last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets Customer last name
     *
     * @param lastName Customer last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns Customer email
     *
     * @return Customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets Customer email
     *
     * @param email Customer email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns Customer login password
     *
     * @return Customer login password
     */
    public String getPassword() {
        return password; // todo consider deleting
    }

    /**
     * Sets Customer login password
     *
     * @param password Customer login password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to compare an Object value to Customer value.
     *
     * @param o Generic Object
     * @return True -> Objects are equal, else False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password) && Objects.equals(coupons, customer.coupons);
    }

    /**
     * Generates a distinct hashCode for Customer.
     *
     * @return hashCode for Customer
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, coupons);
    }

    /**
     * Returns a String description of Customer attributes.
     *
     * @return Attribute String for Customer
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("Company{").
                append("id=").append(id).
                append(", firstName='").append(firstName).append('\'').
                append(", lastName='").append(lastName).append('\'').
                append(", email='").append(email).append('\'').
                append(", coupons=").append(coupons.size() == 0 ? "No coupons" : coupons).append('}');
        return stringBuilder.toString();
    }
}
