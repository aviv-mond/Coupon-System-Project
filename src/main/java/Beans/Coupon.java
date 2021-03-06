package Beans;

import java.sql.Date;
import java.util.Objects;

/**
 * Class for Coupon record, includes attributes: id, companyId, amount, price, (enum) category, title, description, image, (sql.Date) startDate, (sql.Date) endDate
 */
public class Coupon {
    private Integer id;
    private int companyId, amount;
    private double price;
    private Category category;
    private String title, description, image;
    private Date startDate, endDate;

    /**
     * Initiates an instance of Coupon. From MySQL database.
     *
     * @param id          Coupon ID number
     * @param companyId   ID number of the issuing Company
     * @param amount      How many coupons available for purchase
     * @param price       Price to purchase the Coupon
     * @param category    Commercial category the Coupon gives discount in
     * @param title       Commercial name of the Coupon
     * @param description Description of the products the Coupon is valid for and additional info
     * @param image       File name for the Coupon's logo image
     * @param startDate   Coupon creation date
     * @param endDate     Date of Coupon expiration
     */
    public Coupon(Integer id, int companyId, int amount, double price, Category category, String title, String description, String image, Date startDate, Date endDate) {
        this.id = id;
        this.companyId = companyId;
        this.amount = amount;
        this.price = price;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Initiates an instance of Coupon. Before insertion to MySQL database.
     *
     * @param companyId   ID number of the issuing Company
     * @param amount      How many coupons available for purchase
     * @param price       Price to purchase the Coupon
     * @param category    Commercial category the Coupon gives discount in
     * @param title       Commercial name of the Coupon
     * @param description Description of the products the Coupon is valid for and additional info
     * @param image       File name for the Coupon's logo image
     * @param startDate   Coupon creation date
     * @param endDate     Date of Coupon expiration
     */
    public Coupon(int companyId, int amount, double price, Category category, String title, String description, String image, Date startDate, Date endDate) {
        this.companyId = companyId;
        this.amount = amount;
        this.price = price;
        this.category = category;
        this.title = title;
        this.description = description;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns Coupon ID number.
     *
     * @return Coupon ID number
     */
    public int getId() {
        return id;
    }

    /**
     * You can not set this parameter.
     *
     * @param id Coupon ID number
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the ID of the Company that issued the Coupon.
     *
     * @return Issuing Company's ID number
     */
    public int getCompanyId() {
        return companyId;
    }

    /**
     * You can not set this parameter.
     *
     * @param companyId Issuing Company's ID number
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    /**
     * Returns the amount of coupons available for purchase.
     *
     * @return Coupon amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of coupons available for purchase.
     *
     * @param amount Coupon amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the price for purchasing the Coupon.
     *
     * @return Price to purchase the Coupon
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price for purchasing the Coupon.
     *
     * @param price Price to purchase the Coupon
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the commercial category the Coupon gives discount in.
     *
     * @return Coupon commercial category
     */
    public String getCategory() {
        return String.valueOf(category);
    }

    /**
     * Sets the commercial category the Coupon gives discount in.
     *
     * @param category Coupon commercial category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the commercial name of the Coupon.
     *
     * @return Coupon commercial title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the commercial name of the Coupon.
     *
     * @param title Coupon commercial title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the products the Coupon is valid for and additional info.
     *
     * @return Coupon product description and additional info
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Coupon. Describe the products the Coupon is valid for and any additional info.
     *
     * @param description Coupon description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the file name for the Coupon's logo image.
     *
     * @return Logo image file name
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the file name for the Coupon's logo image.
     *
     * @param image Logo image file name
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the date of the Coupon creation.
     *
     * @return Coupon creation date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * You can not set this parameter.
     *
     * @param startDate Coupon creation date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the date of the Coupon expiration.
     *
     * @return Coupon expiration date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the date of the Coupon expiration.
     *
     * @param endDate Coupon expiration date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Method to compare an Object value to Coupon value.
     *
     * @param o Generic Object
     * @return True -> Objects are equal, else False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && companyId == coupon.companyId && amount == coupon.amount && Double.compare(coupon.price, price) == 0 && category == coupon.category && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(image, coupon.image) && Objects.equals(startDate, coupon.startDate) && Objects.equals(endDate, coupon.endDate);
    }

    /**
     * Generates a distinct hashCode for Coupon.
     *
     * @return hashCode for Coupon
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, companyId, amount, price, category, title, description, image, startDate, endDate);
    }

    /**
     * Returns String description of Coupon attributes: id, companyId, amount, price, category, title, description, image, startDate, endDate
     *
     * @return Returns String description of Coupon attributes
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", amount=" + amount +
                ", price=" + price +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
